FROM openjdk:11-slim
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
