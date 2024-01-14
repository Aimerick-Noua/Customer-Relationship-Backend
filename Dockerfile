# Maven Build Stage
FROM maven:3.1.5-openjdk-17 AS build
WORKDIR /app
COPY ./app/ /app/
RUN mvn clean package

# Java Image Stage
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
