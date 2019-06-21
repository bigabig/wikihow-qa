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
from Models import BertTransformer

max_seq_length = 512
batch_size = 16
device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu") # Figure out whether to use the GPU or CPU


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


# Activate Logging
logging.basicConfig(level=logging.INFO)

# load bert tokenizer (vocabulary)
tokenizer = BertTokenizer.from_pretrained('bert-base-uncased')

# Load Dataset
df = pd.DataFrame()
for chunk in pd.read_csv('data/cnn_data.csv', header=0, names=['article', 'summary'], chunksize=1000):
    df = pd.concat([df, chunk], ignore_index=True)
X = df["article"].to_list()
y = df["summary"].to_list()

# Train / Test split the Dataset
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.10, random_state=42)

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

# Create A Configuration for BERT
config = BertConfig(vocab_size_or_config_json_file=32000, hidden_size=768,
                    num_hidden_layers=12, num_attention_heads=12, intermediate_size=3072)

# Create the BERT model
model = BertTransformer(trg_vocab, 768, 3, 12, 0.1)

model.to(device)

# Create A Configuration for BERT
config = BertConfig(vocab_size_or_config_json_file=32000, hidden_size=768,
                    num_hidden_layers=12, num_attention_heads=12, intermediate_size=3072)

# Define Learning Rates (different LRs for Bert and Classifier)
lrlast = .001
lrmain = .00001

# Define Optimizer (different LRs for Bert and Classifier)
optim1 = optim.Adam([
        {"params": model.bert.parameters(), "lr": lrmain},
        {"params": model.classifier.parameters(), "lr": lrlast}
])
# optim1 = optim.Adam(model.parameters(), lr=0.001)#,momentum=.9) # Same LR for whole Model!
optimizer_ft = optim1

# Decay LR by a factor of 0.1 every 7 epochs
exp_lr_scheduler = lr_scheduler.StepLR(optimizer_ft, step_size=3, gamma=0.1)

# Define Loss Function
criterion = nn.CrossEntropyLoss()

# Train the model
model_ft1 = train_model(model, criterion, optimizer_ft, exp_lr_scheduler, num_epochs=num_epochs)