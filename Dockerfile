# Use a base image with OpenJDK 17 installed
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project file(s) into the container
COPY pom.xml .

# Download the project dependencies
RUN mvn dependency:go-offline

# Copy the source code into the container
COPY src ./src

# Build the Spring Boot application
RUN mvn package -DskipTests


# Use a base image with Java 17 installed
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

#make directory to save image
RUN mkdir upload-dir

# Copy the Spring Boot application JAR file from the build stage
COPY --from=build /app/target/petpicserv-0.0.1-SNAPSHOT.jar app.jar

# Expose the port on which your application listens
EXPOSE 9090

# Set any additional environment variables if required
# ENV VARIABLE_NAME=value

# Run the Spring Boot application when the container starts
CMD ["java", "-jar", "app.jar"]
