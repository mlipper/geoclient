#!/usr/bin/env bash

#
# Example Kubernetes readiness probe which executes a F1B call to
# to Geosupport.
#
# If there is an issue with one of the Geosupport files, one of
# the return code (F1EX, F1AX) will usually not match the regex
# test, preventing the container from being considered ready.
#
# This example uses environment variables and a complete URL for demo
# purposes.
#
# In an actual Deployment manifest, only the URI path is used and no
# auth key is needed and the URI path would simply be:
#
# /geoclient/v2/address?houseNumber=280&street=rsd&borough=manhattan
#

BASE="${BASE:-https://api.nyc.gov/geoclient/v2}"
KEY="${KEY:-keynotset}"

rc=$(curl -k -s --get \
    "${BASE}/address?houseNumber=280&street=rsd&borough=manhattan&key=${KEY}" \
    | jq -M -cr '.address | [.geosupportReturnCode,.geosupportReturnCode2]' \
    | sed -r 's/(\[|,|"|\])//g') \
    && [[ "$rc" =~ 0[0-1]0[0-1] ]];
