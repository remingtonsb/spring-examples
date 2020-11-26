package com.rmendes.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.rmendes.events.TransactionEvent;

@Configuration
public class KafkaProducerConfig {
	
	@Value(value = "${kafka.bootstrap.url}")
	private String bootstrapServer;
	
	@Bean
	public ProducerFactory<String, String> producerFactory(){
		Map<String, Object> configs = new HashMap<String, Object>();
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configs.put(ProducerConfig.ACKS_CONFIG, "-1");
		configs.put(ProducerConfig.LINGER_MS_CONFIG, 20);
		configs.put("security.protocol", "ssl");
		configs.put("ssl.truststore.location", "./truststore-pnq.jks");
		configs.put("ssl.truststore.password", "password");
		return new DefaultKafkaProducerFactory<String, String>(configs);
	}
	
	@Bean
	public ProducerFactory<String, TransactionEvent> customProducerFactory(){
		Map<String, Object> configs = new HashMap<String, Object>();
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		configs.put(ProducerConfig.ACKS_CONFIG, "-1");
		configs.put(ProducerConfig.LINGER_MS_CONFIG, 20);
		configs.put("security.protocol", "ssl");
		configs.put("ssl.truststore.location", "./truststore-pnq.jks");
		configs.put("ssl.truststore.password", "password");
		return new DefaultKafkaProducerFactory<String, TransactionEvent>(configs);
	}
	
	@Bean
	public KafkaTemplate<String, String> kafkaTemplate(){
		return new KafkaTemplate<String, String>(producerFactory());
	}
	
	@Bean
	public KafkaTemplate<String, TransactionEvent> kafkaTemplateJson(){
		return new KafkaTemplate<String, TransactionEvent>(customProducerFactory());
	}

}
