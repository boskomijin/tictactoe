#!/bin/sh
#Colors
BBlue='\033[1;34m'
Color_Off='\033[0m'


echo
echo "${BBlue}Running application..."
echo "----------------------${Color_Off}"
echo
docker run -it --rm -d --name tictactoe -p 0:8081 tictactoe:0.0.1-SNAPSHOT
echo
echo "${BBlue}Application is accesible at: "
docker port tictactoe 8081/tcp
echo "----------------------${Color_Off}"
docker logs --follow tictactoe
