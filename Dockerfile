# syntax=docker/dockerfile:1

FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw ./
RUN chmod +x mvnw
RUN ./mvnw -q  dependency:go-offline

COPY src src
RUN ./mvnw -q clean package

FROM eclipse-temurin:21-jre
WORKDIR /app

RUN addgroup --system spring && adduser --system spring --ingroup spring

COPY --from=build /app/target/*.jar app.jar
COPY wallet wallet

EXPOSE 8081
USER spring:spring

ENTRYPOINT ["java", "-jar", "/app/app.jar"]