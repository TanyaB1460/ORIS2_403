FROM openjdk:19-jdk-alpine
WORKDIR /app
COPY target/lab2_8_nats-1.0-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]