from __future__ import print_function, division
from pytorch_pretrained_bert import BertTokenizer, BertModel, BertForMaskedLM, BertConfig
from sklearn.model_selection import train_test_split
from torch.utils.data import Dataset, DataLoader
import logging
import torch
import torch.nn as nn
import torch.optim as optim
from torch.optim import lr_scheduler
import numpy as np
import time
import copy
import torch.nn.functional as F
import pandas as pd

# HYPER PARAMETERS
max_seq_length = 512
batch_size = 16


class TextDataset(Dataset):
    def __init__(self, x_y_list, transform=None):
        self.x_y_list = x_y_list
        self.transform = transform

    def __getitem__(self, index):
        tokenized_review = tokenizer.tokenize(self.x_y_list[0][index])

        if len(tokenized_review) > max_seq_length:
            tokenized_review = tokenized_review[:max_seq_length]

        ids_review = tokenizer.convert_tokens_to_ids(tokenized_review)

        padding = [0] * (max_seq_length - len(ids_review))

        ids_review += padding

        assert len(ids_review) == max_seq_length

        # print(ids_review)
        ids_review = torch.tensor(ids_review)

        sentiment = self.x_y_list[1][index]  # color
        list_of_labels = [torch.from_numpy(np.array(sentiment))]

        return ids_review, list_of_labels[0]

    def __len__(self):
        return len(self.x_y_list[0])


# Load pre-trained model tokenizer (vocabulary)
tokenizer = BertTokenizer.from_pretrained('bert-base-uncased')

# Load Dataset
dat = pd.read_csv('data.tsv', sep='\t')
X = dat['article']
y = dat['summary']

# Train / Test split the Dataset
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.10, random_state=42)
X_train = X_train.values.tolist()
X_test = X_test.values.tolist()
y_train = pd.get_dummies(y_train).values.tolist()
y_test = pd.get_dummies(y_test).values.tolist()

# Create TextDatasets with the Train / Test Splits
train_lists = [X_train, y_train]
test_lists = [X_test, y_test]
training_dataset = TextDataset(x_y_list=train_lists)
test_dataset = TextDataset(x_y_list=test_lists)

# Create DataLoaders / Batches of the Train & Test (Validation) Data
dataloaders_dict = {
    'train': torch.utils.data.DataLoader(training_dataset, batch_size=batch_size, shuffle=True, num_workers=0),
    'val': torch.utils.data.DataLoader(test_dataset, batch_size=batch_size, shuffle=True, num_workers=0)
}
dataset_sizes = {
    'train': len(train_lists[0]),
    'val': len(test_lists[0])
}
