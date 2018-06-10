# Test

create topic 


./bin/kafka-topics.sh --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --create --topic topic-par3



mvn archetype:generate \
-DarchetypeGroupId=com.wardziniak.kafka.tutorial \
-DarchetypeArtifactId=kafka-utils \
-DarchetypeVersion=1.0-SNAPSHOT

