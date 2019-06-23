from __future__ import print_function, division
from sklearn.model_selection import train_test_split
from torch.utils.data import Dataset
import torch
import pandas as pd


class TextDataset(Dataset):
    def __init__(self, x_y_list, tokenizer, max_seq_length):
        self.x_y_list = x_y_list
        self.tokenizer = tokenizer
        self.max_seq_length = max_seq_length

    def __getitem__(self, index):

        # Tokenize the sequence
        tokenized_source = self.tokenizer.tokenize(self.x_y_list[0][index])
        tokenized_target = self.tokenizer.tokenize(self.x_y_list[1][index])

        # If sequence to long -> CUT
        if len(tokenized_source) > self.max_seq_length - 4:
            tokenized_source = tokenized_source[:self.max_seq_length - 4]

        if len(tokenized_target) > self.max_seq_length - 4:
            tokenized_target = tokenized_target[:self.max_seq_length - 4]

        # Append Begin of Sentence end of Sentence and  BERT special tokens
        tokenized_source = ["[CLS]"] + ["[unused9]"] + tokenized_source + ["[unused89]"] + ["[SEP]"]
        tokenized_target = ["[CLS]"] + ["[unused9]"] + tokenized_target + ["[unused89]"] + ["[SEP]"]

        # Convert tokens to ids
        ids_source = self.tokenizer.convert_tokens_to_ids(tokenized_source)
        ids_target = self.tokenizer.convert_tokens_to_ids(tokenized_target)

        # Create BERT specific segment id (Always 0). BERT can also be used for 2 sentences, then its 0's and 1's.
        segment_ids_source = [0] * len(ids_source)
        segment_ids_target = [0] * len(ids_target)

        # create input mask
        mask_source = [1] * len(ids_source)
        mask_target = [1] * len(ids_target)

        # create no peak mask
        nopeak_mask = []
        for i in range(0, self.max_seq_length):
            temp = []
            for j in range(0, self.max_seq_length):
                if j <= i and j < len(ids_target):
                    temp.append(1)
                else:
                    temp.append(0)
            nopeak_mask.append(temp)

        # Zero-pad up to the sequence length.
        padding = [0] * (self.max_seq_length - len(ids_source))
        ids_source += padding
        segment_ids_source += padding
        mask_source += padding

        padding = [0] * (self.max_seq_length - len(ids_target))
        ids_target += padding
        segment_ids_target += padding
        mask_target += padding

        ids_source = torch.tensor(ids_source)
        segment_ids_source = torch.tensor(segment_ids_source)
        mask_source = torch.tensor(mask_source)
        ids_target = torch.tensor(ids_target)
        segment_ids_target = torch.tensor(segment_ids_target)
        mask_target = torch.tensor(mask_target)
        nopeak_mask = torch.tensor(nopeak_mask)

        if torch.cuda.is_available():
            ids_source = ids_source.cuda()
            segment_ids_source = segment_ids_source.cuda()
            mask_source = mask_source.cuda()
            ids_target = ids_target.cuda()
            segment_ids_target = segment_ids_target.cuda()
            mask_target = mask_target.cuda()
            nopeak_mask = nopeak_mask.cuda()

        return ids_source, segment_ids_source, mask_source, ids_target, segment_ids_target, mask_target, nopeak_mask

    def __len__(self):
        return len(self.x_y_list[0])


def create_nopeak_mask(size):
    nopeak_mask = []
    for i in range(0, size):
        temp = []
        for j in range(0, size):
            if j <= i:
                temp.append(1)
            else:
                temp.append(0)
        nopeak_mask.append(temp)

    return nopeak_mask


def get_data_loaders(tokenizer, max_seq_len, batch_size=1):
    # Load Dataset
    df = pd.DataFrame()
    for chunk in pd.read_csv('data/cnn_data.csv', header=0, names=['article', 'summary'], chunksize=1000,
                             converters={i: str for i in range(2)}):
        df = pd.concat([df, chunk], ignore_index=True)
    X = df["article"].to_list()
    y = df["summary"].to_list()

    # Train / Test split the Dataset
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.10, random_state=42)

    # Create TextDatasets with the Train / Test Splits
    train_lists = [X_train, y_train]
    test_lists = [X_test, y_test]
    training_dataset = TextDataset(train_lists, tokenizer, max_seq_len)
    test_dataset = TextDataset(test_lists, tokenizer, max_seq_len)

    # Create DataLoaders / Batches of the Train & Test (Validation) Data
    dataloaders_dict = {
        'train': torch.utils.data.DataLoader(training_dataset, batch_size=batch_size, shuffle=True, num_workers=0),
        'val': torch.utils.data.DataLoader(test_dataset, batch_size=batch_size, shuffle=True, num_workers=0)
    }
    dataset_sizes = {
        'train': len(train_lists[0]),
        'val': len(test_lists[0])
    }

    return dataloaders_dict, dataset_sizes
