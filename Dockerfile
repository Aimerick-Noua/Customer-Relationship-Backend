# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY ./ /app/
RUN mvn clean package

# Stage 2: Create the final image with only the JAR file
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8082  # Adjust the port based on your Spring Boot application configuration
ENTRYPOINT ["java", "-jar", "app.jar"]
