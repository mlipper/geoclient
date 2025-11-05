#!/usr/bin/env bash

set -Eeuo pipefail

this_dir="$(dirname "$(readlink -vf "$BASH_SOURCE")")"
this_file="$(basename "$0")"

mkdir -p "${this_dir}"/logs

cp -v "@geoclientjar@" "${this_dir}/geoclient.jar"

"${this_dir}"/bin/jarexec \
--jarfile="${this_dir}"/geoclient.jar \
--httpfile="${this_dir}"/http-shutdown.json \
--workdir="${this_dir}" \
--arg="--server.port=9090" \
--arg="--spring.profiles.active=docsamples" \
--start
