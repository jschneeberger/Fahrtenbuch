package de.thd.pms.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.thd.pms.model.Fahrt;
import de.thd.pms.model.Person;

@Service
@Transactional
public class PersonDao {
	@SuppressWarnings("unused")
	private static Log logger = LogFactory.getLog(PersonDao.class);
    @PersistenceContext
    EntityManager entityManager;

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
		return (Person) entityManager.find(Person.class, id);
	}

	public Person save(Person person) {
		entityManager.merge(person);
		return person;
	}

	/**
	 * @return all {@link Person} objects from the database.
	 */
	public List<Person> findAll() {
		return (List<Person>) entityManager.createQuery("select p from Person p", Person.class).getResultList();
	}

	public void delete(Integer id) throws DaoException {
		Person p = findById(id);
		List<Fahrt> fahrten = entityManager.createQuery("select f from Fahrt f", Fahrt.class).getResultList();
		for (Fahrt fahrt : fahrten) {
			if (fahrt.getRuderer().contains(p)) {
				throw new DaoException("Die Person kann nicht gelöscht werden, da es Fahrten mit dieser Person gibt.");
			}
		}
		entityManager.remove(p);
	}

}
