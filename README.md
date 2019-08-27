# WikiHow QA
Question Answering System based on WikiHow

## Set-up WikiHow QA

### Step 0: Requirements
To run the Wikihow QA Application, you need to install the following:
- Git
- Docker
- docker-compose
- Python3
- Python3 virtual environment (venv)

Also, you need to clone this repo:
- git clone https://github.com/bigabig/wikihow-qa.git

### Step 1: Set-up the docker network
The WikiHow QA Application consists of a network of many different docker containers. There are two ways to get all needed docker containers:
1. Easy: Download all necessary docker containers from Docker Hub
2. Advanced: Build all docker containers yourself
Building all docker containers will take significantly more time. Skip Step 1.1 if you do not want to follow the advanced instructions.

#### Step 1.1 Building Docker containers (Advanced, you can skip this)
This is the advanced guide on building all docker containers yourself. The provided links contain detailed instructions on how to build the respective container.
- Build the Frontend: [LINK](frontend/README.md)
- Build the Backend: [LINK](backend/README.md)
- Build the Microservices
  - TextRank Summarization: [LINK](services/wikihow-textrank/README.md)
  - Pointer-Generator Summarization: [LINK](services/wikihow-network/README.md)
  - BERT Summarization: [LINK](services/wikihow-bertsum/README.md)
  - Named Entity Recognition: [LINK](services/wikihow-ner/README.md)
  - Keyword Extraction: [LINK](services/wikihow-keywords/README.md)
  - ROUGE Evaluation: [LINK](services/wikihow-evaluation/README.md)

#### Step 1.2 Start the Docker containers
- Navigate to wikihow-qa/docker
- Download & start all necessary containers: docker-compose up -d
- Check if all containers are running: docker ps
  - you should see: elasticsearch, mysql, server, textrank, network, bertsum, ner, eval, keywords

### Step 2: Import the data
As the name suggests, the Wikihow QA Application uses WikiHow as the main dataset. For the application to work, it is necessary to import the data into the elasticsearch docker. The original dataset can be found here: [LINK](https://github.com/mahnazkoupaee/WikiHow-Dataset)
- Download the dataset here: [LINK](https://drive.google.com/file/d/1VvPrW1MZAS9PHdUWridi6XBXzLJZdmUk/view?usp=sharing)
- Unzip the archive to wikihow-qa/
  - Now you should have a folder wikihow-qa/dataset/articles_full/ that contains many *.json files.
- Navigate to tools: cd wikihow-qa/tools
- Create new virtual environment: python -m venv env
  - Please make sure that you use Python > 3, you might need to type python3 instead of python! Check version with python3 --version or python --version
- Activate the new virtual environment: source env/bin/activate
- Install dependencies: pip install -r requirements.txt
  - Please make sure that you use the correct pip in the following command. You might need to type pip3 instead of pip!
- Before executing the import script, please make sure that the elasticsearch docker is running: docker ps
- Import all data: python import_wikihow_elasticsearch_autocomplete.py -d wikihow-qa/dataset/articles_full/ -i autohow2 -t 60
- Once the import is finished, you can deactivate the virtual environment: deactivate

### Step 3: Use the application
All docker containers are running, the data is imported... You are now ready to use the WikiHow QA Application! You can find a detailed user guide that helps you exploring all functionalities here: [LINK](USER_GUIDE.md)
- Use the application: http://localhost:8080/wikihowqa/index.html
- Or view the REST API: http://localhost:8080/wikihowqa/swagger-ui.html
- Now you can start & stop the application as you like
  - cd wikihow-qa/docker/
  - Start: docker-compose up -d
  - Stop: docker-compose down
