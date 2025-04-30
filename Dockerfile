# Use a pinned digest for reproducibility
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the application JAR file
COPY target/house-management-api-0.0.2.jar house-management-api.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "house-management-api.jar"]