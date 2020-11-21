#!/bin/sh
#Colors
BBlue='\033[1;34m'
Color_Off='\033[0m'

echo
echo "${BBlue}Building application..."
echo "-----------------------${Color_Off}"
echo
./gradlew
echo
echo "${BBlue}Dockerizing application..."
echo "--------------------------${Color_Off}"
echo
docker build -t tictactoe:0.0.1-SNAPSHOT .
