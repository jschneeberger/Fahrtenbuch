package de.thd.pms.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import de.thd.pms.model.Boot;
import de.thd.pms.service.BootDao;

import static org.junit.Assert.*;

@ContextConfiguration("/test-application-context.xml")
public class BootDaoTest extends AbstractDataAccessTest {
	private BootDao bootDao;
	private String[] tables = {"tbl_boot"};

	@Autowired
	public void setBootDao(BootDao bootDao) {
		this.bootDao = bootDao;
	}

	@Test
	public void testFindById() {
		// deleteFromTables is provided by the Superclass AbstractDataAccessTest
		// delete all rows from db table
		deleteFromTables(tables);
		// create new line in table
		bootDao.create("Uno", 1, "Einer");
		bootDao.create("Due", 2, "Zweier");
		List<Boot> alleBoote = bootDao.findAll();
		for (Boot p : alleBoote) {
			int id = p.getId();
			Boot found = bootDao.findById(id);
			assertEquals(p.getName(), found.getName());
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
		String tabellennameBoot = "tbl_boot";
		String[] tables = {tabellennameBoot};
		deleteFromTables(tables);
		bootDao.create("Deggendorf", 4, "Vierer");
		int rows = countRowsInTable(tabellennameBoot);
		assertEquals("Die DB muss eine Zeile enthalten", 1, rows);
		// delete all rows from db table
		deleteFromTables(tables);
	}

	@Test
	public void testFindFreie() {
		// delete all rows from db table
		deleteFromTables(tables);
		// create new line in table
		bootDao.create("Uno", 1, "Einer");
		bootDao.create("Due", 2, "Zweier");
		List<Boot> alleBoote = bootDao.findFreie();
		for (Boot p : alleBoote) {
			int id = p.getId();
			Boot found = bootDao.findById(id);
			assertEquals(p.getName(), found.getName());
		}
		// delete all rows from db table
		deleteFromTables(tables);
	}

}
