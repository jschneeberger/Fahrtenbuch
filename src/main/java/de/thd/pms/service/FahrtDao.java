package de.thd.pms.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.thd.pms.dto.FahrtBootPersonDTO;
import de.thd.pms.dto.FahrtPersonenDTO;
import de.thd.pms.model.Boot;
import de.thd.pms.model.Fahrt;
import de.thd.pms.model.Person;
import de.thd.pms.repository.BootRepository;
import de.thd.pms.repository.FahrtRepository;
import de.thd.pms.repository.PersonRepository;
import de.thd.pms.utility.DateTimeUtility;


/**
 * @author josef.schneeberger@th-deg.de
 */
@Service
@Transactional
public class FahrtDao {
	@Autowired
	private FahrtRepository fahrtRepository;
	@Autowired
	private BootRepository bootRepository;
	@Autowired
	private PersonRepository personRepository;
	
	/**
	 * <p>returns all Fahrt objects, which are not yet termiated, i.e. which have a ankunft property == null.
	 * All the results are returned as FahrtPersonenDTO objects.</p>
	 * <p>In order to access lazy loaded properties, a HibernateCallback is used.</p>
	 * @return A sorted set of FahrtPersonenDTO objects
	 */
	public Set<FahrtPersonenDTO> findNichtBeendetDTO() {
		List<Fahrt> tmp = fahrtRepository.findByAnkunftIsNull();
		Set<FahrtPersonenDTO> result = new TreeSet<>();
		for (Fahrt fahrt : tmp) {
			String mannschaft = "";
			String trenner = "";
			for (Person r : fahrt.getRuderer()) {
				mannschaft = mannschaft + trenner + r.getVorname() + " " + r.getNachname();
				trenner = ", ";
			}
			result.add(new FahrtPersonenDTO(fahrt.getAbfahrt(),
					fahrt.getId(),
					fahrt.getBoot().getName(),
					fahrt.getAbfahrt().format(DateTimeUtility.germanDateFormat),
					(fahrt.getAnkunft() == null ? "" : fahrt.getAnkunft().format(DateTimeUtility.germanDateFormat)),
					mannschaft));
		}
		return result;
	}

	/**
	 * <p>returns all Fahrt objects as FahrtPersonenDTO objects.</p>
	 * <p>In order to access lazy loaded properties of those, a HibernateCallback is used.</p>
	 * @return A sorted set of FahrtPersonenDTO objects
	 */
	public Set<FahrtPersonenDTO> findAllDTO() {
		Iterable<Fahrt> alleFahrten = fahrtRepository.findAll();
		return createDtoSet(alleFahrten);
	}

	/**
	 * Private utility function
	 * @param alleFahrten a List of persistent Fahrt objects
	 * @return a Set of FahrtPersonenDTO objects
	 */
	private Set<FahrtPersonenDTO> createDtoSet(Iterable<Fahrt> alleFahrten) {
		Set<FahrtPersonenDTO> results = new TreeSet<FahrtPersonenDTO>();
		for (Fahrt f : alleFahrten) {
			LocalDateTime a = f.getAnkunft();
			results.add(new FahrtPersonenDTO(f.getAbfahrt(), 
					f.getId(), 
					f.getBoot().getName(),
					f.getAbfahrt().format(DateTimeUtility.germanDateFormat),
					(a == null ? "" : a.format(DateTimeUtility.germanDateFormat)),
					mannschaftToString(f.getRuderer())));
		}
		return results;
	}

	/**
	 * Private utility function
	 * @param rudererSet a Set of Person objects
	 * @return a String of the names of those Person objects
	 */
	private String mannschaftToString(Set<Person> rudererSet) {
		String mannschaft = "";
		String trenner = "";
		for (Person ruderer : rudererSet) {
			mannschaft = mannschaft + trenner + ruderer.getVorname() + " "
					+ ruderer.getNachname();
			trenner = ", ";
		}
		return mannschaft;
	}

	/**
	 * @return all Fahrt objects in the database.
	 */
	public Iterable<Fahrt> findAll() {
		return fahrtRepository.findAll();
	}

	/**
	 * Starts a trip specifying a Boot id and a set of Person ids.
	 * @param bootId
	 * @param sitz
	 * @throws DaoException 
	 */
	public void beginne(Long bootId, Long[] sitz) throws DaoException {
		Boot b = bootRepository.findById(bootId).get();
		Set<Person> ruderer = new HashSet<Person>();
		for(Long personId : sitz) {
			ruderer.add(personRepository.findById(personId).get());
		}
		Fahrt f = new Fahrt();
		f.setBoot(b);
		f.setRuderer(ruderer);
		try {
			fahrtRepository.save(f);
		} catch (DataAccessException e) {
			throw new DaoException("Ein und der selbe Ruderer kann nicht zwei Mal an einer Fahrt teilnehmen.");
		}
	}

	/**
	 * Ends a trip by adding an ankunft date.
	 * @param id
	 */
	public void beende(Long id) {
		Fahrt f = fahrtRepository.findById(id).get();
		f.setAnkunft(LocalDateTime.now());
		fahrtRepository.save(f);
	}

	/**
	 * <p>returns all Fahrt objects as FahrtBootPersonDTO objects.</p>
	 * <p>In order to access lazy loaded properties of those, a HibernateCallback is used.</p>
	 * @return A sorted set of FahrtPersonenDTO objects
	 */
	public List<FahrtBootPersonDTO> findAllVoll() {
		Iterable<Fahrt> alleFahrten = fahrtRepository.findAll();
		List<FahrtBootPersonDTO> liste = new LinkedList<FahrtBootPersonDTO>();
		for (Fahrt f : alleFahrten) {
			Set<Person> ruderer = f.getRuderer();
			for (Person p : ruderer) {
				FahrtBootPersonDTO fbpDto = new FahrtBootPersonDTO(f, p);
				liste.add(fbpDto);
			}
		}
		return liste;
	}

	public Set<Fahrt> findByBoot(Boot boot) {
		return fahrtRepository.findByBoot(boot);
	}

}
