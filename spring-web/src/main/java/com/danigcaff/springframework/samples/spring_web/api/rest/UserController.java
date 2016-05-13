package com.danigcaff.springframework.samples.spring_web.api.rest;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
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
import com.danigcaff.springframework.samples.spring_web.persistence.User;
import com.danigcaff.springframework.samples.spring_web.persistence.mongo.BoardMongo;
import com.danigcaff.springframework.samples.spring_web.persistence.mongo.RepositoryMongo;
import com.danigcaff.springframework.samples.spring_web.persistence.mongo.TaskMongo;
import com.danigcaff.springframework.samples.spring_web.persistence.mongo.UserMongo;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

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
	public Map<String, String> createUser(@RequestBody String data) {
		JSONObject json = new JSONObject(data);
		Map<String, String> map = new LinkedHashMap<String, String>();
		try{
			UserMongo.insert((DBObject) (JSON.parse(json.getJSONObject("user").toString())));
			map.put("response", "ok");
			
			GitHub gitHub = new GitHubConnectionFactory(clientId, clientSecret)
					.createConnection(new AccessGrant(oauth2ClientContext.getAccessToken().getValue()))
					.getApi();
			
			List<GitHubRepo> listaRepos = gitHub.userOperations().getRepositories();
			for(GitHubRepo repo : listaRepos) {
				Map<String, String> repoMap = new LinkedHashMap<String,String>();
				String repoId = Long.toString(repo.getId());
				String repoName = repo.getName();
				repoMap.put(RepositoryMongo.FIELDS.id.name(), repoId);
				repoMap.put(RepositoryMongo.FIELDS.repoId.name(), repoId);
				repoMap.put(RepositoryMongo.FIELDS.repoName.name(), repoName);
				repoMap.put(RepositoryMongo.FIELDS.boardId.name(), "");
				repoMap.put(RepositoryMongo.FIELDS.owner.name(), json.getJSONObject("user").get(UserMongo.FIELDS.id.name()).toString());
				repoMap.put(RepositoryMongo.FIELDS.asoc.name(), "false");
				RepositoryMongo.insert(repoMap);
			}
			
			JSONArray boardsArray = json.getJSONArray("boardData");
			for (int i = 0; i < boardsArray.length(); i++) {
				Map<String, String> mapBoards = new LinkedHashMap<String, String>();
				mapBoards.put(BoardMongo.FIELDS.id.name(), boardsArray.getJSONObject(i).get("id").toString());
				mapBoards.put(BoardMongo.FIELDS.boardName.name(), boardsArray.getJSONObject(i).get("name").toString());
				mapBoards.put(BoardMongo.FIELDS.owner.name(), json.getJSONObject("user").get(UserMongo.FIELDS.trelloUserId.name()).toString());
				BoardMongo.insert(mapBoards);
			}
			
			JSONArray tasksArray = json.getJSONArray("taskData");
			for (int i = 0; i < tasksArray.length(); i++) {
				Map<String, String> mapTasks = new LinkedHashMap<String, String>();
				mapTasks.put(TaskMongo.FIELDS.id.name(), tasksArray.getJSONObject(i).get("id").toString());
				mapTasks.put(TaskMongo.FIELDS.creation.name(), tasksArray.getJSONObject(i).get("creation").toString());
				mapTasks.put(TaskMongo.FIELDS.lastModification.name(), tasksArray.getJSONObject(i).get("lastModification").toString());
				mapTasks.put(TaskMongo.FIELDS.dateLastActivity.name(), tasksArray.getJSONObject(i).get("dateLastActivity").toString());
				mapTasks.put(TaskMongo.FIELDS.idBoard.name(), tasksArray.getJSONObject(i).get("idBoard").toString());
				mapTasks.put(TaskMongo.FIELDS.idList.name(), tasksArray.getJSONObject(i).get("idList").toString());
				mapTasks.put(TaskMongo.FIELDS.name.name(), tasksArray.getJSONObject(i).get("name").toString());
				mapTasks.put(TaskMongo.FIELDS.pos.name(), tasksArray.getJSONObject(i).get("pos").toString());
				mapTasks.put(TaskMongo.FIELDS.due.name(), tasksArray.getJSONObject(i).get("due").toString());
				mapTasks.put(TaskMongo.FIELDS.shortUrl.name(), tasksArray.getJSONObject(i).get("shortUrl").toString());
				TaskMongo.insert(mapTasks);
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
