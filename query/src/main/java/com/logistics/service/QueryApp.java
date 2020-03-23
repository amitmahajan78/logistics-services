package com.logistics.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

@SpringBootApplication(exclude = KafkaAutoConfiguration.class)
public class QueryApp {
	public static void main(String[] args) {
		SpringApplication.run(QueryApp.class, args);
	}
}