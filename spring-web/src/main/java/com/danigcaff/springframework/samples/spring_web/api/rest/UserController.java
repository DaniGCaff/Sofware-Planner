package com.danigcaff.springframework.samples.spring_web.api.rest;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.danigcaff.springframework.samples.spring_web.api.UserApi;
import com.danigcaff.springframework.samples.spring_web.persistence.mongo.UserMongo;

@RestController
@EnableAuthorizationServer
public class UserController implements UserApi {
	/* (non-Javadoc)
	 * @see com.danigcaff.springframework.samples.spring_web.api.UserApi#user(java.security.Principal)
	 */
	@RequestMapping({ "/user", "/me" })
	public Map<String, String> user(Principal principal) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("name", principal.getName());
		return map;
	}
	
	@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public void createUser(@RequestBody Map<String, String> json) {
		UserMongo.insert(json); // TODO Esto podría hacerse con un Builder
	}
}
