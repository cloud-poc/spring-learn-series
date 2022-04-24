WebFlux User Service


docker run --detach --name mongo -p 27017:27017 \
-v /Users/jamie/Documents/work-benches/mongo/test/conf/mongod.conf:/etc/mongod.conf \
-v /Users/jamie/Documents/work-benches/mongo/test/data:/data/db \
-v /Users/jamie/Documents/work-benches/mongo/test/conf/create-mongo-user.sh:/docker-entrypoint-initdb.d/create-mongo-user.sh \
-e MONGO_INITDB_ROOT_USERNAME='root' -e MONGO_INITDB_ROOT_PASSWORD='Test1234' \
mongo --auth