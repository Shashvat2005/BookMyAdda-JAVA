# Use modern Java image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Give permission to mvnw
RUN chmod +x mvnw

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose port (Render uses dynamic port)
EXPOSE 8080

# Run the app
CMD ["sh", "-c", "java -jar target/*.jar"]