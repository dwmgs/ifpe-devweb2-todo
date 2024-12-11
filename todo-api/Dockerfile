# Use a imagem base do Maven para compilar o projeto
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY ./pom.xml /app/pom.xml
COPY todo-api/pom.xml /app/todo-api/pom.xml
COPY todo-api/src /app/todo-api/src
RUN mvn -f /app/todo-api/pom.xml clean package -DskipTests

# Use a imagem base do OpenJDK para rodar o projeto
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/todo-api/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]