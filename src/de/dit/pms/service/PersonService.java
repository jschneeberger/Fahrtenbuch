package de.dit.pms.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

import de.dit.pms.model.Person;

@Service
public class PersonService {
	private HibernateTemplate template;

	@Autowired
	public PersonService(SessionFactory sessionFactory) {
		super();
		this.template = new HibernateTemplate(sessionFactory);
	}

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
		return (Person) template.get(Person.class, id);
	}

	public Person save(Person person) {
		template.saveOrUpdate(person);
		return person;
	}

	public void delete(Person person) {
		template.delete(person);
//		"Die Person kann nicht gelöscht werden, da sie bereits Fahrten unternommen hat."
	}

	/**
	 * @return all Person objects from the database.
	 */
	@SuppressWarnings("unchecked")
	public List<Person> findAll() {
		@SuppressWarnings("rawtypes")
		List results = template.loadAll(Person.class);
		return results;
	}

}
