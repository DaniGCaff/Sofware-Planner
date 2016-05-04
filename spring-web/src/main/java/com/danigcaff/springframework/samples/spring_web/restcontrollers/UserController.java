package com.danigcaff.springframework.samples.spring_web.restcontrollers;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAuthorizationServer
public class UserController {
	@RequestMapping({ "/user", "/me" })
	public Map<String, String> user(Principal principal) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("name", principal.getName());
		return map;
	}
}
