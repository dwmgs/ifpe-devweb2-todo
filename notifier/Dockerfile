# Use a imagem base do Maven para compilar o projeto
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY ./pom.xml /app/pom.xml
COPY notifier/pom.xml /app/notifier/pom.xml
COPY notifier/src /app/notifier/src
RUN mvn -f /app/notifier/pom.xml clean package -DskipTests

# Use a imagem base do OpenJDK para rodar o projeto
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/notifier/target/notifier-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]