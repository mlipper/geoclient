#!/usr/bin/env bash

set -Eeuo pipefail

this_dir="$(dirname "$(readlink -vf "$BASH_SOURCE")")"
this_file="$(basename "$0")"

source "${this_dir}/configure-env.sh"

cd "${PROJECT_DIR}"

SVCJAR="${PROJECT_DIR}/geoclient-service/build/libs/geoclient-service-${VERSION}.jar"

_build() {
    docker build -t "mlipper/geoclient:${VERSION}" --build-arg JAR="${SVCJAR}" -f images/run.Dockerfile .
}

_run() {
    docker run --name geoclientsvc -d --mount type=volume,source=geosupport-latest,target=/opt/geosupport -p 8080:8080 "mlipper/geoclient:${VERSION}"
}

_stop() {
    docker stop geoclientsvc
}

_die() {
    echo "$@"
    exit 1
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
            build)
                action=_build; shift
                _build
                ;;
            run)
                action=_run; shift
                _run
                ;;
            stop)
                action=_stop; shift
                _stop
                ;;
            *)
                args+=($@); break
                ;;
        esac
    done
    [[ ! -z $action ]] || _die "Missing required argument <help>|<build>|<run>|<stop>."
    if [[ "${#args[@]}" != 0 ]]; then
        echo "Unrecogized argument ${args[@]}."
        _usage
    fi
}

_usage() {
    local this_file
    this_file="$(basename "${BASH_SOURCE[0]}")"
cat <<- EOF

Usage: ${this_file} <help>|<build>|<run>|stop

help   Show this message.

build  Builds geoclient-service runtime image defined in file
       ${PROJECT_DIR}/images/run.Dockerfile

run    Runs geoclient-service runtime image as a daemon on port 8080
       using container name "geoclientsvc".

stop   Stops the running "geoclientsvc" container.

WARNING: ${this_file} assumes that a local volume named geosupport-latest,
         containing Geosupport exists.
EOF
}

_main "$@"
