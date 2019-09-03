FROM openjdk:latest
ENV APP_HOME=/root/dev/myapp/
ARG JAR_FILE
WORKDIR $APP_HOME
COPY ${JAR_FILE} app.jar
EXPOSE 8181
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]