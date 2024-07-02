#!/usr/bin/sh

set -ex

java \
  ${JAVA_OPTS} \
  -jar order-service.jar