FROM openjdk:11-jre-slim

ARG JAR_FILE=target/*.jar
ADD ./target/readingisgood-1.0.0.jar readingisgood.jar

ENTRYPOINT ["java", "-jar", "/readingisgood.jar"]

