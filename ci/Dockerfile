# syntax=docker/dockerfile:1

FROM gradle:jdk21 AS builder

# Default for JARFILE assumes Docker context is the root project directory.
ARG JARFILE
ENV JARFILE="${JARFILE:-./geoclient-service/build/libs/geoclient.jar}"

ENV GEOSUPPORT_BASEDIR=/opt/geosupport

RUN set -ex \
  && apt-get update \
  && apt-get install --yes --no-install-recommends \
     gcc \
     g++ \
     libc6-dev \
  && rm -rf /var/lib/apt/lists/*

COPY --from=mlipper/geosupport-docker:latest-dist "/dist/geosupport.tgz" "${GEOSUPPORT_BASEDIR}/geosupport.tgz"

RUN set -eux \
  && tar xzvf "${GEOSUPPORT_BASEDIR}/geosupport.tgz" -C "${GEOSUPPORT_BASEDIR}" \
  && rm "${GEOSUPPORT_BASEDIR}/geosupport.tgz"

RUN set -eux \
  && SCRIPT=$(find ${GEOSUPPORT_BASEDIR}/version-*/bin/geosupport) \
  && [ -f "${SCRIPT}" ] || exit 1 \
  && /bin/bash -c set -eux \
  && "${SCRIPT}" install

WORKDIR /app
COPY . .

ENV GEOSUPPORT_HOME="${GEOSUPPORT_BASEDIR}/current"
ENV GEOFILES="${GEOSUPPORT_BASEDIR}/current/fls/"
ENV GS_BIN_PATH="${GEOSUPPORT_BASEDIR}/current/bin"
ENV GS_LIBRARY_PATH="${GEOSUPPORT_BASEDIR}/current/lib"
ENV GS_INCLUDE_PATH="${GEOSUPPORT_BASEDIR}/current/include"

RUN set -ex \
  && gradle clean build --continue testAggregateTestReport integrationTestAggregateTestReport testCodeCoverageReport integrationTestCodeCoverageReport \
  && mkdir -p ./dist/ci/test-results ./dist/ci/coverage \
  && [ -f "${JARFILE}" ] || exit 1 \
  && cp -v "${JARFILE}" /app/dist/geoclient.jar \
  && cp -R ./build/reports/tests/testAggregateTestReport /app/dist/ci/test-results/test \
  && cp -R ./build/reports/tests/integrationTestAggregateTestReport /app/dist/ci/test-results/integrationTest \
  && cp -R ./build/reports/jacoco/testCodeCoverageReport /app/dist/ci/coverage/test \
  && cp -R ./build/reports/jacoco/integrationTestCodeCoverageReport /app/dist/ci/coverage/integrationTest

### Run
FROM eclipse-temurin:21-jre AS runner

RUN set -ex \
  && apt-get update \
  && apt-get install --yes --no-install-recommends jq \
  && rm -rf /var/lib/apt/lists/*

ENV GEOSUPPORT_BASEDIR=/opt/geosupport
ENV GEOSUPPORT_HOME="${GEOSUPPORT_BASEDIR}/current"
ENV GEOFILES="${GEOSUPPORT_BASEDIR}/current/fls/"

COPY --from=builder "${GEOSUPPORT_BASEDIR}" "${GEOSUPPORT_BASEDIR}"

RUN set -eux \
  && "${GEOSUPPORT_HOME}/bin/geosupport" install

WORKDIR /app

ENV CI_DIR=/app/ci
ENV JARFILE=/app/geoclient.jar

COPY --from=builder /app/dist/geoclient.jar ${JARFILE}
COPY --from=builder /app/dist/ci ${CI_DIR}

RUN set -ex \
  && [ -f ${JARFILE} ] || (echo "ERROR: ${JARFILE} does not exist."; exit 1) \
  && [ -d ${CI_DIR} ] || (echo "ERROR: ${CI_DIR} does not exist."; exit 1) \
  && [ -d ${CI_DIR}/coverage ] || (echo "ERROR: ${CI_DIR}/coverage does not exist."; exit 1) \
  && [ -d ${CI_DIR}/test-results ] || (echo "ERROR: ${CI_DIR}/test-results does not exist."; exit 1)

ENTRYPOINT ["java", "-Dgc.jni.version=geoclient-jni-2", "-Dspring.profiles.active=default", "-Xmx2048m", "-jar", "/app/geoclient.jar"]
EXPOSE 8080
