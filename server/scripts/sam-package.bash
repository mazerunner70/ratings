#!/bin/bash

. ~/.env



SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"
echo "This script located at ${SCRIPT_DIR}"

SAM_RELATIVE_DIR="../aws/sam-app"
SAM_APP_DIR="$( cd ${SCRIPT_DIR}/${SAM_RELATIVE_DIR} >/dev/null && pwd )"
if [ ! -d "$SAM_APP_DIR" ]; then
    printf "Error - ${SAM_APP_DIR} should be a valid dir!\n"
    exit 3
fi

TARGET_RELATIVE_DIR="../harvest/target"
TARGET_DIR="$( cd ${SCRIPT_DIR}/${TARGET_RELATIVE_DIR} >/dev/null && pwd)"
if [! -d "$TARGET_DIR" ]; then
    printf "Error - ${TARGET_DIR} should be a valid dir!\n"
    exit 3
fi

printf "SAM app directory at ${SAM_APP_DIR}\n"

#Move SAM template file over
mv ${TARGET_DIR}/harvest*.jar ${SAM_APP_DIR}/build

pushd ${SAM_APP_DIR}

sam package \
   --template-file sam-app.yml \
   --output-template-file /tmp/packaged.yaml \
   --s3-bucket ${STAGING_S3}


popd
