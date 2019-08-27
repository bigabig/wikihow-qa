# TextRank
This is a microservice for extractive summarization with TextRank.

## BUILD DOCKER
- Change into correct diretory: cd wikihow-qa/services/wikihow-textrank
- Build the container: docker build -t bigabig/wikihow-textrank:latest .
- (Optional) Test if the container is working:
  - Run the container: docker run -p5000:5000 bigabig/wikihow-textrank:latest
  - View example output: visit http://localhost:5000/
