package org.spring.web;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.danigcaff.springframework.samples.spring_web.persistence.User;
import com.danigcaff.springframework.samples.spring_web.persistence.mongo.UserMongo;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class UserMongoTest extends TestCase {
	
	@Autowired
	protected ApplicationContext ctx;
	
	@Test
	public void insertTest()
    {	
		Map <String, String> user = new LinkedHashMap <String, String>();
		user.put(UserMongo.FIELDS.id.name(), "DaniGCaff");
		user.put(UserMongo.FIELDS.name.name(), "Dani");
		user.put(UserMongo.FIELDS.gitHubUserId.name(), "DaniGCaff");
		user.put(UserMongo.FIELDS.trelloUserId.name(), "DaniGCaffTrello");
		user.put(UserMongo.FIELDS.password.name(), "1234");
		
	    UserMongo.insert(user);
        
        User usuarioLeer;
		try {
			usuarioLeer = new UserMongo("DaniGCaff", "1234");
	        assertEquals("Nombre distinto", "Dani", usuarioLeer.getName());
	        assertEquals("Git distinto", "DaniGCaff", usuarioLeer.getGitHubUserId());
	        assertEquals("Trello distinto", "DaniGCaffTrello", usuarioLeer.getTrelloUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
        

    }

}
