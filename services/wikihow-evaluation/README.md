# Wikihow Evaluation
This is a microservice for ROUGE Evaluation.

## BUILD DOCKER
- Change into correct diretory: cd wikihow-qa/services/wikihow-evaluation/
- Build the container: docker build -t bigabig/wikihow-eval:latest .
- (Optional) Test if the container is working:
  - Run the container: docker run -p9000:9000 bigabig/wikihow-eval:latest
  - View example output: visit http://localhost:9000/
