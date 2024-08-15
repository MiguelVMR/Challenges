FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/agenda-0.0.1-SNAPSHOT.jar /app/agenda.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/agenda.jar", "--spring.profiles.active=docker"]
