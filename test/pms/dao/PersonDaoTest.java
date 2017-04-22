package pms.dao;

import java.util.List;


import org.junit.Test;

import pms.dao.PersonDao;
import pms.dao.PersonDeleteException;
import pms.om.Person;

public class PersonDaoTest extends AbstractDataAccessTest {
	private PersonDao personDao;
	private String tableName = "tbl_person";

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

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

	@Test
	public void testModify() {
		String[] tables = {tableName};
		// delete all rows from db table
		deleteFromTables(tables);
		// create new line in table
		personDao.create("Uno", "First", "111");
		// retrieve all existing records from table (just one)
		List<Person> alleRuderer = personDao.findAll();
		String nn = "Third";
		// modify it
		for (Person p : alleRuderer) {
			int id = p.getId();
			personDao.modify(id, "Tre", nn, "333");
		}
		// retrieve all existing records from table (still just one)
		alleRuderer = personDao.findAll();
		Person found = null;
		for (Person p : alleRuderer) {
			found = p;
		}
		// check the modified name
		assertEquals(nn, found.getNachname());
		// delete all rows from db table
		deleteFromTables(tables);
	}

	@Test
	public void testDeleteById() {
		String[] tables = {tableName};
		// delete all rows from db table
		deleteFromTables(tables);
		// create new line in table
		personDao.create("Uno", "First", "111");
		// retrieve all existing records from table (just one)
		List<Person> alleRuderer = personDao.findAll();
		// delete it
		for (Person p : alleRuderer) {
			int id = p.getId();
			try {
				personDao.deleteById(id);
				assertEquals(0, countRowsInTable(tableName));
			} catch (PersonDeleteException e) {
				fail("delete person should be possible.");
			}
		}
	}

}
