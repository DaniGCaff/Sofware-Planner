package com.danigcaff.springframework.samples.spring_web;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.social.github.api.GitHub;
import org.springframework.social.github.api.GitHubHook;
import org.springframework.social.github.api.GitHubRepo;
import org.springframework.social.github.connect.GitHubConnectionFactory;
import org.springframework.social.github.connect.GitHubServiceProvider;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

@RestController
@EnableOAuth2Client
@Order(7)
public class ReposController implements BeanFactoryAware {
	
	private BeanFactory beanFactory;
	
	@Value("${github.client.clientId}")
	private String clientId;
	@Value("${github.client.clientSecret}")
	private String clientSecret;

	@Autowired
	OAuth2ClientContext oauth2ClientContext;
	
	@RequestMapping("/repos")
	public List<Map<String,String>> listView() {
		
		GitHub gitHub = new GitHubConnectionFactory(clientId, clientSecret)
		.createConnection(new AccessGrant(oauth2ClientContext.getAccessToken().getValue()))
		.getApi();
		String owner = gitHub.userOperations().getProfileId();
		
		List<GitHubRepo> listaRepos = gitHub.userOperations().getRepositories();
		List<Map<String, String>> listaIdNombre = new ArrayList<Map<String,String>>();
		
		for(int i=0;i<listaRepos.size();i++){
			Map<String, String> map = new LinkedHashMap<String,String>();
			String repoId = Long.toString(listaRepos.get(i).getId());
			Boolean repoAsoc = isRepoAsoc(owner, repoId);
			map.put("id", repoId);
			map.put("name", listaRepos.get(i).getName());
			map.put("asoc", Boolean.toString(repoAsoc));
			listaIdNombre.add(map);
		}	
		return listaIdNombre;
    }
	
	private boolean isRepoAsoc(String owner, String repoId) {
		DB database = MongoManager.getManager().getDatabase();
		if (database.collectionExists(MongoManager.COLLECTIONS.AUTORIZADOS.name())) {
			DBCollection coll = database.getCollection(MongoManager.COLLECTIONS.AUTORIZADOS.name());
			DBObject queryDoc = new BasicDBObject();
			queryDoc.put("owner", owner);
			queryDoc.put("repoId", repoId);
			DBObject result = coll.findOne(queryDoc);
			if(result != null) {
				return true;
			}
		}
		return false;
	}

	@RequestMapping("/repos/asociar/{owner}/{repoId}/{boardId}")
	public Map <String, String> asociate(@PathVariable("owner") String owner, @PathVariable("repoId") String repoId, @PathVariable("boardId") String boardId){
		
		DB database = MongoManager.getManager().getDatabase();
		if (!database.collectionExists(MongoManager.COLLECTIONS.AUTORIZADOS.name())) {
			DBObject options = BasicDBObjectBuilder.start().add("capped", false).get();
			database.createCollection(MongoManager.COLLECTIONS.AUTORIZADOS.name(), options);
		}
		DBCollection coll = database.getCollection(MongoManager.COLLECTIONS.AUTORIZADOS.name());
		
		DBObject asoc = new BasicDBObject();
		asoc.put("owner", owner);
		asoc.put("repoId", repoId);
		asoc.put("boardId", boardId);
		asoc.put("creation", new Date().toString());
		coll.insert(asoc);
		
		Map<String,String> map =new LinkedHashMap<String, String>();
		map.put("response", "ok");
		return map;
	}
	
	public void setBeanFactory(BeanFactory arg0) throws BeansException {
		this.beanFactory = arg0;
	}
}
