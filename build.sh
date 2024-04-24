#!/bin/sh

# File: keycloak-event-gateway/build.sh
#

set -e

mvn install

docker build -t kalisio/keycloak-event-gateway .
