# Backend Build Stage
FROM maven:3.9.9 AS backend-builder

WORKDIR /elections-backend

# Copy only necessary files
COPY pom.xml ./
COPY src ./src
COPY build-running.txt ./
COPY build-success.txt ./

# Build the application
RUN mvn clean install -DskipTests

# Backend Runtime Stage
FROM openjdk:21-jdk-slim AS backend-runtime

WORKDIR /elections-backend

# Copy the built JAR file
COPY --from=backend-builder /elections-backend/target/elections-backend-0.0.1-SNAPSHOT.jar /elections-backend/app.jar

# Set environment variable and expose port
ENV PORT=8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT} -jar /elections-backend/app.jar"]
