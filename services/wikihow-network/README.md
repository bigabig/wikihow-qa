# Pointer-Generator Network
This is a microservice for abstractive summarization with a Pointer-Generator-Network.

## BUILD DOCKER
- Write me an E-Mail to get the pre-trained model + vocabulary
- Unzip the archive to wikihow-qa/services/wikihow-network/
  - Now you should have this folder: wikihow-qa/services/wikihow-network/models
  - Also you should have this file: wikihow-qa/services/wikihow-network/vocab
- Change into correct diretory: cd wikihow-qa/services/wikihow-network
- Build the container: docker build -t bigabig/wikihow-network:latest .
- (Optional) Test if the container is working:
  - Run the container: docker run -p5000:5000 bigabig/wikihow-network:latest
  - View example output: visit http://localhost:5000/
