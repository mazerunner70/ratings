#!/bin/bash

. ~/.env

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"
echo "This script located at ${SCRIPT_DIR}"

while getopts ":hf" opt; do
  case ${opt} in
    h )
      ;;
    f ) 
      csvfile=$2
      shift
      ;;
    \? ) echo "Usage: cmd [-h] -f payload"
      ;;
  esac
done

printf "File to process: ${csvfile}\n"

./csv2json.py ${csvfile} /tmp/output.json
curl --request POST --header "Content-Type: text/plain; charset=utf-8" --data "@/tmp/output.json"  ${apiurl}/review/android/upload


