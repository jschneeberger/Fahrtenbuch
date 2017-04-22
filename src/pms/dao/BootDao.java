package pms.dao;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import pms.om.Boot;
import pms.om.Fahrt;

/**
 * The Data access class for boats. All Interaction with the database
 * regarding the entity bean Boot should be handled by this class!
 * @author josef@dr-schneeberger.de
 */
public class BootDao extends HibernateDaoSupport {
	private PersonDao personDao;
	
	public PersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	/**
	 * returns a single boat by its primary db key
	 * @param id
	 * @return a single Boot
	 */
	public Boot findById(int id) {
		HibernateTemplate template = getHibernateTemplate();
		return (Boot) template.get(Boot.class, id);
	}
	
	/**
	 * Returns all boats from the database.
	 * @return a list of Boot
	 * @see Boot
	 */
	@SuppressWarnings("unchecked")
	public List<Boot> findAll() {
		HibernateTemplate template = getHibernateTemplate();
		
//		SessionFactory hibernateSessionFactory = template.getSessionFactory();
//		Session sess = hibernateSessionFactory.getCurrentSession();
//		SQLQuery sqlQuery = sess.createSQLQuery("SELECT * FROM tbl_boot");
//		List boote = sqlQuery.list();
		
//		Criteria crit = sess.createCriteria(Boot.class);
//		List boote = crit.list();
//		return boote;
		
		return template.loadAll(Boot.class);
	}

	/**
	 * modifies an existing boat in the database which is selected by its primary key.
	 * @param id the primary key of the boat.
	 * @param name the new name for the boat
	 * @param sitze the new number of of places in the boat 
	 * @param klasse the class of the boat (e.g. "skiff")
	 * @see Boot
	 */
	public void modify(int id, String name, int sitze, String klasse) {
		HibernateTemplate template = getHibernateTemplate();
		Boot boot = (Boot) template.get(Boot.class, id);
		boot.setKlasse(klasse);
		boot.setName(name);
		boot.setSitze(sitze);
		template.saveOrUpdate(boot);
	}

	/**
	 * creates a new persistent boat in the database providing all the necessary details.
	 * @param name the name for the new boat
	 * @param sitze number of places
	 * @param klasse class
	 * @see Boot
	 */
	public void create(String name, int sitze, String klasse) {
		HibernateTemplate template = getHibernateTemplate();
		Boot boot = new Boot(name, klasse, sitze);
		template.persist(boot);
	}

	/**
	 * returns the list of boats which are currently not on a trip.
	 * @return a List of Boot
	 */
	@SuppressWarnings("unchecked")
	public List<Boot> findFreie() {
		HibernateTemplate template = getHibernateTemplate();
		List<Fahrt> aktuelleFahrten = template.find("from Fahrt f where f.ankunft is null");
		List<Boot> alle = template.loadAll(Boot.class);
		List<Boot> result = new LinkedList<Boot>();
		List<Boot> belegteBoote = new LinkedList<Boot>();
		for (Fahrt fahrt : aktuelleFahrten) {
			belegteBoote.add(fahrt.getBoot());
		}
		for (Boot boot : alle) {
			if (!belegteBoote.contains(boot)) {
				result.add(boot);
			}
		}
		return result;
	}

	/**
	 * deletes a boat using it's id.
	 * @param id
	 * @throws BootDeleteException if there are Fahrt objects involving the boat.
	 */
	public void deleteById(final int id) throws BootDeleteException {
		HibernateTemplate template = getHibernateTemplate();
		boolean success = (Boolean) template.execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Boot b = (Boot) session.load(Boot.class, id);
				Set<Fahrt> fahrten = b.getFahrten();
				if (fahrten == null || fahrten.size() == 0) {
					session.delete(b);
					return true;
				} else {
					return false;
				}
			}
		});
		if (!success) throw new BootDeleteException();
	}

}