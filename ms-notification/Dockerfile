FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app/ms-notification
COPY ./ms-notification ./

RUN mvn clean package -DskipTests


FROM openjdk:21
WORKDIR /app
COPY --from=builder /app/ms-notification/target/*.jar ./app.jar
ENTRYPOINT java -jar app.jar