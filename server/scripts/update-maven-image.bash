#!/bin/bash

aws cloudformation create-stack --stack-name maven-build-rating1 --template-body file:///vagrant/harvest/config/build-image/cf-update-maven-image.yml --parameters file:///home/vagrant/.aws/cf-maven-image.json --capabilities CAPABILITY_NAMED_IAM
aws cloudformation wait stack-update-complete --stack-name maven-build-rating1

