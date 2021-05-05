#!/bin/bash
cd "$(dirname "$0")"
echo 'pulling new changes'
git checkout mainline-release
git pull
echo 'stopping containers'
docker-compose stop
echo 'rebuilding jars'
sh build.sh
echo 'restarting services'
if [ $# -eq 0 ]; then
  echo "running in background"
  docker-compose up --build -d
else
  echo "running in foreground"
  docker-compose up --build 
fi
echo 'deploy script finished'
cd -
