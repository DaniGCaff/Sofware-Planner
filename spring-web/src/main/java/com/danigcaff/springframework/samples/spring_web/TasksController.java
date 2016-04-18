package com.danigcaff.springframework.samples.spring_web;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableOAuth2Client
@Order(6)
public class TasksController {
	@RequestMapping("/tasks/check/{id}")
	public Map <String, String> asociate(@PathVariable("id") int id){
		//TODO Todas las tareas tienen que hacer una request a esta dirección.
		// Las tareas que estén registradas en autorizados devolveran un OK
		// El resto de tareas no devolveran nada.
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("response", "ok");
		return map;
	}
}
