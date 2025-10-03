FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app/ms-gateway
COPY ./ms-gateway ./

RUN mvn clean package -DskipTests


FROM openjdk:21
WORKDIR /app
COPY --from=builder /app/ms-gateway/target/*.jar ./app.jar
ENTRYPOINT java -jar app.jar