package de.thd.pms.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.thd.pms.model.Person;
import de.thd.pms.repository.FahrtRepository;
import de.thd.pms.repository.PersonRepository;

@Service
@Transactional
public class PersonDao {
	@SuppressWarnings("unused")
	private static Logger log = LogManager.getLogger(PersonDao.class);
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private FahrtRepository fahrtRepository;

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
		personRepository.save(p);
	}

	/**
	 * returns a single person by its primary db key
	 * 
	 * @param id
	 * @return the Person
	 */
	public Person findById(Long id) {
		return personRepository.findById(id).get();
	}

	public Person save(Person person) {
		personRepository.save(person);
		return person;
	}

	/**
	 * @return all {@link Person} objects from the database.
	 */
	public Iterable<Person> findAll() {
		return personRepository.findAll();
	}

	public void delete(Long id) throws DaoException {
		Person ruderer = personRepository.findById(id).get();
		boolean keineFahrt = fahrtRepository.findByRuderer(ruderer ).isEmpty();
		if (keineFahrt) {
			personRepository.deleteById(id);
		} else {
			throw new DaoException("Die Person kann nicht gelöscht werden, da es Fahrten mit dieser Person gibt.");
		}
	}

}
