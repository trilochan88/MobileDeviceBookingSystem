FROM maven:3.8.4-openjdk-17 AS builder
COPY ./ /usr/src/app
WORKDIR /usr/src/app

RUN mvn clean package -DskipTests

FROM openjdk:17-slim

COPY --from=builder /usr/src/app/target/*.jar /app/app.jar

WORKDIR /app

EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]
