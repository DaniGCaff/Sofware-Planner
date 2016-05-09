package com.danigcaff.springframework.samples.spring_web.api.rest;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danigcaff.springframework.samples.spring_web.api.TasksApi;

@RestController
@EnableOAuth2Client
@Order(6)
public class TasksController implements TasksApi {	
	
	/* (non-Javadoc)
	 * @see com.danigcaff.springframework.samples.spring_web.api.TasksApi#list(int, int)
	 */
	@RequestMapping("/tasks/{boardId}/{taskId}")
	public Map <String, String> list(@PathVariable("boardId") int boardId, @PathVariable("taskId") int taskId){
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("response", "ok");
		return map;
	}
}
