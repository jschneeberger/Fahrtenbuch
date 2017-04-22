package de.fhd.pms.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import de.fhd.pms.model.Person;

public class PersonDao extends HibernateDaoSupport {

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
		HibernateTemplate template = getHibernateTemplate();
		return (Person) template.get(Person.class, id);
	}

	public Person save(Person person) {
		HibernateTemplate template = getHibernateTemplate();
		template.saveOrUpdate(person);
		return person;
	}

	public void delete(Person person) {
		HibernateTemplate template = getHibernateTemplate();
		template.delete(person);
//		"Die Person kann nicht gelöscht werden, da sie bereits Fahrten unternommen hat."
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

}
