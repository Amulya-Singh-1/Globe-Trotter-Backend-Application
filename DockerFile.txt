# Use Ubuntu for building
FROM ubuntu:latest AS build

# Set working directory
WORKDIR /app

# Install dependencies
RUN apt-get update && apt-get install -y openjdk-11-jdk curl unzip

# Copy the source code
COPY . .

# Give execution permission to Gradle wrapper
RUN chmod +x ./gradlew

# Build the JAR file
RUN ./gradlew bootJar --no-daemon

# Use a smaller image for the final run
FROM openjdk:11-jdk-slim

# Set working directory
WORKDIR /app

# Expose port 8080
EXPOSE 8080

# Copy JAR from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
