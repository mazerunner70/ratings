#!/bin/bash

. ~/.env

STACK_NAME="ratings"

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"
echo "This script located at ${SCRIPT_DIR}"

RELATIVE_DIR="../aws/sam-app"
SAM_APP_DIR="$( cd ${SCRIPT_DIR}/${RELATIVE_DIR} >/dev/null && pwd )"
if [ ! -d "$SAM_APP_DIR" ]; then
    printf "Error - ${SAM_APP_DIR} should be a valid dir!\n"
    exit 3
fi

printf "SAM app directory at ${SAM_APP_DIR}\n"

pushd ${SAM_APP_DIR}


sam deploy \
   --template-file /tmp/packaged.yaml \
   --stack-name ${STACK_NAME} \
   --capabilities CAPABILITY_IAM \
   --region eu-west-1 \
   --parameter-overrides "IosAppId=${IOS_APP_ID}"



popd

DB_TABLE=`aws cloudformation describe-stacks --stack ${STACK_NAME} --region ${REGION} --query "Stacks[].Outputs[?OutputKey=='DbTable'].OutputValue" --output text`

printf "DB_TABLE: $DB_TABLE}\n"

