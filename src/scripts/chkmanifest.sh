#!/usr/bin/env bash

if command -v greadlink &> /dev/null; then
  # macOS with coreutils installed
  THIS_DIR="$(dirname "$(greadlink -vf "$BASH_SOURCE")")"
else
  # Linux
  THIS_DIR="$(dirname "$(readlink -vf "$BASH_SOURCE")")"
fi

set -Eeuo pipefail

BASE_DIR="${THIS_DIR}/../.."
PATTERN='META-INF/(license|notice).txt'
# Does not include geoclient-native project
PROJECTS=(documentation geoclient-cli geoclient-core geoclient-jni geoclient-parser geoclient-search geoclient-service geoclient-test geoclient-xml)
REPORT=report.txt
TEMPLATE_LICENSE=src/dist/license.txt
TEMPLATE_NOTICE=src/dist/notice.txt
VERSION=2.0.4

_showresult() {
  local msg=$1
  local -i actual=$2
  local -i expected=$3
  if [[ $actual -ne $expected ]]; then
    echo -e "[\x1B[31mERROR\x1B[0m] ${msg}: expected ${expected} lines, but got ${actual} lines."
  else
    echo -e "[\x1B[32mOK\x1B[0m] ${msg} is as expected."
  fi
}

_main() {

    echo "Checking jar manifests for version ${VERSION}"
    pushd "${BASE_DIR}" > /dev/null
    for project in "${PROJECTS[@]}"; do

        local jar="./${project}/build/libs/${project}-${VERSION}.jar"
        local -i license_expected
        local -i license_actual
        local -i notice_expected
        local -i notice_actual
        license_expected=$(cat "${TEMPLATE_LICENSE}" | wc -l)
        notice_expected=$(cat "${TEMPLATE_NOTICE}" | wc -l)

        if [[ ! -f "${jar}" ]]; then
          echo "Missing jar file ${jar}"
        else
          license_actual=$(unzip -p "${jar}" "META-INF/license.txt" | wc -l)
          notice_actual=$(unzip -p "${jar}" "META-INF/notice.txt" | wc -l)
          echo "${project}"
          echo "------------------"
          _showresult "License" "${license_actual}" "${license_expected}"
          _showresult "Notice" "${notice_actual}" "${notice_expected}"
          echo
        fi
    done
    popd > /dev/null
}

if [ "$#" -gt 0 ]; then
  VERSION="$1"
fi

_main
