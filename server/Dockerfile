FROM openjdk:11-jdk-slim
MAINTAINER Jan Lishak
VOLUME /tmp
EXPOSE 8080
ADD ./build/gh-core*.jar gh-core.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container", "-jar", "/gh-core.jar"]
