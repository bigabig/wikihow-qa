# Building the BertSum Docker
Code from https://github.com/nlpyang/BertSum
Thank you Yang Liu for sharing the pre-trained model BERTSUM+Classifier with me!

- Download Stanford CoreNLP Version 3.8.0 from 2017-06-09 https://stanfordnlp.github.io/CoreNLP/history.html
- Extract all the contents of 2017-06-09 to /corenlp/
- Write me an E-Mail to get the pre-trained model
- Unzip the pre-trained model to /models/ and rename it to model_step_1.pt
- Build the docker image: docker build -t bertsum .
- (Optional) Run the docker: docker run -p5000:5000 bertsum
- (Optional) Visit http://localhost:5000/ and check if it works :)