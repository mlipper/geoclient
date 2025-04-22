# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jre AS builder

# Default for JARFILE assumes Docker context is the root project directory.
ARG JARFILE
ENV JARFILE="${JARFILE:-./geoclient-service/build/libs/geoclient.jar}"

WORKDIR /app
COPY "${JARFILE}" geoclient.jar
COPY --chmod=755 images/run.sh .

RUN set -ex \
  && java -Djarmode=layertools -jar ./geoclient.jar extract

FROM eclipse-temurin:17-jre

RUN set -ex \
  && apt-get update \
  && apt-get install --yes --no-install-recommends jq \
  && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY --from=builder app/run.sh .
COPY --from=builder app/dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/application/ ./

# Assumes a Geosupport installation has been mounted to /opt/geosupport.
ENV GEOSUPPORT_BASEDIR=/opt/geosupport
ENV GEOSUPPORT_HOME="${GEOSUPPORT_BASEDIR}/current"
ENV GEOFILES="${GEOSUPPORT_BASEDIR}/current/fls/"

ENTRYPOINT ["/app/run.sh"]
EXPOSE 8080