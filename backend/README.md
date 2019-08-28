# BACKEND
This is the "server", "backend" or "broker".
It's main task is to combine all available services into one REST API. 
The REST API can be viewed at http://localhost:8080/wikihowqa/swagger-ui.html.
Since the "server" is also a webserver, the other task is to host the frontend.

## BUILD JAR FILE
This is a short instruction on building the backend standalone .jar file. This is a necessary step before building the docker container.
- Please note that it is required to build the frontend first. See http://frontend/ for the instructions.
- Change into correct diretory: cd wikihow-qa/backend
- Build a .jar with dependencies with maven: mvn assembly:assembly -f pom.xml
- Move the .jar to docker dir: mv target/wikihowqa-1.0.jar docker/

## BUILD DOCKER
This is a short instruction on building the backend docker container:
- Please note that it is required to build the .jar file first. See above for the instructions.
- Change into correct diretory: cd wikihow-qa/backend/docker
- Build the docker container: docker build -t bigabig/wikihow-server .
- (Optional) check if everything is working
  - cd wikihow-qa/docker
  - docker-compose up -d
  - test if REST API is available: visit http://localhost:8080/wikihowqa/swagger-ui.html
  - test if frontend is available: visit http://localhost:8080/wikihowqa/index.html
