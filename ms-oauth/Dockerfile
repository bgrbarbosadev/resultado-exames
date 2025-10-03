FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app/ms-oauth
COPY ./ms-oauth ./

RUN mvn clean package -DskipTests


FROM openjdk:21
WORKDIR /app
COPY --from=builder /app/ms-oauth/target/*.jar ./app.jar
ENTRYPOINT java -jar app.jar