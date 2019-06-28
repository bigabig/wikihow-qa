# docker build -t bigabig/wikihow-keywords .
# docker run -p8090:8080 -d bigabig/wikihow-keywords
# visit http://localhost:8090/swagger-ui.html

FROM openjdk:8-jdk-alpine
VOLUME /tmp
#ARG JAR_FILE
#COPY ${JAR_FILE} app.jar
COPY ltkeyterms.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
