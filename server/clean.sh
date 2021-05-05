#!/bin/sh

#read -p "Are you sure? " -n 1 -r
#echo    # (optional) move to a new line
#if [[ $REPLY =~ ^[Yy]$ ]]
#then
    docker container rm -f $(docker ps -aq)
    docker rmi -f $(docker images -a -q)
    docker volume rm $(docker volume ls -q)
#fi
