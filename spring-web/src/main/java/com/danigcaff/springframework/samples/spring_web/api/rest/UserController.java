package com.danigcaff.springframework.samples.spring_web.api.rest;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.social.github.api.GitHub;
import org.springframework.social.github.api.GitHubRepo;
import org.springframework.social.github.connect.GitHubConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.danigcaff.springframework.samples.spring_web.api.UserApi;
import com.danigcaff.springframework.samples.spring_web.persistence.Repository;
import com.danigcaff.springframework.samples.spring_web.persistence.User;
import com.danigcaff.springframework.samples.spring_web.persistence.mongo.RepositoryMongo;
import com.danigcaff.springframework.samples.spring_web.persistence.mongo.UserMongo;

@RestController
@EnableOAuth2Client
public class UserController implements UserApi {
	
	@Autowired
	OAuth2ClientContext oauth2ClientContext;
	
	@Value("${github.client.clientId}")
	private String clientId;
	@Value("${github.client.clientSecret}")
	private String clientSecret;
	
	@RequestMapping({ "/user", "/me" })
	public Map<String, String> user(Principal principal) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("gitHubUserId", principal.getName());
		return map;
	}
	
	@RequestMapping(value = "/user/create", method = RequestMethod.POST)
	public Map<String, String> createUser(@RequestBody Map<String, String> userData, @RequestBody Map<String, String> trelloBoardData) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		try{
			UserMongo.insert(userData);
			map.put("response", "ok");
			
			GitHub gitHub = new GitHubConnectionFactory(clientId, clientSecret)
					.createConnection(new AccessGrant(oauth2ClientContext.getAccessToken().getValue()))
					.getApi();
			List<GitHubRepo> listaRepos = gitHub.userOperations().getRepositories();
			
			for(int i=0;i<listaRepos.size();i++){
				Map<String, String> repoMap = new LinkedHashMap<String,String>();
				String repoId = Long.toString(listaRepos.get(i).getId());
				String repoName = listaRepos.get(i).getName();
				repoMap.put(RepositoryMongo.FIELDS.id.name(), repoId);
				repoMap.put(RepositoryMongo.FIELDS.repoId.name(), repoId);
				repoMap.put(RepositoryMongo.FIELDS.repoName.name(), repoName);
				repoMap.put(RepositoryMongo.FIELDS.boardId.name(), "");
				repoMap.put(RepositoryMongo.FIELDS.owner.name(), userData.get(UserMongo.FIELDS.id.name()));
				repoMap.put(RepositoryMongo.FIELDS.asoc.name(), "false");
				RepositoryMongo.insert(repoMap);
			}
			
			
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
