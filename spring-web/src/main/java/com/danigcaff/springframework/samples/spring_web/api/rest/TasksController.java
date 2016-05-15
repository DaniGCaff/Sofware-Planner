package com.danigcaff.springframework.samples.spring_web.api.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danigcaff.springframework.samples.spring_web.api.TasksApi;
import com.danigcaff.springframework.samples.spring_web.persistence.Task;
import com.danigcaff.springframework.samples.spring_web.persistence.mongo.TaskMongo;

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
	
	@RequestMapping("/tasks/board/{boardId}")
	public List<Map<String,String>> listTasks(@PathVariable("boardId") String boardId){
		List<Task> listaRepos = TaskMongo.listAll(boardId);
		List<Map<String, String>> listaIdNombre = new ArrayList<Map<String,String>>();
		
		Iterator<Task> it = listaRepos.iterator();
		while (it.hasNext()) {
			Map<String, String> map = new LinkedHashMap<String,String>();
			Task aux = (Task)it.next();
			map.put(TaskMongo.FIELDS.id.name(), aux.getId());
			map.put(TaskMongo.FIELDS.idBoard.name(), aux.getIdBoard());
			map.put(TaskMongo.FIELDS.due.name(), aux.getDue());
			map.put(TaskMongo.FIELDS.name.name(), aux.getName());
			map.put(TaskMongo.FIELDS.desc.name(), aux.getDesc());
			map.put(TaskMongo.FIELDS.shortUrl.name(), aux.getShortUrl());
			map.put(TaskMongo.FIELDS.creation.name(), aux.getCreationDate());
			listaIdNombre.add(map);
		}
		return listaIdNombre;
	}
	
	@RequestMapping("/tasks/commits/{taskId}")
	public List<Map<String,String>> listView(@PathVariable ("taskId") String taskId) {
		List<Map<String,String>> listaCommits = TaskMongo.listCommitsFor(taskId);
		return listaCommits;
    }
	
	@RequestMapping("/tasks/{taskId}")
	public Map<String,String> listATask(@PathVariable("taskId") String taskId){
		Map<String, String> map = new LinkedHashMap<String,String>();
		Task aux = new TaskMongo(taskId);
		map.put(TaskMongo.FIELDS.id.name(), aux.getId());
		map.put(TaskMongo.FIELDS.idBoard.name(), aux.getIdBoard());
		map.put(TaskMongo.FIELDS.due.name(), aux.getDue());
		map.put(TaskMongo.FIELDS.name.name(), aux.getName());
		map.put(TaskMongo.FIELDS.desc.name(), aux.getDesc());
		map.put(TaskMongo.FIELDS.shortUrl.name(), aux.getShortUrl());
		map.put(TaskMongo.FIELDS.creation.name(), aux.getCreationDate());
		return map;
	}
}
