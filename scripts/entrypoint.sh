#!/bin/sh

# File: keycloak-event-gateway/scripts/entrypoint.sh
#
# Usage:
#
#     ENTRYPOINT for the kalisio/keycloak-event-gateway Docker image
#

set -e

if [ -z "${TARGET_DIR}" ]; then
	echo "*** ERROR: The TARGET_DIR environment variable should be set." >&2
	echo "    Exiting." >&2
	exit 1
fi

echo "TARGET_DIR: ${TARGET_DIR}"

ls /artifacts | while read i; do
	echo "Copying /artifacts/${i} to: ${TARGET_DIR}..."
	cp "/artifacts/${i}" "${TARGET_DIR}"
done

echo "Container has started successfully."

# Keep our container running when started as a daemon (docker run -d)
#
tail -f /dev/null
