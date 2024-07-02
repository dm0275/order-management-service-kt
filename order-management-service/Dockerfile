FROM amazoncorretto:17-alpine

ARG ARTIFACT_DIR
ARG ARTIFACT_NAME
ARG SERVICE_DIR=/opt/service

RUN apk update \
    && mkdir -p ${SERVICE_DIR}

WORKDIR ${SERVICE_DIR}

COPY start.sh start.sh
COPY ${ARTIFACT_DIR}/${ARTIFACT_NAME} order-service.jar

EXPOSE 8080

ENTRYPOINT ["sh", "start.sh"]