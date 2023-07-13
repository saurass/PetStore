# Use a base image with Java 17 installed
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

#make directory to save image
RUN mkdir upload-dir

# Copy the Spring Boot application JAR file into the container
COPY ./target/petpicserv-0.0.1-SNAPSHOT.jar app.jar

# Expose the port on which your application listens
EXPOSE 9090

# Set any additional environment variables if required
# ENV VARIABLE_NAME=value

# Run the Spring Boot application when the container starts
CMD ["java", "-jar", "app.jar"]
