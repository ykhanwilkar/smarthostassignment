#!/usr/bin/env sh

# This file will be included as a Docker ENTRYPOINT in our automated testing evironment.
gradle clean test build bootRun
