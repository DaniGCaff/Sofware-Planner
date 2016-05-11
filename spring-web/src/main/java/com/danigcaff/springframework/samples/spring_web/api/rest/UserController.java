package com.danigcaff.springframework.samples.spring_web.api.rest;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.danigcaff.springframework.samples.spring_web.api.UserApi;
import com.danigcaff.springframework.samples.spring_web.persistence.User;
import com.danigcaff.springframework.samples.spring_web.persistence.mongo.UserMongo;

@RestController
@EnableOAuth2Client
public class UserController implements UserApi {
	
	@Autowired
	OAuth2ClientContext oauth2ClientContext;
	
	@RequestMapping({ "/user", "/me" })
	public Map<String, String> user(Principal principal) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("gitHubUserId", principal.getName());
		return map;
	}
	
	@RequestMapping(value = "/user/create", method = RequestMethod.POST)
	public Map<String, String> createUser(@RequestBody Map<String, String> json) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		try{
			UserMongo.insert(json);
			map.put("response", "ok");
		}
		catch (Exception ex) {
			map.put("response", ex.getMessage());
		}
		return map;
	}
	
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public Map<String, String> login(@RequestBody Map<String, String> json) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		try{
			User usuario = new UserMongo(json.get("id"), json.get("password"));
			map.put("id", usuario.getId());
			map.put("name", usuario.getName());
			map.put("gitHubUserId", usuario.getGitHubUserId());
			map.put("trelloUserId", usuario.getTrelloUserId());
			map.put("response", "ok");
		}
		catch (Exception ex) {
			map.put("response", ex.getMessage());
		}
		return map;
	}
}
