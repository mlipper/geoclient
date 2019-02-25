#
# 1. Before running this image create a volume with Geosupport installed:
#
#   $ docker pull mlipper/geosupport-docker:latest
#   ...
#   $ docker run -d --name geosupport \
#                   --mount source=vol-geosupport,target=/opt/geosupport \
#                   mlipper/geosupport-docker
#
#
# 2. Build this image
#
#   # Assuming you've successfully built this project from source 
#   # (e.g., using Dockerfile.build) and the following relative file path to
#   # to the default geoclient-service build artifact is valid:
#   # ./geoclient-service/build/libs/geoclient-service-<VERSION>-boot.jar
#
#   $ docker build -t geoclient -f Dockerfile .
#
#   OR
#
#   # Specify a non-default path to the geoclient-service Spring Boot jar file
#
#   $ docker build --build-arg JARFILE=./build/libs/gc.jar \
#                  -t geoclient -f Dockerfile .
#
# 3. Run this image
#
#   $ docker run -d --name gcrun \
#                -p 8080:8080 \
#               --mount source=vol-geosupport,target=/opt/geosupport \
#                geoclient
#
# 4. Default service endpoint is http://localhost:8080/geoclient/v2
#
#   $ curl -XGET 'http://localhost:8080/geoclient/v2/search.json?input=Broadway%20and%20W%2042%20st%20Manhattan'
#
FROM openjdk:8-jdk
LABEL maintainer "Matthew Lipper <mlipper@gmail.com>"

ARG JARFILE
ENV JARFILE ${JARFILE:-"./geoclient-service/build/libs/*boot.jar"}

ARG GEOSUPPORT_HOME
ENV GEOSUPPORT_HOME ${GEOSUPPORT_HOME:-/opt/geosupport}

RUN set -o errexit -o nounset \
  && apt-get update \
  && apt-get install -y --no-install-recommends \
      bash \
      vim \
  && rm -rf /var/lib/apt/lists/*

ADD $JARFILE /app/geoclient.jar

WORKDIR /app

RUN set -ex; \
  { \
    echo '#!/bin/bash'; \
    echo; \
    echo '. $GEOSUPPORT_HOME/bin/initenv'; \
    echo '$JAVA_HOME/bin/java -Dgc.jni.version=V2 -jar /app/geoclient.jar'; \
  } > /app/run.sh; \
  \
  chmod 755 /app/run.sh;

EXPOSE 8080:8080

CMD ["/bin/bash", "-c", "/app/run.sh"]
