# ================================
# Etapa 1: Construcción con Maven
# ================================
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar proyecto
COPY . .

# Compilar el .war
RUN mvn clean package -DskipTests


# ================================
# Etapa 2: WildFly
# ================================
FROM jboss/wildfly:38.0.1.Final

USER root

# Descargar driver PostgreSQL
RUN curl -L https://jdbc.postgresql.org/download/postgresql-42.7.3.jar \
    -o /opt/jboss/wildfly/standalone/deployments/postgresql.jar

# Crear módulo del driver
RUN mkdir -p /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main

RUN cp /opt/jboss/wildfly/standalone/deployments/postgresql.jar \
    /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main/

RUN echo '<?xml version="1.0" encoding="UTF-8"?> \
<module xmlns="urn:jboss:module:1.5" name="org.postgresql"> \
    <resources> \
        <resource-root path="postgresql.jar"/> \
    </resources> \
    <dependencies> \
        <module name="javax.api"/> \
        <module name="javax.transaction.api"/> \
    </dependencies> \
</module>' \
> /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main/module.xml

# Copiar el .war compilado
COPY --from=build /app/target/*.war \
/opt/jboss/wildfly/standalone/deployments/gproyecto.war

# Script para configurar datasource automáticamente
RUN echo '#!/bin/bash' > /opt/jboss/wildfly/bin/configure-ds.sh && \
    echo '/opt/jboss/wildfly/bin/jboss-cli.sh --connect <<EOF' >> /opt/jboss/wildfly/bin/configure-ds.sh && \
    echo 'module add --name=org.postgresql --resources=/opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main/postgresql.jar --dependencies=javax.api,javax.transaction.api' >> /opt/jboss/wildfly/bin/configure-ds.sh && \
    echo '/subsystem=datasources/jdbc-driver=postgresql:add(driver-name=postgresql,driver-module-name=org.postgresql,driver-class-name=org.postgresql.Driver)' >> /opt/jboss/wildfly/bin/configure-ds.sh && \
    echo '/subsystem=datasources/data-source=PostgresDS:add(jndi-name=java:jboss/datasources/PostgresDS,driver-name=postgresql,connection-url=jdbc:postgresql://dpg-d62ifqili9vc739ffsqg-a.oregon-postgres.render.com:5432/portafolio_db_u5pe,user-name=portafolio_db_u5pe_user,password=2uqLs4NlWWvOTJeQ7YBGnp0ND3G1mW3j)' >> /opt/jboss/wildfly/bin/configure-ds.sh && \
    echo 'run-batch' >> /opt/jboss/wildfly/bin/configure-ds.sh && \
    echo 'EOF' >> /opt/jboss/wildfly/bin/configure-ds.sh && \
    chmod +x /opt/jboss/wildfly/bin/configure-ds.sh

USER jboss

# Ejecutar configuración y luego arrancar WildFly
CMD ["/bin/bash", "-c", "/opt/jboss/wildfly/bin/standalone.sh & sleep 20 && /opt/jboss/wildfly/bin/configure-ds.sh && wait"]
