# Use a more platform-compatible base image
FROM openjdk:17-jdk-slim

# Maintainer label (instead of deprecated MAINTAINER)
LABEL maintainer="saycheese.com"

# Copy the jar file into the container
COPY target/books.jar app.jar

# Expose the application's port (optional)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app.jar"]
