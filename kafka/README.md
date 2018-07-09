# Test

create topic 

export HOST_NAME=`ifconfig | grep -Eo 'inet (addr:)?([0-9]*\.){3}[0-9]*' | grep -v '127.0.0.1' | head -1 | tr -s ' ' ' ' | cut -f2 -d" "`

docker run -it --rm wurstmeister/kafka:1.1.0 /opt/kafka/bin/kafka-topics.sh \
 --zookeeper ${HOST_NAME}:2181 --replication-factor 2 --partitions 3 --create --topic test1
 
docker run -it --rm wurstmeister/kafka:1.1.0 /opt/kafka/bin/kafka-topics.sh \
 --zookeeper ${HOST_NAME}:2181 --replication-factor 1 --partitions 1 --create --topic test1 \
 --config cleanup.policy=compact --config min.compaction.lag.ms=1000 \
 --config segment.bytes=100 --config min.cleanable.dirty.ratio=0.1

Send message 
docker run -it --rm wurstmeister/kafka:1.1.0 /opt/kafka/bin/kafka-console-producer.sh \
 --broker-list ${HOST_NAME}:9092 --topic test1

docker run -it --rm wurstmeister/kafka:1.1.0 /opt/kafka/bin/kafka-console-producer.sh \
 --broker-list ${HOST_NAME}:9092 --topic test1 \
 --property "parse.key=true" --property "key.separator=:"



Read message
docker run -it --rm wurstmeister/kafka:1.1.0 /opt/kafka/bin/kafka-console-consumer.sh \
 --bootstrap-server ${HOST_NAME}:9092 --from-beginning --topic test1 \
 --property print.key=true --property key.separator=":"



docker run -it --rm wurstmeister/kafka:1.1.0 /opt/kafka/bin/kafka-console-consumer.sh \
 --bootstrap-server ${HOST_NAME}:9092 --from-beginning --topic people \
 --property print.key=true --property key.separator=":"



docker run -it --rm wurstmeister/kafka:1.1.0 /opt/kafka/bin/kafka-topics.sh \
 --zookeeper ${HOST_NAME}:2181 --replication-factor 1 --partitions 1 --create --topic people


