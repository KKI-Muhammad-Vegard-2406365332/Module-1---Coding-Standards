# Stage 1: Build the application
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY . .
# Give execution permission to the gradlew script
RUN chmod +x ./gradlew
# Build the application, skipping tests to speed up deployment
RUN ./gradlew clean bootJar -x test

# Stage 2: Run the application
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copy the built jar file from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar
# Expose the port (Koyeb usually defaults to 8000 or 8080)
EXPOSE 8080
# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]