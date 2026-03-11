#!/usr/bin/env bash

JAR=/workspaces/geoclient/geoclient-service/build/libs/geoclient-service-2.0.4.jar

java -jar "${JAR}" \
            --server.address=localhost \
            --server.port=9090 \
            --server.servlet.context-path=/geoclient/v2 \
            --spring.profiles.active=docsamples
