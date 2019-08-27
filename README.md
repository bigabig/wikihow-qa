# WikiHow QA
Question Answering System based on WikiHow

## Set-up WikiHow QA

### Step 1: Set-up the docker network
The WikiHow QA Application consists of a network of many different docker containers. There are two ways to get all needed docker containers:
1) Easy: Download all necessary docker containers from Docker Hub
2) Advanced: Build all docker containers yourself
Both ways work. However building all docker containers will take significantly more time.

#### 1) Using Docker-Hub


#### 2) Building Docker containers
This is the advanced guide on building all docker containers yourself. The provided links contain detailed instructions on how to build the respective container.
1. Build the Frontend: https://github.com/bigabig/wikihow-qa/tree/master/frontend#building-the-frontend
2. Build the Backend: https://github.com/bigabig/wikihow-qa/tree/master/backend#build-jar-file
3. Building the Microservices (in any order) 
  - TextRank Summarization: 
  - Pointer-Generator Summarization:
  - BERT Summarization:
  - Named Entity Recognition:
  - Keyword Extraction:
  - ROUGE Evaluation

### Step 2: Import the data
As the name suggests, the Wikihow QA Application uses WikiHow as the main dataset. For the application to work, it is necessary to import the data into the elasticsearch docker.


###
   
## Dataset
The dataset used for building this system can be found here: https://github.com/mahnazkoupaee/WikiHow-Dataset
