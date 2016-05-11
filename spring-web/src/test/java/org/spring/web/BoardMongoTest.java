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

import com.danigcaff.springframework.samples.spring_web.persistence.Board;
import com.danigcaff.springframework.samples.spring_web.persistence.Repository;
import com.danigcaff.springframework.samples.spring_web.persistence.mongo.BoardMongo;
import com.danigcaff.springframework.samples.spring_web.persistence.mongo.RepositoryMongo;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)

public class BoardMongoTest extends TestCase {

	@Autowired
	protected ApplicationContext ctx;
	
	@Test
	public void insertTest()
    {	String aux = new Date().toString();
		Map <String, String> board = new LinkedHashMap <String, String>();
		board.put(BoardMongo.FIELDS.owner.name(), "DaniGCaff");
		board.put(BoardMongo.FIELDS.id.name(), "55555R");
		board.put(BoardMongo.FIELDS.lastModification.name(), aux);
		board.put(BoardMongo.FIELDS.creation.name(), aux);
		board.put(BoardMongo.FIELDS.boardName.name(), "Nombre de Board");

		
		BoardMongo.insert(board);
        
        Board boardLeer = new BoardMongo("55555R");
        
        assertEquals("Owner distinto", "DaniGCaff", boardLeer.getOwner());
        assertEquals("Id distinto", "55555R", boardLeer.getId());
        assertEquals("Last Mod distinto", aux, boardLeer.getLastModificationDate());
        assertEquals("Creation distinto", aux, boardLeer.getCreationDate());
        assertEquals("BoardName distinto", "Nombre de Board", boardLeer.getBoardName());


    }
}
