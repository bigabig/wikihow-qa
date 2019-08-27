# docker build -t bigabig/wikihow-server .
# docker run -p8080:8080 --name=wikihow-server -d bigabig/wikihow-server
# visit http://localhost:8080/wikihowqa/swagger-ui.html

FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY wikihowqa-1.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
