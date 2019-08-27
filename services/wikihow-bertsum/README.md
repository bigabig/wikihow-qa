# BertSum
This is a microservice for extractive summarization with BERT. Most of the code is from https://github.com/nlpyang/BertSum. Thanks to Yang Liu for sharing the pre-trained model BERTSUM+Classifier with me!

## BUILD DOCKER
- Download Stanford CoreNLP Version 3.8.0 from 2017-06-09 https://stanfordnlp.github.io/CoreNLP/history.html
- Extract all the contents of Stanford CoreNLP to wikihow-qa/services/wikihow-bertsum/corenlp/, so that /corenlp/stanford-corenlp-3.8.0.jar is accessible
- Write me an E-Mail to get the pre-trained model
- Unzip the pre-trained model to /app/models/ and rename it to model_step_1.pt
- Build the docker image: 
 - cd wikihow-qa/services/wikihow-bertsum/
 - docker build -t bertsum .
- (Optional) Test if the container is working:
  - Run the docker: docker run -p5000:5000 bertsum
  - View example output: visit http://localhost:5000/
