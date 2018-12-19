#!/usr/bin/env bash

# Get my ip
MY_IP=$(hostname -i)

# Register to the running consul
CT="Content-Type:application/json"
echo '{"name": "mongosrv", "port": 27017, "address": "'$MY_IP'"}' > body.json

COMMAND="curl -X PUT -H $CT -d @body.json http://$CONSUL_IP:8500/v1/agent/service/register -L"
echo Registering service: $COMMAND

RESPONSE=`$COMMAND`
echo $RESPONSE

exec "$@"