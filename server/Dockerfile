# Start with a minimal Alpine Linux base image
FROM alpine:latest AS build

# Install necessary packages and dependencies
RUN apk update && apk add --no-cache \
    openjdk21-jdk \
    maven \
    bash \
    && apk add --no-cache --virtual .build-deps \
    git \
    && apk del .build-deps

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files to the working directory
COPY ../pom.xml .
COPY ../src ./src

# Download Maven dependencies and build the application (without running tests)
RUN mvn clean package -DskipTests

# Use a minimal base image for the runtime
FROM amazoncorretto:21-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built jar file from the build stage
COPY --from=build /app/target/shareit-0.0.1-SNAPSHOT.jar ./shareit.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "shareit.jar"]
