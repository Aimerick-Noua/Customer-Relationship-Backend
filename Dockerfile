# Maven Build Stage
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the local project directory into the container
COPY ./app/ /app/

# Run Maven build
RUN mvn clean package

# Java Image Stage
FROM openjdk:17-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage to the new image
COPY --from=build /app/target/*.jar /app/app.jar

# Expose the port
EXPOSE 8082

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
