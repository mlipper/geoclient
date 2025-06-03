#!/usr/bin/env bash

set -Eeuo pipefail

this_dir="$(dirname "$(readlink -vf "$BASH_SOURCE")")"
this_file="$(basename "$0")"

PATTERN='META-INF/(license|notice).txt'
ROOT_DIR="$(readlink -f ${this_dir}/../..)"
VERSION=2.0.3

REPORT_DIR="${ROOT_DIR}/build/reports"
REPORT="${REPORT_DIR}/license-report.txt"

declare -A PROJECTS=(["documentation"]="documentation" ["geoclient-core"]="geoclient-core" ["geoclient-jni"]="geoclient-jni" ["geoclient-parser"]="geoclient-parser" ["geoclient-service"]="geoclient-service" ["geoclient-test"]="geoclient-test" ["cli"]="geoclient-utils/cli" ["jni-test"]="geoclient-utils/jni-test")

report() {
    local msg="$1"
    echo "${msg}" >> "${REPORT}"
}

main() {
    [[ -f "${REPORT}" ]] && rm "${REPORT}"
    mkdir -p "${REPORT_DIR}"
    report "License and notice report for Geoclient v${VERSION}"
    report "Generated on $(date)"
    report ""
    local project_temp="${ROOT_DIR}/tmp"
    mkdir -p "${project_temp}"
    for project in ${!PROJECTS[@]}; do
        echo -n "...."
        local project_path="${ROOT_DIR}/${PROJECTS[${project}]}"
        local jar_file="${project_path}/build/libs/${project}-${VERSION}.jar"
        if [[ ! -f "$jar_file" ]]; then
            report "Warning: jar file $jar_file does not exist."
            echo "Jar file $jar_file does not exist. Make sure to build the ${project} project first."
            continue
        fi
        local workdir="$(mktemp -d)"
        local jar_contents="${workdir}/${project}-jar-contents.txt"
        jar tvf "${jar_file}" > "${jar_contents}"
        local matched_string=$(cat "${jar_contents}" | egrep -E "${PATTERN}")
        if egrep -E "${PATTERN}" "${jar_contents}" &> /dev/null; then
            report "[${project}] Found in ${jar_file}:"
            report "${matched_string}"
        else
            report "[${project}] Did not match pattern ${PATTERN} in ${jar_file}."
        fi
        report "------------------------------"
    done
    echo ""
    echo "Report file generated: ${REPORT}"
    echo
}

main "$@"
