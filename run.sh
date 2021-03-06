#!/bin/bash
ENDPOINT=$1
TAG=$2

#setting the correct Runner file
RUNNER="testassessment.runners.${ENDPOINT}.RunnerTest"

#if tag is not provided, run entire set of tests for the module
if [[ $TAG = "" ]]; then
  TAG="@$ENDPOINT"
fi

#start the application in the background
git clone https://github.com/azakordonets/api-playground.git
cd api-playground
npm install
npm start &
BACK_PID=$!

#if endpoint is not provided, run entire set of tests, otherwise run only tests for chosen endpoint
cd ..
if [[ $ENDPOINT = "" ]];
then
  mvn clean test
else
  mvn clean test -Dtest="${RUNNER}" -Dcucumber.options="--tags ${TAG}"
fi

#kill the process and remove the application
trap "kill 0" EXIT
rm -rf api-playground/
