#!/bin/sh
set -e

# Fix ownership of the database directory to the mythicmate user
chown -R mythicmate:mythicmate /app/database

# Drop to non-root user and execute the Java application
exec su-exec mythicmate java -jar mythicmate.jar
