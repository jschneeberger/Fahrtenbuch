package pms.dao;

import java.util.List;


import org.junit.Test;

import pms.dao.BootDao;
import pms.dao.BootDeleteException;
import pms.om.Boot;

public class BootDaoTest extends AbstractDataAccessTest {
	private BootDao bootDao;
	private String tableName = "tbl_boot";

	public void setBootDao(BootDao bootDao) {
		this.bootDao = bootDao;
	}

	@Test
	public void testFindById() {
		String[] tables = {tableName};
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
	public void testModify() {
		String[] tables = {tableName};
		// delete all rows from db table
		deleteFromTables(tables);
		// create new line in table
		bootDao.create("Tre", 3, "Dreier");
		// retrieve all existing records from table (just one)
		List<Boot> all = bootDao.findAll();
		String nn = "Quattro";
		// modify it
		for (Boot x : all) {
			int id = x.getId();
			bootDao.modify(id, nn, 4, "Vierer");
		}
		// retrieve all existing records from table (still just one)
		all = bootDao.findAll();
		Boot found = null;
		for (Boot x : all) {
			found = x;
		}
		// check the modified name
		assertEquals(nn, found.getName());
		// delete all rows from db table
		deleteFromTables(tables);
	}

	@Test
	public void testCreate() {
		String tabellennameBoot = "tbl_boot";
		String[] tables = {tabellennameBoot};
		deleteFromTables(tables);
		bootDao.create("Deggendorf", 4, "Vierer");
		int rows = countRowsInTable(tabellennameBoot);
		assertEquals("Die DB muss eine Zeile enthalten", 1, rows);
	}

	@Test
	public void testFindFreie() {
		String[] tables = {tableName};
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

	@Test
	public void testDeleteById() {
		String[] tables = {tableName};
		// delete all rows from db table
		deleteFromTables(tables);
		// create new line in table
		bootDao.create("Uno", 1, "Einer");
		// retrieve all existing records from table (just one)
		List<Boot> all = bootDao.findAll();
		// delete it
		for (Boot x : all) {
			int id = x.getId();
			try {
				bootDao.deleteById(id);
				assertEquals(0, countRowsInTable(tableName));
			} catch (BootDeleteException e) {
				fail("delete should be possible.");
			}
		}
	}

}
