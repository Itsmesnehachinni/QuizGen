# Use a base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file
COPY target/pharma-service-0.0.1-SNAPSHOT.jar pharma-service-0.0.1-SNAPSHOT.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "pharma-service-0.0.1-SNAPSHOT.jar"]