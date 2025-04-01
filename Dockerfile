FROM openjdk:17-jdk-slim
WORKDIR /app
EXPOSE 8081
COPY target/*.jar /app/BookOnTheGO-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "/app/BookOnTheGO-0.0.1-SNAPSHOT.jar"]