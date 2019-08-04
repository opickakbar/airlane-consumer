package com.example.springbook.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbook.model.Airlane;
import com.example.springbook.repository.AirlaneRepository;

@RestController
public class AirlaneController {

	@Autowired
	private AirlaneRepository airlanes;
	
	@GetMapping("airlane")
	public List<Airlane> getAllAirlane(){
		return (List<Airlane>) airlanes.findAll();
	}
	
}
