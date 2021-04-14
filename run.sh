#!/bin/bash

export DB_TYPE=postgresql
export DB_DRIVER="org.postgresql.xa.PGXADataSource"
export DB_SERVER="db"
export DB_URL="//db:55432/database"
export DB_USER="database"
export DB_PASSWORD="database"

java \
        -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5009 \
        -jar payara-micro-5.2020.7.jar \
        --deploymentDir ${PWD}/target \
        --port 7080  \
	--sslPort 7443

