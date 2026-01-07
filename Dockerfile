# Start with the eclipse-temurin image (Standard replacement for openjdk)
FROM eclipse-temurin:17-jdk-jammy

# Information about who maintains the docker file
LABEL maintainer="sanjivani1212"

# Add the app's jar to the image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

# Execute the application
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]