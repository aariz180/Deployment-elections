# Backend Build Stage
FROM maven:3.9.9 AS backend-builder

WORKDIR /elections-backend

# Copy necessary files using full paths from the build context
COPY elections-backend/pom.xml ./pom.xml
COPY elections-backend/src ./src
COPY elections-backend/build-running.txt ./build-running.txt
COPY elections-backend/build-success.txt ./build-success.txt

# Download dependencies and build the application
RUN mvn clean install -DskipTests

# Backend Runtime Stage
FROM openjdk:21-jdk-slim AS backend-runtime

WORKDIR /elections-backend

# Copy the built JAR file from the builder stage
COPY --from=backend-builder /elections-backend/target/elections-backend-0.0.1-SNAPSHOT.jar ./app.jar

# Set the port and expose it
ENV PORT=8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT} -jar ./app.jar"]
