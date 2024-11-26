#!/usr/bin/env bash

rc="$(curl -k -s --get \
    'https://maps.nyc.gov/geoclient/v2/address?houseNumber=280&street=rsd&borough=manhattan' \
    | jq -M -cr '.address | [.geosupportReturnCode,.geosupportReturnCode2]' | sed -r 's/(\[|,|"|\])//g')" \
    && [[ "$rc" =~ 0[0-1]0[0-1] ]];
#rc="$(echo '["00","01"]' | sed -r 's/(\[|,|"|\])//g')"
#echo "$rc"
#if [[ "$rc" =~ 0[0-1]0[0-1] ]]; then
#    echo true
#else
#    echo false
#fi

echo $?