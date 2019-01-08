
./bin/kafka-topics.sh --zookeeper localhost:2181 --partitions 3 --replication-factor 1 --create --topic people

./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 \
    --property print.key=true --property key.separator="-" \
    --group my1 \
    --topic people

