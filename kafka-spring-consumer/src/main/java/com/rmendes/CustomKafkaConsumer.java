package com.rmendes;

import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.rmendes.events.TransactionEvent;

@Service
public class CustomKafkaConsumer {
	
	@KafkaListener(
			topicPartitions = @TopicPartition(topic = "${kafka.topic.name.string}",
			partitionOffsets = { 
				@PartitionOffset(partition = "0", initialOffset = "0"),
			  	@PartitionOffset(partition = "1", initialOffset = "0"),
				@PartitionOffset(partition = "2", initialOffset = "0")}),
			topics = "${kafka.topic.name.string}", groupId = "2", containerFactory = "kafkaListenerContainerFactory")
	public void listenTopic1(@Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition, @Payload String message) {
		System.out.println("My Topic 1 Received Message: "+message+"from partition: " + partition);
	}
	
	@KafkaListener(
			topicPartitions = @TopicPartition(topic = "${kafka.topic.name.json}",
			partitionOffsets = { 
				@PartitionOffset(partition = "0", initialOffset = "0"),
			  	@PartitionOffset(partition = "1", initialOffset = "0"),
				@PartitionOffset(partition = "2", initialOffset = "0")}),
			topics = "${kafka.topic.name.string}", groupId = "1", containerFactory = "kafkaListenerContainerFactoryJson")
	public void listenTopic2(@Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition, @Payload TransactionEvent message) {
		System.out.println("My Topic 2 Received Message: "+message+"from partition: " + partition);
	}
	
	
	
	
	@Bean
	public CustomKafkaConsumer consumer() {
		return new CustomKafkaConsumer();
	}
     

}
