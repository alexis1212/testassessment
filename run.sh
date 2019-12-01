#!/bin/bash
ENDPOINT=$1
TARGET=$2
PROFILE=$3

RUNNER="testassessment.runners.${ENDPOINT}.RunnerTest"

if [[ $PROFILE = "" ]]; then
  PROFILE="default"
fi

git clone https://github.com/azakordonets/api-playground.git
cd api-playground
npm install
npm start &
BACK_PID=$!
cd ..
cd testassessment
mvn clean test -Dtest="${RUNNER}" -Dcucumber.options="--tags ${TARGET}" -P ${PROFILE}
trap "kill 0" EXIT
rm -rf api-playground/
