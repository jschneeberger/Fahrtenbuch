package pms.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.junit.Test;

import pms.dao.BootDao;
import pms.dao.FahrtDao;
import pms.dao.PersonDao;
import pms.dto.FahrtBootPersonDTO;
import pms.dto.FahrtPersonenDTO;
import pms.om.Boot;
import pms.om.Fahrt;
import pms.om.Person;


public class FahrtDaoTest extends AbstractDataAccessTest {
	private FahrtDao fahrtDao;
	private PersonDao personDao;
	private BootDao bootDao;

	public void setFahrtDao(FahrtDao fahrtDao) {
		this.fahrtDao = fahrtDao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	public void setBootDao(BootDao bootDao) {
		this.bootDao = bootDao;
	}

	/**
	 * Auxiliary method, generates Person, Boot, and Fahrt
	 */
	private void createEverything() {
		deleteEverything();
		// create two persons
		personDao.create("Uno", "First", "111");
		personDao.create("Due", "Second", "222");
		// store them in a set
		Set<Person> rowers = new HashSet<Person>();
		for (Person p : personDao.findAll()) {
			rowers.add(p);
		}
		// create a boat
		bootDao.create("Two", 2, "Zweier");
		// retrieve it from db
		Boot boot = null;
		for (Boot b : bootDao.findAll()) {
			boot = b;
		}
		Fahrt f = new Fahrt();
		f.setBoot(boot);
		f.setRuderer(rowers);
		sessionFactory.getCurrentSession().persist(f);
	}

	/**
	 * auxiliary method, deletes everything
	 */
	private void deleteEverything() {
		String[] tables = {"tbl_boot", "tbl_fahrt", "tbl_person", "tbl_personauffahrt"};
		// delete all rows from all db tables
		deleteFromTables(tables);
	}

	@Test
	public void testFindNichtBeendetDTO() {
		createEverything();
		Set<FahrtPersonenDTO> fahrten = fahrtDao.findNichtBeendetDTO();
		assertEquals(1, fahrten.size());
		deleteEverything();
	}

	@Test
	public void testFindAllDTO() {
		createEverything();
		Set<FahrtPersonenDTO> fahrten = fahrtDao.findAllDTO();
		assertEquals(1, fahrten.size());
		deleteEverything();
	}

	@Test
	public void testFindAll() {
		createEverything();
		List<Fahrt> fahrten = fahrtDao.findAll();
		assertEquals(1, fahrten.size());
		deleteEverything();
	}

	@Test
	public void testBeginne() {
		createEverything();
		int bootId = 0;
		for (Boot b : bootDao.findAll()) {
			bootId = b.getId();
		}
		Set<Integer> personenIds = new HashSet<Integer>();
		for (Person p : personDao.findAll()) {
			personenIds.add(p.getId());
		}
		fahrtDao.beginne(bootId, personenIds);
		// expect 2 - one created in createEverything, the other in beginne
		List<Fahrt> fahrten = fahrtDao.findAll();
		assertEquals(2, fahrten.size());
		deleteEverything();
	}

	@Test
	public void testBeende() {
		createEverything();
		for (Fahrt f : fahrtDao.findAll()) {
			fahrtDao.beende(f.getId());
		}
		for (Fahrt f : fahrtDao.findAll()) {
			assertNotNull(f.getAnkunft());
		}
		deleteEverything();
	}

	@Test
	public void testFindAllVoll() {
		createEverything();
		List<FahrtBootPersonDTO> fahrten = fahrtDao.findAllVoll();
		// expect to records!
		assertEquals(2, fahrten.size());
		deleteEverything();
	}

}
