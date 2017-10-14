package de.thd.pms.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.thd.pms.dto.FahrtBootPersonDTO;
import de.thd.pms.dto.FahrtPersonenDTO;
import de.thd.pms.model.Boot;
import de.thd.pms.model.Fahrt;
import de.thd.pms.model.Person;


/**
 * @author josef.schneeberger@th-deg.de
 */
@Service
@Transactional
public class FahrtDao {
    @PersistenceContext
    EntityManager entityManager;
    @Deprecated // don't use SimpleDateFormat. Use the new Joda time API.
	SimpleDateFormat deutschesDatumsFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");

	/**
	 * <p>returns all Fahrt objects, which are not yet termiated, i.e. which have a ankunft property == null.
	 * All the results are returned as FahrtPersonenDTO objects.</p>
	 * <p>In order to access lazy loaded properties, a HibernateCallback is used.</p>
	 * @return A sorted set of FahrtPersonenDTO objects
	 */
	public Set<FahrtPersonenDTO> findNichtBeendetDTO() {
		CriteriaQuery<Fahrt> criteria = entityManager.getCriteriaBuilder().createQuery(Fahrt.class);
		Root<Fahrt> f = criteria.from(Fahrt.class);
		criteria.where(f.get("ankunft").isNotNull());
		TypedQuery<Fahrt> q = entityManager.createQuery(criteria);
		List<Fahrt> alleFahrten = q.getResultList();
		return createDtoSet(alleFahrten);
	}

	/**
	 * Private utility function
	 * @param fahrten a List of persistent Fahrt objects
	 * @return a Set of FahrtPersonenDTO objects
	 */
	private Set<FahrtPersonenDTO> createDtoSet(List<Fahrt> fahrten) {
		Set<FahrtPersonenDTO> results = new TreeSet<FahrtPersonenDTO>();
		for (Fahrt f : fahrten) {
			Date a = f.getAnkunft();
			results.add(new FahrtPersonenDTO(f.getAbfahrt(), 
					f.getId(), 
					f.getBoot().getName(),
					deutschesDatumsFormat.format(f.getAbfahrt()),
					(a == null ? "" : deutschesDatumsFormat.format(a)),
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
	public List<Fahrt> findAll() {
		CriteriaQuery<Fahrt> criteria = entityManager.getCriteriaBuilder().createQuery(Fahrt.class);
		criteria.select(criteria.from(Fahrt.class));
		return (List<Fahrt>) entityManager.createQuery(criteria).getResultList();
	}

	/**
	 * <p>returns all Fahrt objects as FahrtPersonenDTO objects.</p>
	 * <p>In order to access lazy loaded properties of those, a HibernateCallback is used.</p>
	 * @return A sorted set of FahrtPersonenDTO objects
	 */
	public Set<FahrtPersonenDTO> findAllDTO() {
		return createDtoSet(findAll());
	}

	/**
	 * Starts a trip specifying a Boot id and a set of Person ids.
	 * @param bootId
	 * @param sitz
	 * @throws DaoException 
	 */
	public void beginne(int bootId, Integer[] sitz) throws DaoException {
		Boot b = (Boot) entityManager.find(Boot.class, bootId);
		Set<Person> ruderer = new HashSet<Person>();
		for(Integer personId : sitz) {
			ruderer.add((Person) entityManager.find(Person.class, personId));
		}
		Fahrt f = new Fahrt();
		f.setBoot(b);
		f.setRuderer(ruderer);
		try {
			entityManager.persist(f);
		} catch (DataAccessException e) {
			throw new DaoException("Ein und der selbe Ruderer kann nicht zwei Mal an einer Fahrt teilnehmen.");
		}
	}

	/**
	 * Ends a trip by adding an ankunft date.
	 * @param id
	 */
	public void beende(int id) {
		Fahrt f = (Fahrt) entityManager.find(Fahrt.class, id);
		f.setAnkunft(new Date());
		entityManager.merge(f);
	}

	/**
	 * <p>returns all Fahrt objects as FahrtBootPersonDTO objects.</p>
	 * <p>In order to access lazy loaded properties of those, a HibernateCallback is used.</p>
	 * @return A sorted set of FahrtPersonenDTO objects
	 */
	public List<FahrtBootPersonDTO> findAllVoll() {
		List<Fahrt> alleFahrten = findAll();
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
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Fahrt> cq = cb.createQuery(Fahrt.class);
		Root<Fahrt> fahrt = cq.from(Fahrt.class);
		cq.where(cb.equal(fahrt.get("boot"), boot));
		TypedQuery<Fahrt> q = entityManager.createQuery(cq);
		List<Fahrt> tmp = q.getResultList();
		Set<Fahrt> result = new HashSet<Fahrt>();
		for (Fahrt f : tmp) {
			result.add(f);
		}
		return result;
	}

}
