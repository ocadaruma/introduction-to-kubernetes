#!/bin/bash

set -u

while :
do
  sleep 0.005
  err=$(curl $ENDPOINT 2>&1 > /dev/null)
  if [[ $? -ne 0 ]]; then
    echo "$err"
    exit $?
  fi
done
