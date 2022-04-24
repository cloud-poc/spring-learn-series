#!/usr/bin/env bash
echo "Creating users..."
mongo admin --host localhost -u root -p 'Test1234' --eval "db.createUser({user: 'jamie', pwd: 'Test1234',roles: [{role: 'userAdminAnyDatabase', db: 'admin'},'readWriteAnyDatabase']});"
echo "Users created."