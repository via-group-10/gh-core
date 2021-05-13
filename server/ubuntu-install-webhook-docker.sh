#!/bin/bash
apt update
apt install -y curl webhook openjdk-11-jre-headless
curl -fsSL https://get.docker.com -o get-docker.sh
sudo sh get-docker.sh
apt install -y docker-compose
