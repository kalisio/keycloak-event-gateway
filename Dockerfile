# File: keycloak-event-gateway/Dockerfile
#
# A minimal image to embed the “keycloak-event-gateway.jar” file
#

FROM alpine:3.14

ENV ARTIFACT_VERSION 0.0.1-SNAPSHOT

RUN mkdir /artifacts/

COPY target/keycloak-event-gateway-${ARTIFACT_VERSION}.jar /artifacts/

