package pms.dao;

import java.util.List;
import java.util.Set;


import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import pms.om.Fahrt;
import pms.om.Person;

/**
 * @author josef@dr-schneeberger.de
 */
public class PersonDao extends HibernateDaoSupport {
	/**
	 * returns a single person by its primary db key
	 * @param id
	 * @return the Person
	 */
	public Person findById(int id) {
		HibernateTemplate template = getHibernateTemplate();
		return (Person) template.get(Person.class, id);
	}
	
	/**
	 * @return all Person objects from the database.
	 */
	@SuppressWarnings("unchecked")
	public List<Person> findAll() {
		HibernateTemplate template = getHibernateTemplate();
		@SuppressWarnings("rawtypes")
		List results = template.loadAll(Person.class);
		return results;
	}

	/**
	 * creates and persists a new Person object.
	 * @param vorname
	 * @param nachname
	 * @param telefon
	 */
	public void create(String vorname, String nachname, String telefon) {
		HibernateTemplate template = getHibernateTemplate();
		Person p = new Person(vorname, nachname, telefon);
		template.persist(p);
	}

	/**
	 * Modifies an existing persistent Person object.
	 * @param id the primary key used in the database
	 * @param vorname
	 * @param nachname
	 * @param telefon
	 */
	public void modify(int id, String vorname, String nachname, String telefon) {
		HibernateTemplate template = getHibernateTemplate();
		Person p = (Person) template.get(Person.class, id);
		p.setVorname(vorname);
		p.setNachname(nachname);
		p.setTelefon(telefon);
		template.saveOrUpdate(p);
	}

	/**
	 * deletes a person using it's id.
	 * @param id
	 * @throws PersonDeleteException if the Person is stored in a Fahrt object
	 */
	public void deleteById(int id) throws PersonDeleteException {
		HibernateTemplate template = getHibernateTemplate();
		Person p = findById(id);
		Set<Fahrt> fahrten = p.getFahrten();
		if (fahrten == null || fahrten.size() == 0) {
			template.delete(p);
		} else {
			throw new PersonDeleteException();
		}
		template.flush();
	}
}
