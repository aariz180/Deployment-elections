# Backend Build Stage
FROM maven:3.9.9 AS backend-builder

WORKDIR /elections-backend

# Cache Maven dependencies
COPY elections-backend/pom.xml ./
RUN mvn dependency:go-offline

# Copy source files and build
COPY elections-backend/src ./src
COPY ../build-running.txt .
COPY ../build-success.txt .
RUN mvn clean install -DskipTests

# Backend Runtime Stage
FROM openjdk:21-jdk-slim AS backend-runtime

WORKDIR /elections-backend

# Copy built JAR file
COPY --from=backend-builder /elections-backend/target/elections-backend-0.0.1-SNAPSHOT.jar /elections-backend/app.jar

# Set environment variable and expose port
ENV PORT=8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT} -jar /elections-backend/app.jar"]
