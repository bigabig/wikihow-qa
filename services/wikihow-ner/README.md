# Named Entity Recognition
This is a microservice for english NER.

## BUILD DOCKER
- Change into correct diretory: cd wikihow-qa/services/wikihow-ner
- Build the container: docker build -t bigabig/wikihow-ner:latest .
- (Optional) Test if the container is working:
  - Run the container: docker run -p5000:5000 bigabig/wikihow-ner:latest
  - View example output: visit http://localhost:5000/
