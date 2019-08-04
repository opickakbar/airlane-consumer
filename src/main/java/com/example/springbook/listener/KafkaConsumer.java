package com.example.springbook.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.util.ClassUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.springbook.model.Airlane;

@Service
public class KafkaConsumer {
	
	@Autowired
	private com.example.springbook.repository.AirlaneRepository airlanes;
	
	//group
	private static final String
	AIRLANE_GROUP = "group_airlane";
	
	//topics
	private static final String 
	INSERT_TOPIC = "airlane-insert",
	UPDATE_TOPIC = "airlane-update";
	
	//container factories
	private static final String
	AIRLANE_LISTENER_FACTORY = "airlaneKafkaListenerFactory";
	
	@KafkaListener(topics = INSERT_TOPIC, groupId = AIRLANE_GROUP, containerFactory = AIRLANE_LISTENER_FACTORY)
	public void listenInsert(Airlane airlane) {
		System.out.println("Data: "+airlane);
		airlanes.save(airlane);
	}
	
	@KafkaListener(topics = UPDATE_TOPIC, groupId = AIRLANE_GROUP, containerFactory = AIRLANE_LISTENER_FACTORY)
	public void listenUpdate(Airlane airlane) {
		
		//update airlane data
		Airlane updatedAirlane = new Airlane(airlane.getId(), airlane.getCode(), airlane.getName(), airlane.getStatus());
		
		//check data exist or not (for update purpose)
		if(airlanes.existsById(airlane.getId()))
			airlanes.deleteById(airlane.getId());
		
		//save data updated
		airlanes.save(updatedAirlane);
	}
	
}
