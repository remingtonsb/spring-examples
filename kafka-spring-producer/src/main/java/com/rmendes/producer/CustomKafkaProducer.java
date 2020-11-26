package com.rmendes.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.rmendes.events.TransactionEvent;

@Service
public class CustomKafkaProducer {
	
	Logger logger = LoggerFactory.getLogger(CustomKafkaProducer.class);
	
	@Autowired
	private KafkaTemplate<String, String> template;
	
	@Autowired
	private KafkaTemplate<String, TransactionEvent> templateJson;
	
	@Value(value = "${kafka.topic.name.string}")
	private String topicNameString;
	
	@Value(value = "${kafka.topic.name.json}")
	private String topicNameJson;
	
	public void sendMessage(String key, String message) {
		ListenableFuture<SendResult<String, String>> future = template.send(topicNameString, key, message);
		
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				logger.info("Message Delivered: "+message+" offset: "+result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable arg0) {
				// TODO implement a dead letter queue
			}
			
		});
	}
	
	public void sendMessage(String key, TransactionEvent event) {
		ListenableFuture<SendResult<String, TransactionEvent>> future = templateJson.send(topicNameJson, key, event);
		future.addCallback(new ListenableFutureCallback<SendResult<String, TransactionEvent>>() {

			@Override
			public void onSuccess(SendResult<String, TransactionEvent> result) {
				logger.info("Message Delivered with offset: "+result.getRecordMetadata().offset());
				
			}

			@Override
			public void onFailure(Throwable arg0) {
				// TODO implement dead letter queue
				
			}
		});
	}

}
