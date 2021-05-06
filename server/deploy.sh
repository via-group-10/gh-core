#!/bin/bash
cd "$(dirname "$0")"
echo 'stopping docker containters'
git checkout main
git pull
docker-compose stop
sh build.sh
docker-compose up --build
#docker-compose up --build -d
echo 'deploy script end'
