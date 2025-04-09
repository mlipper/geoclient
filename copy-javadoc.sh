#!/usr/bin/env bash

set -Eeuo pipefail

this_dir="$(dirname "$(readlink -vf "$BASH_SOURCE")")"
this_file="$(basename "$0")"

GEOCLIENT_REPO="${this_dir}/../geoclient"
GEOCLIENT_VERSION=2.0.2
LOCAL_REPO="repo"
TARGET_DIR="docs/current/api"

if [ $# -gt 0 ]; then
    GEOCLIENT_REPO="$1"
fi

if [[ ! -d "${GEOCLIENT_REPO}" ]]; then
    echo "Error: directory ${GEOCLIENT_REPO} does not exist."
    exit 1
fi

cp_javadoc() {
    local prj="$1"
    if [ $# -gt 1 ]; then
        prj="$1/$2"
    fi
    local apidir="${TARGET_DIR}/${prj}/api"
    mkdir -p "${apidir}"
    cp -r "${GEOCLIENT_REPO}/${prj}"/build/docs/javadoc/* "${apidir}/"
}

cp_javadocjars() {
    rm -rf "${LOCAL_REPO}"
    mkdir -p "${LOCAL_REPO}"
    find "${GEOCLIENT_REPO}" -type f -path '**/build/libs/*' -name '*-javadoc.jar' -exec cp {} "${LOCAL_REPO}" \;
    for jar in "${LOCAL_REPO}"/*; do
        project="${jar%-${GEOCLIENT_VERSION}-javadoc.jar}"
        project=$(basename "${project}")
        apidir="${TARGET_DIR}/${project}/api"
        if [[ "${project}" == cli ]] || [[ "${project}" == jni-test ]]; then
            apidir="${TARGET_DIR}/geoclient-utils/${project}/api"
        fi
        rm -rf "${apidir}"
        mkdir -p "${apidir}"
        unzip "${jar}" -d "${apidir}"
    done
}

main() {
    #cp_javadoc "documentation"
    #cp_javadoc "geoclient-core"
    #cp_javadoc "geoclient-jni"
    #cp_javadoc "geoclient-parser"
    #cp_javadoc "geoclient-service"
    #cp_javadoc "geoclient-test"
    #cp_javadoc "geoclient-utils" "cli"
    #cp_javadoc "geoclient-utils" "jni-test"

    cp_javadocjars
}

main
