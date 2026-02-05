FROM jboss/wildfly:latest
COPY target/*.war /opt/jboss/wildfly/standalone/deployments/
