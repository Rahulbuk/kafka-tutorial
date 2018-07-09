
min.compaction.lag.ms=1000 # Minimum time a message will remain uncompacted in the log
segment.bytes=200 # Segment file size for log
min.cleanable.dirty.ratio=0.1 # Min dirty message, when cleanable is applied


export HOST_NAME=`ifconfig | grep -Eo 'inet (addr:)?([0-9]*\.){3}[0-9]*' | grep -v '127.0.0.1' | head -1 | tr -s ' ' ' ' | cut -f2 -d" "`

docker run -it --rm wurstmeister/kafka:1.1.0 /opt/kafka/bin/kafka-topics.sh \
 --zookeeper ${HOST_NAME}:2181 --replication-factor 1 --partitions 1 --create --topic test1 \
 --config cleanup.policy=compact --config min.compaction.lag.ms=1000 \
 --config segment.bytes=1000 --config min.cleanable.dirty.ratio=0.1
 
docker run -it --rm wurstmeister/kafka:1.1.0 /opt/kafka/bin/kafka-topics.sh \
 --zookeeper ${HOST_NAME}:2181 --replication-factor 1 --partitions 1 --create --topic test1 \
 --config retention.ms=30000 --config segment.bytes=200




docker run -it --rm wurstmeister/kafka:1.1.0 /opt/kafka/bin/kafka-topics.sh \
 --zookeeper ${HOST_NAME}:2181 --replication-factor 1 --partitions 1 --create --topic people \
 --config cleanup.policy=compact


