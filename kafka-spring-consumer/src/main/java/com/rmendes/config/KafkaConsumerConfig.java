package com.rmendes.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.rmendes.events.TransactionEvent;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
	
	@Value(value = "${kafka.bootstrap.url}")
	private String bootstrapServer;
	
	
	public ConsumerFactory<String, String> consumerFactory(){
		Map<String, Object> configs = new HashMap<String, Object>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put("security.protocol", "ssl");
		configs.put("ssl.truststore.location", "./truststore-pnq.jks");
		configs.put("ssl.truststore.password", "password");
		return new DefaultKafkaConsumerFactory<String, String>(configs);
	}
	
	public ConsumerFactory<String, TransactionEvent> consumerFactoryJson(){
		Map<String, Object> configs = new HashMap<String, Object>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configs.put("security.protocol", "ssl");
		configs.put("ssl.truststore.location", "./truststore-pnq.jks");
		configs.put("ssl.truststore.password", "password");
		return new DefaultKafkaConsumerFactory<String, TransactionEvent>(configs, new StringDeserializer(),new JsonDeserializer<>(TransactionEvent.class));
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, TransactionEvent> kafkaListenerContainerFactoryJson(){
		ConcurrentKafkaListenerContainerFactory<String, TransactionEvent> factory = new ConcurrentKafkaListenerContainerFactory<String, TransactionEvent>();
		factory.setConsumerFactory(consumerFactoryJson());
		return factory;
	}

}
