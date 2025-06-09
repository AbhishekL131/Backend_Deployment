# Use OpenJDK base image
FROM openjdk:17-jdk-slim

# Add a label
LABEL maintainer="Abhishek Londhe"

# Set app jar location in container
COPY target/Library-0.0.1-SNAPSHOT.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
