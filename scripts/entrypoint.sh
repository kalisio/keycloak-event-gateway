#!/bin/sh

# File: keycloak-event-gateway/scripts/entrypoint.sh
#
# Usage:
#
#     ENTRYPOINT for the kalisio/keycloak-event-gateway Docker image
#
# We declare /bin/sh, not /bin/bash, so we can run in Alpine.
#

set -e

# 1. If arguments were passed in the command line, execute the command and return.

if [ -n "${1}" ]; then

	$*
	
	exit

fi

# 2. If no argument was passed in the command line, copy the artifacts to
# TARGET_DIR.

if [ -z "${TARGET_DIR}" ]; then
	echo "No TARGET_DIR variable found in environment."
	TARGET_DIR=/opt/kalisio/artifacts
fi

echo "Using TARGET_DIR: ${TARGET_DIR}"

if [ ! -d "${TARGET_DIR}" ]; then
	echo "Creating directory: ${TARGET_DIR}..."
	mkdir -p "${TARGET_DIR}"
fi

ls /artifacts | while read i; do
	echo "Copying /artifacts/${i} to: ${TARGET_DIR}..."
	cp "/artifacts/${i}" "${TARGET_DIR}"
done

echo "Container has started successfully."

# Keep our container running when started as a daemon (docker run -d)
#
tail -f /dev/null
