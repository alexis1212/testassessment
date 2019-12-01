#!/bin/bash
ENDPOINT=$1
TAG=$2

#setting the correct Runner file
RUNNER="testassessment.runners.${ENDPOINT}.RunnerTest"

#if tag is not provided, run entire set of tests for the module
if [[ $TAG = "" ]]; then
  TAG="@$ENDPOINT"
fi

#start the application in the background using docker
git clone https://github.com/azakordonets/api-playground.git
cd api-playground
docker build -t api-playground .
docker run -d -p 127.0.0.1:3030:3030 --name api-playground api-playground

#if endpoint is not provided, run entire set of tests, otherwise run only tests for chosen endpoint
cd ..
if [[ $ENDPOINT = "" ]];
then
  mvn clean test
else
  mvn clean test -Dtest="${RUNNER}" -Dcucumber.options="--tags ${TAG}"
fi

#remove docker container and the application
docker rm -f api-playground
rm -rf api-playground/
