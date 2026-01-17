#!/usr/bin/env sh

# Minimal Gradle wrapper script
DIR="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"

# JVM opts can be set via GRADLE_OPTS
JAVA_CMD="${JAVA_HOME:+$JAVA_HOME/bin/}java"

if [ ! -x "$JAVA_CMD" ]; then
  JAVA_CMD="java"
fi

APP_NAME="Gradle"

CLASSPATH="$DIR/gradle/wrapper/gradle-wrapper.jar"

exec "$JAVA_CMD" ${GRADLE_OPTS} -Dorg.gradle.appname="$APP_NAME" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
