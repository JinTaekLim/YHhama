FROM openjdk:17-jdk
LABEL maintainer="email"
ARG JAR_FILE=build/libs/yeohaenghama-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} docker-springboot.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/docker-springboot.jar"]


ENV MYSQLSERVER 118.67.132.49
ENV MYSQLUSERNAME root
ENV MYSQLPASSWORD yhhama
ENV REDISSERVER 118.67.132.49
ENV REDISPORTNUM 6379