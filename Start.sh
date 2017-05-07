#!/bin/bash

if [[ "$1" = "" ]]
then
  PARAM=""
elif [[ "$1" != "" && "$2" = "" ]]
then
  PARAM="-D$1"
elif [[ "$1" != "" && "$2" != "" && "$3" = "" ]]
then
  PARAM="-D$1 -D$2"
elif [[ "$1" != "" && "$2" != "" && "$3" != "" ]]
then
  PARAM="-D$1 -D$2 -D$3"
fi

java $PARAM -jar jenkins-winstone-0.9.10-jenkins-47.jar --webroot=webroot
