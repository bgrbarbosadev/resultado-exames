FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app/ms-customer
COPY ./ms-customer ./

RUN mvn clean package -DskipTests


FROM openjdk:21
WORKDIR /app
COPY --from=builder /app/ms-customer/target/*.jar ./app.jar
ENTRYPOINT java -jar app.jar