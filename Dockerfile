FROM openjdk:14-jdk-slim
WORKDIR /app
COPY target/product-discount-api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
