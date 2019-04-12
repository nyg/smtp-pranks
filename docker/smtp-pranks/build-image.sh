#!/usr/bin/env sh

# build smtp-pranks
cd ../..
mvn clean package
cp target/smtp-pranks-*.jar docker/smtp-pranks/smtp-pranks.jar

# build Docker image
cd docker/smtp-pranks
docker build -t res/smtp-pranks .
