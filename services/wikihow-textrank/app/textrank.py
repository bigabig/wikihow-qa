import numpy as np
import pandas as pd
import nltk
import networkx as nx
import os
import sys
from nltk.tokenize import sent_tokenize
from nltk.corpus import stopwords
from sklearn.metrics.pairwise import cosine_similarity

# one time downloads
nltk.download('punkt')
nltk.download('stopwords')

# global variables
stop_words = stopwords.words('english')
word_embeddings = {}


# function to load GLOVE embeddings
def load_glove_embeddings():
    # load glove word embeddings
    print("Loading GLOVE Embeddings...")
    f = open(os.path.join(sys.path[0], 'glove.6B.100d.txt'), encoding='utf-8')
    for line in f:
        values = line.split()
        word = values[0]
        coefs = np.asarray(values[1:], dtype='float32')
        word_embeddings[word] = coefs
    f.close()
    print("Finished Loading GLOVE Embeddings!")


# load embeddings
load_glove_embeddings()


# function to remove stopwords from one sentence
def remove_stopwords(sen):
    sen_new = " ".join([i for i in sen if i not in stop_words])
    return sen_new


# function to generate a summary from text with sentence length n
def generate_summary(text, n):
    # split input text into sentences
    sentences = sent_tokenize(text)
    print(sentences)

    # clean the sentences
    # remove punctuations, numbers and special characters
    clean_sentences = pd.Series(sentences).str.replace("[^a-zA-Z]", " ")

    # make alphabets lowercase
    clean_sentences = [s.lower() for s in clean_sentences]

    # remove stopwords
    clean_sentences = [remove_stopwords(r.split()) for r in clean_sentences]
    print(clean_sentences)

    # create sentence vectors
    sentence_vectors = []
    for sentence in clean_sentences:
        if len(sentence) != 0:
            sentence_vector = np.zeros((100,))
            for word in sentence.split():
                word_embedding = word_embeddings.get(word, np.zeros((100,)))
                sentence_vector += word_embedding
            sentence_vector = sentence_vector / (len(sentence.split()) + 0.001)
        else:
            sentence_vector = np.zeros((100,))
        sentence_vectors.append(sentence_vector)
        print("Sentence:"+str(sentence)+" Embedding:"+str(sentence_vector))

    # create similarity matrix
    sim_mat = np.zeros([len(sentences), len(sentences)])

    # init similarity matrix with cosine similarities of all sentences
    print("Calculating initial similarity...")
    for i in range(len(sentences)):
        for j in range(len(sentences)):
            if i != j:
                sim_mat[i][j] = cosine_similarity(sentence_vectors[i].reshape(1, 100), sentence_vectors[j].reshape(1, 100))[0, 0]
    print(sim_mat)

    # use pagerank on the simmilarity matrix
    nx_graph = nx.from_numpy_array(sim_mat)
    print("Starting pagerank algorithm...")
    scores = nx.pagerank(nx_graph)
    print("Finished calculating pagerank!")
    print(scores)

    # map sentences to scores
    ranked_sentences = sorted(((scores[i], s) for i, s in enumerate(sentences)), reverse=True)
    print(ranked_sentences)

    # Generate Summary
    print("Printing summarization...")
    summary = ""
    for i in range(n):
        summary += str(ranked_sentences[i][1]) + "\n"
        print("Sentence:"+str(ranked_sentences[i][1])+" Score:"+str(ranked_sentences[i][0]))

    # return summary
    return summary.strip()
