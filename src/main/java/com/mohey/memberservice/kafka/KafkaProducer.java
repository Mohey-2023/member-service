package com.mohey.memberservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohey.memberservice.dto.memberalarm.NotificationDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaProducer {
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public NotificationDto send(String topic, NotificationDto notificationDto) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = "";
		try {
			jsonInString = mapper.writeValueAsString(notificationDto);
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
		}
		kafkaTemplate.send(topic, jsonInString);
		log.info("Kafka Producer send data from Member microservice : " + notificationDto);
		return notificationDto;
	}

}