import torch
import torch.nn as nn 
from Layers import EncoderLayer, DecoderLayer
from Embed import Embedder, PositionalEncoder
from pytorch_pretrained_bert import BertModel
from Sublayers import Norm
import copy


def get_clones(module, N):
    return nn.ModuleList([copy.deepcopy(module) for i in range(N)])


class BertTransformer(nn.Module):
    # The parameters hidden_size, num_hidden_layers and target_vocab length have to be equal to BERT's config!!!
    def __init__(self, hidden_size, num_hidden_layers, num_attention_heads, dropout_probability, target_vocab_length):
        super().__init__()
        self.bert = BertModel.from_pretrained('bert-base-uncased')
        self.encoder = EncoderWithBertEmbeddings(self.bert, hidden_size, num_hidden_layers, num_attention_heads, dropout_probability)
        self.decoder = DecoderWithBertEmbeddings(self.bert, hidden_size, num_hidden_layers, num_attention_heads, dropout_probability)
        self.out = nn.Linear(hidden_size, target_vocab_length)

    def forward(self, src_ids, src_token_type_ids, src_mask, target_ids, target_token_type_ids, target_mask, target_nopeak_mask):
        output = self.encoder(src_ids, src_token_type_ids, src_mask)
        d_output = self.decoder(target_ids, target_token_type_ids, target_mask, target_nopeak_mask, output, src_mask)
        output = self.out(d_output)
        return output


class EncoderWithBertEmbeddings(nn.Module):
    def __init__(self, bert, hidden_size, num_hidden_layers, num_attention_heads, dropout):
        super().__init__()
        self.N = num_hidden_layers
        self.bert = bert
        self.pe = PositionalEncoder(hidden_size, dropout=dropout)
        self.layers = get_clones(EncoderLayer(hidden_size, num_attention_heads, dropout), num_hidden_layers)
        self.norm = Norm(hidden_size)

    def forward(self, src_ids, src_token_type_ids, src_mask):
        # x sind sozusagen die von bert generierten embeddings und haben die größe
        # [batch_size, sequence_length, hidden_size]
        x, _ = self.bert(src_ids, src_token_type_ids, src_mask, output_all_encoded_layers=False)
        x = self.pe(x)
        for i in range(self.N):
            x = self.layers[i](x, src_mask)
        return self.norm(x)


class DecoderWithBertEmbeddings(nn.Module):
    def __init__(self, bert, hidden_size, num_hidden_layers, num_attention_heads, dropout):
        super().__init__()
        self.N = num_hidden_layers
        self.bert = bert
        self.pe = PositionalEncoder(hidden_size, dropout=dropout)
        self.layers = get_clones(DecoderLayer(hidden_size, num_attention_heads, dropout), num_hidden_layers)
        self.norm = Norm(hidden_size)

    def forward(self, target_ids, target_token_type_ids, target_mask, target_nopeak_mask, encoder_outputs, src_mask):
        # x sind sozusagen die von bert generierten embeddings und haben die größe
        # [batch_size, sequence_length, hidden_size]
        x, _ = self.bert(target_ids, target_token_type_ids, target_mask, output_all_encoded_layers=False)
        x = self.pe(x)
        for i in range(self.N):
            x = self.layers[i](x, encoder_outputs, src_mask, target_nopeak_mask)
        return self.norm(x)


class Encoder(nn.Module):
    def __init__(self, vocab_size, d_model, N, heads, dropout):
        super().__init__()
        self.N = N
        self.embed = Embedder(vocab_size, d_model)
        self.pe = PositionalEncoder(d_model, dropout=dropout)
        self.layers = get_clones(EncoderLayer(d_model, heads, dropout), N)
        self.norm = Norm(d_model)

    def forward(self, src, mask):
        x = self.embed(src)
        x = self.pe(x)
        for i in range(self.N):
            x = self.layers[i](x, mask)
        return self.norm(x)


class Decoder(nn.Module):
    def __init__(self, vocab_size, d_model, N, heads, dropout):
        super().__init__()
        self.N = N
        self.embed = Embedder(vocab_size, d_model)
        self.pe = PositionalEncoder(d_model, dropout=dropout)
        self.layers = get_clones(DecoderLayer(d_model, heads, dropout), N)
        self.norm = Norm(d_model)

    def forward(self, trg, e_outputs, src_mask, trg_mask):
        x = self.embed(trg)
        x = self.pe(x)
        for i in range(self.N):
            x = self.layers[i](x, e_outputs, src_mask, trg_mask)
        return self.norm(x)


class Transformer(nn.Module):
    def __init__(self, src_vocab, trg_vocab, d_model, N, heads, dropout):
        super().__init__()
        self.encoder = Encoder(src_vocab, d_model, N, heads, dropout)
        self.decoder = Decoder(trg_vocab, d_model, N, heads, dropout)
        self.out = nn.Linear(d_model, trg_vocab)

    def forward(self, src, trg, src_mask, trg_mask):
        e_outputs = self.encoder(src, src_mask)
        d_output = self.decoder(trg, e_outputs, src_mask, trg_mask)
        output = self.out(d_output)
        return output
