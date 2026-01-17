#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"

# Builds the mod jar using an installed Gradle (8.x recommended).
# Requires: Java JDK 17.
#
# NOTE: This project intentionally does NOT rely on the Gradle wrapper, because
# many setups block wrapper downloads. Install Gradle 8.x and ensure `gradle`
# is on PATH.

if ! command -v gradle >/dev/null 2>&1; then
  echo "ERROR: 'gradle' was not found on PATH."
  echo "Install Gradle 8.x and try again (or run: gradle clean build)."
  exit 1
fi

gradle clean build

echo
echo "Build complete. Jar(s) are in: $(pwd)/build/libs"
ls -1 ./build/libs || true
