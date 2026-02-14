#!/usr/bin/env sh

sudo chmod -R 777 .gradle/
gradle clean test build
java -jar ./build/libs/SmartRoomAllocator-0.0.1-SNAPSHOT.jar
