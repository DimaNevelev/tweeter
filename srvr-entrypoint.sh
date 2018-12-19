#!/bin/sh

exec "java" \
    $JVM_OPTS \
    $JAVA_ARGS \
    -jar \
    /usr/local/run/tweeter.jar \

exec "$@"