#!/usr/bin/env bash

#
# Asynchronous geoclient-service executable spring-boot jar start/stop script.
#
# Requires bash getopts builtin and curl
#

#set -Eeuo pipefail

this_dir="$(dirname "$(readlink -vf "$BASH_SOURCE")")"
this_file="$(basename "$0")"

# Define variables with default values
ACTION=""          # Positional argument 
JARFILE=""         # Long and short options
PORT="9090"
PROFILES=""
SERVER="localhost"

# Global constants
CONTEXT_PATH="/geoclient/v2"
SHUTDOWN_PATH="/actuator/shutdown"
STATUS_PATH="/version"
STATUS_RUNNING="running"
STATUS_STOPPED="stopped"
STATUS_UNKNOWN="unknown"
STATUS_UNKNOWN_MESSAGE_PREFIX="Error: service returned HTTP"

# Derived global variables
BASE_URL=""

# Environment variable to maintain state
#export GEOCLIENT_SERVICE_STATUS="${STATUS_UNKNOWN}"

#
# Configure getopt bash built-in
#
SHORT_OPTS="s:r:a:p:oth"
LONG_OPTS="start:,profiles:,address:,port:,stop,status,help"

# Use getopt to process and reorder the command-line arguments.
# The "|| exit 1" ensures the script stops if invalid options are provided.
# The final "--" is crucial to separate getopt's options from the script's
# arguments.
# The "$@" passes all original arguments to getopt.
PARSED_OPTS=$(getopt -o "$SHORT_OPTS" --long "$LONG_OPTS" -n "$0" -- "$@") || exit 1

# This line is essential. It takes the safe, quoted output from getopt 
# and reassigns the shell's positional parameters ($1, $2, etc.) to the 
# reordered and correctly quoted arguments. This handles arguments with spaces
# correctly.
eval set -- "${PARSED_OPTS}"

main() {
    # Loop through the reordered options and their values
    while true; do
        case "$1" in
            -s | --start )
                ACTION="start"
                JARFILE="$2"
                shift 2
                ;;
            -r | --profiles )
                PROFILES="$2"
                shift 2
                ;;
            -a | --address )
                SERVER="$2"
                shift 2
                ;;
            -p | --port )
                PORT="$2"
                shift 2
                ;;
            -o | --stop )
                ACTION="stop"
                shift 1
                ;;
            -t | --status )
                ACTION="status"
                shift 1
                ;;
            -h | --help )
                usage
                exit 0
                ;;
            -- )
                shift
                break
                ;;
            * )
                echo "Error: unrecognized option '$1'" >&2
                usage
                exit 1
                ;;
        esac
    done

    BASE_URL="http://${SERVER}:${PORT}${CONTEXT_PATH}"

    # Call the action function
    eval "$ACTION"
}

progress_bar() {
    # Default to 24 if total steps is not provided
    local total_steps=${1:-32}
    echo "totatl_steps=${total_steps}"
    local progress_char="."
    local empty_char=" "
    for i in $(seq 1 $total_steps); do
        progress_bar=""
        for ((j=0; j<i; j++)); do
            progress_bar="${progress_bar}${progress_char}"
        done
        for ((j=i; j<total_steps; j++)); do
            progress_bar="${progress_bar}${empty_char}"
        done
        # Print the progress bar, using \r to return to the beginning of the line
        echo -ne "\r${progress_bar}"
        sleep 0.5
    done
}

#
# Exports "GEOCLIENT_SERVICE_STATUS=running" and starts geoclient-service
# executable Spring Boot jar as a background job. Arguments to this function
# are passed to the java command.
#
# After sleeping for a few seconds, returns immediately. This allows gradle
# tasks to use the service. E.g., for tests:
#
#    "${PROJECT_DIR}"/gradlew \
#            -Dgeoclient.service.status=running \
#            -Dtesting.endpoint=http://localhost:8080/geoclient/v2 \
#            -Dtesting.samples.test=true $@
#
start() {
    if [[ ! -f "${JARFILE}" ]]; then
        echo "Error: jar file '${JARFILE}' does not exist." >&2
        exit 1
    fi
    local javacmd="java"
    if [[ ! -z "${JAVA_HOME}" ]] && [[ -f "${JAVA_HOME}/bin/java" ]]; then
        javacmd="${JAVA_HOME}/bin/java"
    fi
    local jvmargs='--add-opens=java.base/java.lang=ALL-UNNAMED'
    local bootargs="--server.address=${SERVER} --server.port=${PORT}"
    [[ ! -z "${PROFILES}" ]] && bootargs+=" --spring.profiles.active=${PROFILES}"
    local appargs='-Dgeoclient.service.status=running -Dtesting.samples.test=true'
    cmd=$(cat << EOF
$javacmd -jar $JARFILE \\
    $jvmargs \\
    $bootargs \\
    $appargs
EOF
)
    eval "$cmd" &
    echo "${cmd} & "
    progress_bar 32
    echo
    local stat="$(status)"
    if [[ "${stat}"=="${STATUS_UNKNOWN_MESSAGE_PREFIX}"* ]]; then
        #unset GEOCLIENT_SERVICE_STATUS
        #export GEOCLIENT_SERVICE_STATUS="${STATUS_UNKNOWN}"
        echo "${stat}"
        exit 1
    fi
    echo "${STATUS_RUNNING}"
    #unset GEOCLIENT_SERVICE_STATUS
    #export GEOCLIENT_SERVICE_STATUS="${STATUS_RUNNING}"
}

status() {
    local -i http_code="$(curl -s -o /dev/null -w "%{http_code}" "${BASE_URL}${STATUS_PATH}")"
    echo "HTTP ${http_code}"
    if [ $http_code -eq 0 ]; then
        echo "${STATUS_STOPPED}"
    elif [ $http_code -eq 200 ]; then
        echo "${STATUS_RUNNING}"
    else
        echo "${STATUS_UNKNOWN_MESSAGE_PREFIX} ${http_code}. Check port ${PORT} before attempting to start again."
    fi
}

stop() {
    if ! curl -X POST "${BASE_URL}${SHUTDOWN_PATH}"; then
        #unset GEOCLIENT_SERVICE_STATUS
        #export GEOCLIENT_SERVICE_STATUS="${STATUS_UNKNOWN}"
        echo "${STATUS_UNKNOWN_MESSAGE_PREFIX} ${http_code}. Check port ${PORT} before attempting to start again."
        #echo "Error: failed to shutdown service running on port ${PORT}."
        exit 1
    fi
    #unset GEOCLIENT_SERVICE_STATUS
    #export GEOCLIENT_SERVICE_STATUS="${STATUS_STOPPED}"
    echo "${STATUS_STOPPED}"
}

synopsis() {
    echo "Usage: ${this_file} -j|--jarfile <path> [OPTIONS] <ACTIONS>"
}

usage() {
    synopsis
cat <<- EOF

DESCRIPTION
Runs the geoclient-service executable jar as a background process with the
ability to check and stop the server subprocess.

Allows gradle to start the service asynchronously so it can be used by build,
test and document generation tasks without blocking task execution.

OPTIONS
    Required
    -j|--jarfile  <path>        Path to the executable jar file.
    Optional
    -r|--profiles <p1,p2,...>   Spring profile(s) the service should use.
    -p|--port <port number>     Port number the HTTP server should listen on.
    -s|--server <name or IP>    Host name or IP address the server should be bound to.
    Or
    -h|--help                   Show this message.

ACTIONS

start    Sets up environment with variables for integration testing,
         runs geoclient-service on port 8080, and invokes
         gradle with system properties for integration tesing plus
         any arguments given.

         If no additional gradle arguments are given, passes gradle
         "check".

         If the -a option is given, runs clean and assemble tasks
         before starting geoclient-service.

status   Checks and reports the state of the service process.

stop     Stops the running geoclient-service.

EOF
}

main "$@"
