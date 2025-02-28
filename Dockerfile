# First stage: build the application
FROM maven:3.9.9-eclipse-temurin-21 AS build
COPY . /app
WORKDIR /app
RUN mvn package -DskipTests

# Second stage: create image
FROM openjdk:21-slim
COPY --from=build /app/target/umg-backend-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]