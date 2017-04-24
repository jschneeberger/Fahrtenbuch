package de.thd.pms.dao;

import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;

import de.thd.pms.dto.FahrtBootPersonDTO;
import de.thd.pms.dto.FahrtPersonenDTO;
import de.thd.pms.model.Boot;
import de.thd.pms.model.Fahrt;
import de.thd.pms.model.Person;
import de.thd.pms.service.BootDao;
import de.thd.pms.service.DaoException;
import de.thd.pms.service.FahrtDao;
import de.thd.pms.service.PersonDao;

import static org.junit.Assert.*;

@ContextConfiguration("/test-application-context.xml")
public class FahrtDaoTest extends AbstractDataAccessTest {
	private FahrtDao fahrtDao;
	private PersonDao personDao;
	private BootDao bootDao;
	private String[] tables = {"tbl_fahrt", "tbl_boot", "tbl_person"};

	@Autowired
	public void setFahrtDao(FahrtDao fahrtDao) {
		this.fahrtDao = fahrtDao;
	}

	@Autowired
	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	@Autowired
	public void setBootDao(BootDao bootDao) {
		this.bootDao = bootDao;
	}

	@Before
	public void before() {
		try {
			// create two persons
			personDao.create("Uno", "First", "111");
			personDao.create("Due", "Second", "222");
			// store them in a set
			List<Person> rowers = personDao.findAll();
			Integer[] sitze = new Integer[rowers.size()];
			int index = 0;
			for (Person p : rowers) {
				sitze[index] = p.getId();
				index++;
			}
			// create a boat
			bootDao.create("Two", 2, "Zweier");
			// retrieve it from db
			for (Boot b : bootDao.findAll()) {
				fahrtDao.beginne(b.getId(), sitze);
			}
		} catch (DaoException e) {
			fail("Could not create objects.");
		}
	}
	
	@After
	public void after() {
		try {
			deleteFromTables(tables);
		} catch (DataIntegrityViolationException e) {
			// Nothing to do: this may happen when the database is already empty. 
		}
	}
	
	@Test
	public void testFindNichtBeendetDTO() throws DaoException {
		Set<FahrtPersonenDTO> fahrten = fahrtDao.findNichtBeendetDTO();
		assertEquals(1, fahrten.size());
	}

	@Test
	public void testFindAllDTO() throws DaoException {
		Set<FahrtPersonenDTO> fahrten = fahrtDao.findAllDTO();
		assertEquals(1, fahrten.size());
	}

	@Test
	public void testFindAll() throws DaoException {
		List<Fahrt> fahrten = fahrtDao.findAll();
		assertEquals(1, fahrten.size());
	}

	@Test
	public void testBeginne() throws DaoException {
		int bootId = 0;
		for (Boot b : bootDao.findAll()) {
			bootId = b.getId();
		}
		List<Person> anzahlPersonen = personDao.findAll();
		Integer[] personenIds = new Integer[anzahlPersonen.size()];
		int i = 0;
		for (Person p : anzahlPersonen) {
			personenIds[i] = p.getId();
			i++;
		}
		try {
			fahrtDao.beginne(bootId, personenIds);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// expect 2 - one created in createEverything, the other in beginne
		List<Fahrt> fahrten = fahrtDao.findAll();
		assertEquals(2, fahrten.size());
	}

	@Test
	public void testBeende() throws DaoException {
		for (Fahrt f : fahrtDao.findAll()) {
			fahrtDao.beende(f.getId());
		}
		for (Fahrt f : fahrtDao.findAll()) {
			assertNotNull(f.getAnkunft());
		}
		
	}

	@Test
	public void testFindAllVoll() throws DaoException {
		List<FahrtBootPersonDTO> fahrten = fahrtDao.findAllVoll();
		// expect two records!
		assertEquals(2, fahrten.size());
	}

}
