package com.rmendes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rmendes.events.TransactionEvent;
import com.rmendes.producer.CustomKafkaProducer;

@RestController
public class ProducerController {
	
	@Autowired
	private CustomKafkaProducer producer;
	
	
	@GetMapping("/send/{message}")
	public ResponseEntity<String> sendMessage(@PathVariable String message) {
		producer.sendMessage("GET", message);
		return new ResponseEntity<>("Event received", HttpStatus.OK);
	}
	
	@PostMapping("/send")
	public ResponseEntity<String> sendMessage(@RequestBody TransactionEvent transaction){
		producer.sendMessage("POST", transaction);
		return new ResponseEntity<>("Event received", HttpStatus.OK);
	}

}
