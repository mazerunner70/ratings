#!/bin/bash

aws cloudformation update-stack --stack-name rating1 --template-body file:///vagrant/aws/codepipeline/cf-codepipeline.yml --parameters file:///home/vagrant/.aws/cf-code-pipeline.json --capabilities CAPABILITY_NAMED_IAM
aws cloudformation wait stack-update-complete --stack-name rating1

