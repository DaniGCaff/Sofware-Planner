package com.danigcaff.springframework.samples.spring_web.restcontrollers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableOAuth2Client
@Order(6)
public class TasksController {	
	
	@RequestMapping("/tasks/{boardId}/{taskId}")
	public Map <String, String> list(@PathVariable("boardId") int boardId, @PathVariable("taskId") int taskId){
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("response", "ok");
		return map;
	}
}
