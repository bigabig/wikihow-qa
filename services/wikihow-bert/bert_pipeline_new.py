from __future__ import print_function, division
import logging
import os
import time
import torch
import torch.nn.functional as F
import torch.optim as optim
from pytorch_pretrained_bert import BertTokenizer, BertConfig

# HYPER PARAMETERS
from Beamsearch import beam_search
from Datasets import get_data_loaders
from Models import BertTransformer

PATH = os.path.normpath("./model/bert-model.pth")
device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")  # Figure out whether to use the GPU or CPU

# Create A Configuration for BERT
config = BertConfig(vocab_size_or_config_json_file=30522, hidden_size=768, num_hidden_layers=12, num_attention_heads=12,
                    intermediate_size=3072)

max_seq_length = 512
batch_size = 1
num_epochs = 1
learning_rate = 0.0001

display_every_steps = 100
save_every_steps = 2000


def validate_model(dataloaders_dict, model, tokenizer):
    # Try to load existing model
    if os.path.isfile(PATH):
        print("Loading existing model!")
        print("-" * 20)
        checkpoint = torch.load(PATH)
        model.load_state_dict(checkpoint['model_state_dict'])

    # Set model to evaluate mode
    model.eval()

    for ids_source, segment_ids_source, mask_source, ids_target, segment_ids_target, mask_target, nopeak_mask in dataloaders_dict['val']:
        result = beam_search(tokenizer, ids_source, segment_ids_source, mask_source, model, 2, 512, ids_target)
        print(result)


def train_model(selected_mode, dataloaders_dict, dataset_sizes, tokenizer, model, optimizer, scheduler=None):

    epoch = 0       # current epoch
    mode = selected_mode        # mode train or val
    total_loss = 0  # used to display average loss every display_every_steps steps
    step = 0        # number of steps in the current epoch in the current phase

    # Try to load existing model
    if os.path.isfile(PATH):
        print("Loading existing model!")
        print("-" * 20)
        checkpoint = torch.load(PATH)
        model.load_state_dict(checkpoint['model_state_dict'])
        optimizer.load_state_dict(checkpoint['optimizer_state_dict'])
        epoch = checkpoint['epoch']
        mode = checkpoint['mode']
        total_loss = checkpoint['total_loss']

    start = time.time()

    while epoch < num_epochs:
        print('Epoch {}/{}'.format(epoch+1, num_epochs))
        print('-' * 20)

        if mode == 'train':
            print("Starting phase: Training!")
            print('-' * 20)
            # TODO IST DAS SO RICHTIG?
            if scheduler:
                scheduler.step(epoch)
            model.train()  # Set model to training mode
        else:
            print("Starting phase: Evaluation!")
            print('-' * 20)
            model.eval()  # Set model to evaluate mode

        # TODO: STEP IS PROBLEMATIC!!!
        # Wie kann ich beim Dataloader sicherstellen, dass wenn ich mitten im training aufhöre, er auch da weiter macht wo zuende war (also ohne beispiele doppelt zu sehen!)
        # TODO: IST DIE NO PEAK MASK RICHTIG? WIE RAUSFINDEN?
        for ids_source, segment_ids_source, mask_source, ids_target, segment_ids_target, mask_target, nopeak_mask in dataloaders_dict[mode]:
            step = step + 1

            # let the model predict
            prediction = model(ids_source, segment_ids_source, mask_source, ids_target, segment_ids_target, mask_target, nopeak_mask)

            # TODO WHAT IS THIS?
            optimizer.zero_grad()

            # BEGIN CALCULATE LOSS
            # Shift the target sequence one to the left, so the model learns to predict instead of copying!
            ids_target = ids_target.squeeze()
            shifted_ids_target = ids_target[1:]
            shifted_ids_target = torch.cat((shifted_ids_target, torch.tensor([0]).cuda()), 0)

            # muss ich vorher nochmal softmax anwenden?
            # zur zeit sind prediction ja "random werte", oder übernimmt das die cross_entropy funktion?
            # TODO WAS IST SIZE AVERAGE?
            loss = F.cross_entropy(prediction.squeeze(), shifted_ids_target.squeeze())
            total_loss += loss.item()
            # END CALCULATE LOSS

            # backward + optimize only if in training phase
            if mode == 'train':
                loss.backward()
                optimizer.step()

            if step % display_every_steps == 0:
                p = int(100 * step / dataset_sizes[mode])
                avg_loss = total_loss / display_every_steps
                print("   %dm: epoch %d mode %s [%s%s]  %d%%  loss = %.3f" %
                      ((time.time() - start) // 60, epoch + 1, mode, "".join('#' * (p // 5)),
                       "".join(' ' * (20 - (p // 5))), p, avg_loss))

                # print the target sentence
                text = tokenizer.convert_ids_to_tokens(shifted_ids_target.squeeze().tolist())
                print(text)

                # print the predicted sentence
                softmaxed = F.softmax(prediction.squeeze(), dim=-1)
                argmaxed = torch.argmax(softmaxed, -1)
                text = tokenizer.convert_ids_to_tokens(argmaxed.tolist())
                print(text)

                total_loss = 0

            if mode == 'train' and step % save_every_steps == 0:
                save_model(model, optimizer, epoch, mode, total_loss)
                break

            # TODO: In VAL phase check how good model performs and save it as best model!

            # END OF ONE BATCH

        # END OF EPOCH
        print("%dm: epoch %d complete" % ((time.time() - start) // 60, epoch + 1))
        epoch = epoch + 1
        step = 0
        total_loss = 0

    # END OF TRAINING
    print("Training completed!")
    # Dont need to save model if validating, model does not change!
    if mode == 'train':
        save_model(model, optimizer, epoch, mode, total_loss)


def save_model(model, optimizer, epoch, mode, total_loss):
    print("Saving current model")
    torch.save({
        'epoch': epoch,
        'phase': mode,
        'model_state_dict': model.state_dict(),
        'optimizer_state_dict': optimizer.state_dict(),
        'total_loss': total_loss,
    }, PATH)


def main():
    # Activate Logging
    logging.basicConfig(level=logging.INFO)

    # Load bert tokenizer (vocabulary)
    tokenizer = BertTokenizer.from_pretrained('bert-base-uncased')

    # Load Dataset
    dataloader_dict, dataset_sizes = get_data_loaders(tokenizer, max_seq_length, batch_size)

    # Create the BERT model
    model = BertTransformer(768, 3, 6, 0.1, 30522)
    model.to(device)

    # Option 1: Define optimizer with one learning rate for the whole model
    optimizer = optim.Adam(model.parameters(), lr=learning_rate, betas=(0.9, 0.98), eps=1e-9)

    # Option 2: Define optimizer with different learning rates for BERT and model and decaying learning rate
    # lrtransformer = .0001
    # lrbert = .00001
    # optimizer = optim.Adam([
    #         {"params": model.encoder.parameters(), "lr": lrtransformer},
    #         {"params": model.decoder.parameters(), "lr": lrtransformer},
    #         {"params": model.out.parameters(), "lr": lrtransformer},
    #         {"params": model.bert.parameters(), "lr": lrbert},
    # ])
    # # TODO WHAT?
    # scheduler = lr_scheduler.StepLR(optimizer, step_size=3, gamma=0.1)  # Decay LR by a factor of 0.1 every 7 epochs

    mode = 'train'
    # mode = 'val'
    # train_model(mode, dataloader_dict, dataset_sizes, tokenizer, model, optimizer)
    validate_model(dataloader_dict, model, tokenizer)


if __name__ == "__main__":
    main()

