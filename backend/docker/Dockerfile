# docker build -t bigabig/wikihow-server .
# docker run -p8080:8080 --name=wikihow-server -d bigabig/wikihow-server
# visit http://localhost:8080/wikihowqa/swagger-ui.html

FROM openjdk:8-jdk-alpine
VOLUME /tmp
RUN apk add --update \
    curl \
    && rm -rf /var/cache/apk/*
COPY wait_to_start.sh wait_to_start.sh
COPY wikihowqa-1.0.jar app.jar
EXPOSE 8080
CMD [ "sh", "wait_to_start.sh" ]
# ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
