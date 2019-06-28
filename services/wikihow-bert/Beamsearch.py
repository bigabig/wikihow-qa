import torch
import torch.nn.functional as F
import math

from Datasets import create_nopeak_mask


def init_vars(src_ids, src_token_type_ids, src_mask, model, k, max_len, true_target_ids):
    # encode source
    e_output = model.encoder(src_ids, src_token_type_ids, src_mask)

    # create target
    target_ids = [101, 10]  # tokenizer.convert_tokens_to_ids(['[CLS]'])

    # create target mask
    target_mask = [1] * len(target_ids)

    # create segment ids
    target_segment_ids = [0] * len(target_ids)

    # target mask wird hier anders erstellt...
    # normalerweise hat nopeak mask nur so viele 1'n wie target lang ist, aber das wäre ja schummeln!
    target_nopeak_mask2 = create_nopeak_mask(512)
    target_nopeak_mask = []
    for i in range(0, 512):
        temp = []
        for j in range(0, 512):
            if j <= i and j < len(target_ids):
                temp.append(1)
            else:
                temp.append(0)
        target_nopeak_mask.append(temp)

    # pad everything up to sequenze length
    padding = [0] * (512 - len(target_ids))
    target_ids += padding
    target_mask += padding
    target_segment_ids += padding

    # convert to tensors
    target_ids = torch.tensor([target_ids])
    target_mask = torch.tensor([target_mask])
    target_segment_ids = torch.tensor([target_segment_ids])
    target_nopeak_mask = torch.tensor([target_nopeak_mask])
    target_nopeak_mask2 = torch.tensor([target_nopeak_mask2])
    if torch.cuda.is_available():
        target_ids = target_ids.cuda()
        target_mask = target_mask.cuda()
        target_segment_ids = target_segment_ids.cuda()
        target_nopeak_mask = target_nopeak_mask.cuda()
        target_nopeak_mask2 = target_nopeak_mask2.cuda()

    # predict with limited information (just the CLS token / sentence start token)
    out = model.out(model.decoder(target_ids, target_segment_ids, target_mask, target_nopeak_mask, e_output, src_mask))
    out = F.softmax(out, dim=-1)

    # TODO WARUM -1? das ist doch das letzte ergebnis, ich bin doch am ersten (0) interessiert!
    # probs, ix = out[:, -1].data.topk(k)
    probs, ix = out[:, 1].data.topk(k)  # top k ist wie argmax, nur für die besten k argumente (statt 1)
    log_scores = torch.Tensor([math.log(prob) for prob in probs.data[0]]).unsqueeze(0)

    # convert target_ids to target_ids covering the top k predicted sentences
    target_ids = torch.zeros(k, max_len).long()
    if torch.cuda.is_available():
        target_ids = target_ids.cuda()
    target_ids[:, 0] = 101  # [CLS] token
    target_ids[:, 1] = 10  # [CLS] token
    target_ids[:, 2] = ix[0]
    # convert embedding outputs to k times the embedding outputs
    e_outputs = torch.zeros(k, e_output.size(-2), e_output.size(-1))
    if torch.cuda.is_available():
        e_outputs = e_outputs.cuda()
    e_outputs[:, :] = e_output[0]

    # target_ids size = [k, 512], e_outputs_size = [k, 512, 768], log_scores = [1, k]
    return target_ids, e_outputs, log_scores


def k_best_outputs(outputs, out, log_scores, i, k):

    # ER NIMMT HIER IMMER DEN LETZEN OUTPUT!!! möglicher FEHLER IN MEINEM MODELL!
    probs, ix = out[:, i].data.topk(k)
    log_probs = torch.Tensor([math.log(p) for p in probs.data.view(-1)]).view(k, -1) + log_scores.transpose(0, 1)
    k_probs, k_ix = log_probs.view(-1).topk(k)

    row = k_ix // k
    col = k_ix % k

    outputs[:, :i] = outputs[row, :i]
    outputs[:, i] = ix[row, col]

    log_scores = k_probs.unsqueeze(0)

    return outputs, log_scores


def beam_search(tokenizer, src_ids, src_token_type_ids, src_mask, model, k, max_len, true_target_ids):
    target_ids, e_outputs, log_scores = init_vars(src_ids, src_token_type_ids, src_mask, model, k, max_len, true_target_ids)
    eos_tok = 90  # End of Sentence Symbol: tokenizer.convert_tokens_to_ids(['[SEP]'])
    ind = None
    for i in range(3, max_len):

        # create target mask
        target_mask = [1] * i

        # create segment ids
        target_segment_ids = [0] * i

        # pad everything up to sequenze length
        padding = [0] * (512 - i)
        target_mask += padding
        target_segment_ids += padding

        # target mask wird hier anders erstellt...
        # normalerweise hat nopeak mask nur so viele 1'n wie target lang ist, aber das wäre ja schummeln!
        target_nopeak_mask = create_nopeak_mask(512)

        # repeat everything k times
        target_mask = [target_mask for _ in range(k)]
        target_segment_ids = [target_segment_ids for _ in range(k)]
        target_nopeak_mask = [target_nopeak_mask for _ in range(k)]

        # convert to tensors
        target_mask = torch.tensor(target_mask)
        target_segment_ids = torch.tensor(target_segment_ids)
        target_nopeak_mask = torch.tensor(target_nopeak_mask)
        if torch.cuda.is_available():
            target_mask = target_mask.cuda()
            target_segment_ids = target_segment_ids.cuda()
            target_nopeak_mask = target_nopeak_mask.cuda()

        # out = model.out(model.decoder(outputs[:, :i], e_outputs, src_mask, trg_mask))
        out = model.out(
                model.decoder(target_ids, target_segment_ids, target_mask, target_nopeak_mask, e_outputs, src_mask))

        out = F.softmax(out, dim=-1)

        target_ids, log_scores = k_best_outputs(target_ids, out, log_scores, i, k)

        ones = (target_ids == eos_tok).nonzero()  # Occurrences of end symbols for all input sentences.
        sentence_lengths = torch.zeros(len(target_ids), dtype=torch.long).cuda()
        for vec in ones:
            i = vec[0]
            if sentence_lengths[i] == 0:  # First end symbol has not been found yet
                sentence_lengths[i] = vec[1]  # Position of first end symbol

        num_finished_sentences = len([s for s in sentence_lengths if s > 0])

        if num_finished_sentences == k:
            alpha = 0.7
            div = 1 / (sentence_lengths.type_as(log_scores) ** alpha)
            _, ind = torch.max(log_scores * div, 1)
            ind = ind.data[0]
            break

    if ind is None:
        return tokenizer.convert_ids_to_tokens(target_ids[0].tolist())

    else:
        return tokenizer.convert_ids_to_tokens(target_ids[ind].tolist())
