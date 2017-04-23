package de.thd.pms.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import de.thd.pms.model.Person;
import de.thd.pms.service.PersonDao;

import static org.junit.Assert.*;

@ContextConfiguration("/test-application-context.xml")
public class PersonDaoTest extends AbstractDataAccessTest {
	private PersonDao personDao;
	private String[] tables = {"tbl_person"};

	@Autowired
	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	@Test
	public void testFindById() {
		// delete all rows from db table
		deleteFromTables(tables);
		// create new line in table
		personDao.create("Uno", "First", "111");
		personDao.create("Due", "Second", "222");
		List<Person> alleRuderer = personDao.findAll();
		for (Person p : alleRuderer) {
			int id = p.getId();
			Person found = personDao.findById(id);
			assertEquals(p.getNachname(), found.getNachname());
		}
		// delete all rows from db table
		deleteFromTables(tables);
	}

	@Test
	public void testFindAll() {
		// already tested by testFindById()
		testFindById();
	}

	@Test
	public void testCreate() {
		// already tested by testFindById()
		testFindById();
	}

}
