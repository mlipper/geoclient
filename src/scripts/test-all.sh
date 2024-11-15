#!/usr/bin/env bash

set -Eeuo pipefail

this_dir="$(dirname "$(readlink -vf "$BASH_SOURCE")")"
this_file="$(basename "$0")"

# Run from project root directory
cd "${this_dir}/../../"

VERSION=2.0.1-rc.2
SVCJAR=geoclient-service/build/libs/geoclient-service-${VERSION}.jar

_die() {
    echo "$@"
    exit 1
}

_gradle() {
    "${this_dir}"/gradlew \
            -Dgeoclient.service.status=running \
            -Dtesting.endpoint=http://localhost:8080/geoclient/v2 \
            -Dtesting.samples.test=true $@
    _stop
}

_start() {
    export GEOCLIENT_SERVICE_STATUS=running
    java -jar "${this_dir}/${SVCJAR}" --spring.profiles.active=docsamples &
    sleep 8
    _gradle $@
}

_stop() {
    unset GEOCLIENT_SERVICE_STATUS
    curl -X POST http://localhost:8080/geoclient/v2/actuator/shutdown
}

_main() {
    if [ $# -eq 0 ]; then
        _usage
        exit 0
    fi
    local action=
    local args=()
    while [ $# -gt 0 ]; do
        case "$1" in
            help)
                _usage && exit 0;
                ;;
            start)
                action=_start; shift
                ;;
            stop)
                action=_stop; shift
                ;;
            *)
                args+=($@); break
                ;;
        esac
    done
    [[ ! -z $action ]] || _die "Missing required argument <help>|<start>|<stop>."
    if [[ $action == "_start" ]]; then
        if [[ "${#args[@]}" == 0 ]]; then
            _start check
        else
            _start ${args[@]}
        fi
    else
        _stop
    fi
}

_usage() {
    local this_file
    this_file="$(basename "${BASH_SOURCE[0]}")"
cat <<- EOF

Usage: ${this_file} <help>|<start>|<stop> [gradle args ...]

help   Show this message.

start  Sets up environment with variables for integration testing,
       runs geoclient-service on port 8080, and invokes
       gradle with system properties for integration tesing plus
       any arguments given.

       If no additional gradle arguments are given, passes gradle
       "check".

stop   Stops the running geoclient-service.

EOF
}

_main "$@"
