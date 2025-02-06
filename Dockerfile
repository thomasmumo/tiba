FROM openjdk:17-jdk-slim

EXPOSE 8080
ADD target/tiba.jar tiba.jar
ENTRYPOINT ["java", "-jar", "/tiba.jar"]