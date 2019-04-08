#!/usr/bin/env sh

# get MockMock
if [ ! -d MockMock ]
then
    git clone https://github.com/dc55028/MockMock.git
fi

# build MockMock
cd MockMock
mvn clean package
cd ..

# build Docker image
docker build -t res/mockmock .
