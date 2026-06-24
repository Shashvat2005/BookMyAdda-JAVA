FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 7860

CMD ["java", "-jar", "app.jar", "--server.port=7860"]