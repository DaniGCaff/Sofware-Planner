package org.spring.web;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.danigcaff.springframework.samples.spring_web.persistence.Task;
import com.danigcaff.springframework.samples.spring_web.persistence.User;
import com.danigcaff.springframework.samples.spring_web.persistence.mongo.TaskMongo;
import com.danigcaff.springframework.samples.spring_web.persistence.mongo.UserMongo;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class TaskMongoTest extends TestCase {
	
	@Autowired
	protected ApplicationContext ctx;
	
	@Test
	public void insertTest()
    {	//id, creation, lastModification, dateLastActivity, idBoard, idList, name, pos, due, shortUrl
		String aux = new Date().toString();
		Map <String, String> task = new LinkedHashMap <String, String>();
		task.put(TaskMongo.FIELDS.id.name(), "11111T");
		task.put(TaskMongo.FIELDS.creation.name(), aux);
		task.put(TaskMongo.FIELDS.lastModification.name(), aux);
		task.put(TaskMongo.FIELDS.dateLastActivity.name(), aux);
		task.put(TaskMongo.FIELDS.idBoard.name(), "22222B");
		task.put(TaskMongo.FIELDS.idList.name(), "333333L");
		task.put(TaskMongo.FIELDS.name.name(), "TarjetaPrueba");
		task.put(TaskMongo.FIELDS.pos.name(), "999999Pos");
		task.put(TaskMongo.FIELDS.due.name(), "Mañana");
		task.put(TaskMongo.FIELDS.shortUrl.name(), "/esurl");

	    TaskMongo.insert(task);
        
        Task taskLeer = new TaskMongo("11111T");
        
        assertEquals("Creation distinto", aux, taskLeer.getCreationDate());
        assertEquals("Last Mod distinto", aux, taskLeer.getLastModificationDate());
        assertEquals("Date Last Act distinto", aux, taskLeer.getDateLastActivity());
        assertEquals("IdBoard distinto", "22222B", taskLeer.getIdBoard());
        assertEquals("IdList distinto", "333333L", taskLeer.getIdList());
        assertEquals("Nombre distinto", "TarjetaPrueba", taskLeer.getName());
        assertEquals("Pos distinto", "999999Pos", taskLeer.getPos());
        assertEquals("Due distinto", "Mañana", taskLeer.getDue());
        assertEquals("ShortUrl distinto", "/esurl", taskLeer.getShortUrl());

    }

}
