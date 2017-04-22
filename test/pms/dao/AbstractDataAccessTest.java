package pms.dao;

import org.hibernate.SessionFactory;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

public class AbstractDataAccessTest extends
		AbstractTransactionalDataSourceSpringContextTests {
	protected SessionFactory sessionFactory;

	/**
	 * Reference the Spring configuration file for the test case.
	 */
	protected String[] getConfigLocations() {
		return new String[] { "test-applicationContext.xml" };
	}

	/**
	 * Spring will automatically inject the SessionFactory instance on startup.
	 * Only necessary for Hibernate-backed DAO testing
	 */
	public void setSessionFactory(SessionFactory factory) {
		this.sessionFactory = factory;
	}

}
