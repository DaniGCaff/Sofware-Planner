package com.danigcaff.springframework.samples.spring_web.api.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danigcaff.springframework.samples.spring_web.persistence.Board;
import com.danigcaff.springframework.samples.spring_web.persistence.Repository;
import com.danigcaff.springframework.samples.spring_web.persistence.mongo.BoardMongo;

@RestController
@Order(8)
public class BoardsController {
	
	@RequestMapping("/boards/{owner}")
	public List<Map<String,String>> listView(@PathVariable ("owner") String owner) {
		List<Board> listaRepos = BoardMongo.listAll(owner);
		List<Map<String, String>> listaIdNombre = new ArrayList<Map<String,String>>();
		
		Iterator<Board> it = listaRepos.iterator();
		while (it.hasNext()) {
			Map<String, String> map = new LinkedHashMap<String,String>();
			Board aux = (Board)it.next();
			map.put(BoardMongo.FIELDS.id.name(), aux.getId());
			map.put(BoardMongo.FIELDS.owner.name(), aux.getOwner());
			map.put(BoardMongo.FIELDS.boardName.name(), aux.getBoardName());
			listaIdNombre.add(map);
		}
		return listaIdNombre;
    } 

}
