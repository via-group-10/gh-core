#!/bin/sh
cd "$(dirname "$0")/.."
./mvnw -e clean package -DskipTests
cd -
mkdir ./build
mv ../target/*.jar ./build
