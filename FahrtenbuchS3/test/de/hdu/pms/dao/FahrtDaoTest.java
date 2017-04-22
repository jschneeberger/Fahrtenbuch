package de.hdu.pms.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import de.hdu.pms.dao.BootDao;
import de.hdu.pms.dao.DaoException;
import de.hdu.pms.dao.FahrtDao;
import de.hdu.pms.dao.PersonDao;
import de.hdu.pms.dto.FahrtBootPersonDTO;
import de.hdu.pms.dto.FahrtPersonenDTO;
import de.hdu.pms.model.Boot;
import de.hdu.pms.model.Fahrt;
import de.hdu.pms.model.Person;


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

	@Test
	public void testFindNichtBeendetDTO() {
		createEverything();
		Set<FahrtPersonenDTO> fahrten = fahrtDao.findNichtBeendetDTO();
		assertEquals(1, fahrten.size());
	}

	@Test
	public void testFindAllDTO() {
		createEverything();
		Set<FahrtPersonenDTO> fahrten = fahrtDao.findAllDTO();
		assertEquals(1, fahrten.size());
	}

	@Test
	public void testFindAll() {
		createEverything();
		List<Fahrt> fahrten = fahrtDao.findAll();
		assertEquals(1, fahrten.size());
	}

	@Test
	public void testBeginne() {
		createEverything();
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
	public void testBeende() {
		createEverything();
		for (Fahrt f : fahrtDao.findAll()) {
			fahrtDao.beende(f.getId());
		}
		for (Fahrt f : fahrtDao.findAll()) {
			assertNotNull(f.getAnkunft());
		}
	}

	@Test
	public void testFindAllVoll() {
		createEverything();
		List<FahrtBootPersonDTO> fahrten = fahrtDao.findAllVoll();
		// expect two records!
		assertEquals(2, fahrten.size());
	}

}
