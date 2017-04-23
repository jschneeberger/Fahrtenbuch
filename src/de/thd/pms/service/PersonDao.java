package de.thd.pms.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.thd.pms.model.Fahrt;
import de.thd.pms.model.Person;

@Service
@Transactional
public class PersonDao {
	@SuppressWarnings("unused")
	private static Logger log = LogManager.getLogger(PersonDao.class);
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Creates and saves a {@link Person} object.
	 * @param vorname
	 * @param nachname
	 * @param telefon
	 */
	public void create(String vorname, String nachname, String telefon) {
		Person p = new Person();
		p.setVorname(vorname);
		p.setNachname(nachname);
		p.setTelefon(telefon);
		save(p);
	}

	/**
	 * returns a single person by its primary db key
	 * 
	 * @param id
	 * @return the Person
	 */
	public Person findById(int id) {
		Session session = sessionFactory.getCurrentSession();
		return (Person) session.get(Person.class, id);
	}

	public Person save(Person person) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(person);
		return person;
	}

	/**
	 * @return all {@link Person} objects from the database.
	 */
	@SuppressWarnings("unchecked")
	public List<Person> findAll() {
		Session session = sessionFactory.getCurrentSession();
		return (List<Person>) session.createCriteria(Person.class).list();
	}

	public void delete(Integer id) throws DaoException {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Fahrt.class).createCriteria("ruderer").add(Restrictions.eq("id", id));
		boolean keineFahrt = crit.list().isEmpty();
		if (keineFahrt) {
			Person person = (Person) session.get(Person.class, id);
			session.delete(person);
		} else {
			throw new DaoException("Die Person kann nicht gelöscht werden, da es Fahrten mit dieser Person gibt.");
		}
	}

}
