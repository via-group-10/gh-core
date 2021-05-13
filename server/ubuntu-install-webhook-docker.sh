#!/bin/bash
apt update
apt install curl webhook docker-compose openjdk-11-jre-headless
curl -fsSL https://get.docker.com -o get-docker.sh
sudo sh get-docker.sh
