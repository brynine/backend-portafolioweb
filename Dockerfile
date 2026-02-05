# Etapa 1: construir el .war con Maven
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: desplegar en WildFly
FROM jboss/wildfly:latest
COPY --from=build /app/target/*.war /opt/jboss/wildfly/standalone/deployments/
