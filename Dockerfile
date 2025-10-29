# Étape 1 : build Maven
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : image finale runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8081

# Profil Spring actif via variable d'environnement
ENV SPRING_PROFILES_ACTIVE=local

ENTRYPOINT ["java", "-jar", "app.jar"]
