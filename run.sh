#!/usr/bin/env sh

gradle clean test build
java -jar ./build/libs/SmartRoomAllocator-0.0.1-SNAPSHOT.jar
