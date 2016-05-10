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

import com.danigcaff.springframework.samples.spring_web.persistence.Repository;
import com.danigcaff.springframework.samples.spring_web.persistence.User;
import com.danigcaff.springframework.samples.spring_web.persistence.mongo.RepositoryMongo;
import com.danigcaff.springframework.samples.spring_web.persistence.mongo.UserMongo;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)

public class RepositoryMongoTest extends TestCase {

	@Autowired
	protected ApplicationContext ctx;
	
	@Test
	public void insertTest()
    {	String aux = new Date().toString();
		Map <String, String> repo = new LinkedHashMap <String, String>();
		repo.put(RepositoryMongo.FIELDS.owner.name(), "DaniGCaff");
		repo.put(RepositoryMongo.FIELDS.repoId.name(), "55555R");
		repo.put(RepositoryMongo.FIELDS.boardId.name(), "6666B");
		repo.put(RepositoryMongo.FIELDS.creation.name(), aux);
		
		RepositoryMongo.insert(repo);
        
        Repository repositorioLeer = new RepositoryMongo("55555R");
        
        assertEquals("Owner distinto", "DaniGCaff", repositorioLeer.getOwner());
        assertEquals("Repo distinto", "55555R", repositorioLeer.getRepoId());
        assertEquals("Board distinto", "6666B", repositorioLeer.getBoardId());
        assertEquals("Creation distinto", aux, repositorioLeer.getCreationDate());

    }

}
