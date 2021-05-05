#!/bin/sh
cd "$(dirname "$0")/.."
./mvnw -e clean package -DskipTests
cd -
mv ../target/*.jar ./build
