package de.fhd.pms.dao;

import java.util.List;

import org.junit.Test;

import de.fhd.pms.model.Person;

public class PersonDaoTest extends AbstractDataAccessTest {
	private PersonDao personDao;
	private String tableName = "tbl_person";

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testFindById() {
		String[] tables = {tableName};
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
