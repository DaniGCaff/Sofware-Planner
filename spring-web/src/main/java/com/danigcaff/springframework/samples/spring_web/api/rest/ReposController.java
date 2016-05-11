package com.danigcaff.springframework.samples.spring_web.api.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.social.github.api.GitHub;
import org.springframework.social.github.api.GitHubRepo;
import org.springframework.social.github.connect.GitHubConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danigcaff.springframework.samples.spring_web.api.ReposApi;
import com.danigcaff.springframework.samples.spring_web.persistence.Repository;
import com.danigcaff.springframework.samples.spring_web.persistence.mongo.RepositoryMongo;
import com.danigcaff.springframework.samples.spring_web.util.MongoManager;
import com.danigcaff.springframework.samples.spring_web.util.MongoManager.COLLECTIONS;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

@RestController
@Order(7)
public class ReposController implements ReposApi {
	/* (non-Javadoc)
	 * @see com.danigcaff.springframework.samples.spring_web.api.ReposApi#listView()
	 */
	@RequestMapping("/repos/{owner}")
	public List<Map<String,String>> listView(@PathVariable ("owner") String owner) {
		List<Repository> listaRepos = RepositoryMongo.listAll(owner);
		List<Map<String, String>> listaIdNombre = new ArrayList<Map<String,String>>();
		
		Iterator<Repository> it = listaRepos.iterator();
		if (it.hasNext()){
			Map<String, String> map = new LinkedHashMap<String,String>();
			Repository aux = (Repository)it.next();
			map.put(RepositoryMongo.FIELDS.id.name(), aux.getRepoId());
			map.put(RepositoryMongo.FIELDS.repoName.name(), aux.getRepoName());
			map.put(RepositoryMongo.FIELDS.repoId.name(), aux.getRepoId());
			map.put(RepositoryMongo.FIELDS.boardId.name(), aux.getBoardId());
			map.put(RepositoryMongo.FIELDS.asoc.name(), aux.getAsoc().toString());
			map.put(RepositoryMongo.FIELDS.owner.name(), aux.getOwner());
			listaIdNombre.add(map);
		}
		return listaIdNombre;
    }
	
	private boolean isRepoAsoc(String owner, String repoId) {
		DBCollection coll = MongoManager.getManager().getCollection(COLLECTIONS.AUTORIZADOS);
		DBObject queryDoc = new BasicDBObject("owner", owner)
								.append("repoId", repoId);
		DBObject result = coll.findOne(queryDoc);
		if(result != null) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.danigcaff.springframework.samples.spring_web.api.ReposApi#asociate(java.lang.String, java.lang.String, java.lang.String)
	 */
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
	
	/* (non-Javadoc)
	 * @see com.danigcaff.springframework.samples.spring_web.api.ReposApi#list(java.lang.String)
	 */
	@RequestMapping("/repos/board/{repoId}")
	public Map <String, String> list(@PathVariable("repoId") String repoId){
		/*
		 * A partir del repoId se debe conseguir el boardId en la collection de autorizados.
		 */
		Map<String, String> map = new LinkedHashMap<String, String>();
		String response = "ok";
		DB database = MongoManager.getManager().getDatabase();
		if (database.collectionExists(MongoManager.COLLECTIONS.AUTORIZADOS.name())) {
			DBCollection coll = database.getCollection(MongoManager.COLLECTIONS.AUTORIZADOS.name());
			DBObject options = BasicDBObjectBuilder.start().add("repoId", repoId).get();
			DBObject fields = BasicDBObjectBuilder.start().add("boardId", 1).get();
			DBObject result = coll.findOne(options, fields);
			map.put("boardId", result.get("boardId").toString());
		} else {
			response = "not ok";
		}
		map.put("response", response);
		return map;
	}
}
