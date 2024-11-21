#!/usr/bin/env bash

set -Eeuo pipefail

this_dir="$(dirname "$(readlink -vf "$BASH_SOURCE")")"
this_file="$(basename "$0")"

cd "${this_dir}/../../"

# Set root project directory
PROJECT_DIR="$(pwd)"
export PROJECT_DIR

cd "${this_dir}"

# Set project version
version_file="${PROJECT_DIR}/gradle.properties"

if [[ ! -f "${version_file}" ]]; then
  echo "[ERROR] Version file ${version_file} does not exist."
  exit 1
fi

v="$(cat "${version_file}" | grep '^version=')"
VERSION="${v#*=}"
unset v
export VERSION
