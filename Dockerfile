FROM openjdk:17-jdk
ENV JAVA_OPTS="-Xmx2g -Xms1g"
LABEL maintainer="email"
ARG JAR_FILE=build/libs/yeohaenghama-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} docker-springboot.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/docker-springboot.jar"]


ENV MYSQLSERVER 43.202.208.22
ENV MYSQLUSERNAME root
ENV MYSQLPASSWORD yhhama
ENV REDISSERVER 43.202.208.22
ENV REDISPASSWORD YHhama
ENV REDISPORTNUM 6379