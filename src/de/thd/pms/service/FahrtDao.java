package de.thd.pms.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private SessionFactory sessionFactory;
	SimpleDateFormat deutschesDatumsFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");

	/**
	 * <p>returns all Fahrt objects, which are not yet termiated, i.e. which have a ankunft property == null.
	 * All the results are returned as FahrtPersonenDTO objects.</p>
	 * <p>In order to access lazy loaded properties, a HibernateCallback is used.</p>
	 * @return A sorted set of FahrtPersonenDTO objects
	 */
	@Transactional
	public Set<FahrtPersonenDTO> findNichtBeendetDTO() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Fahrt.class);
		criteria.add(Restrictions.isNull("ankunft"));
		@SuppressWarnings("unchecked")
		List<Fahrt> alleFahrten = criteria.list();
		return createDtoSet(alleFahrten);
	}

	/**
	 * <p>returns all Fahrt objects as FahrtPersonenDTO objects.</p>
	 * <p>In order to access lazy loaded properties of those, a HibernateCallback is used.</p>
	 * @return A sorted set of FahrtPersonenDTO objects
	 */
	@Transactional
	public Set<FahrtPersonenDTO> findAllDTO() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Fahrt.class);
		@SuppressWarnings("unchecked")
		List<Fahrt> alleFahrten = criteria.list();
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
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Fahrt> findAll() {
		Session session = sessionFactory.getCurrentSession();
		return (List<Fahrt>) session.createCriteria(Fahrt.class).list();
	}

	/**
	 * Starts a trip specifying a Boot id and a set of Person ids.
	 * @param bootId
	 * @param sitz
	 * @throws DaoException 
	 */
	@Transactional
	public void beginne(int bootId, Integer[] sitz) throws DaoException {
		Session session = sessionFactory.getCurrentSession();
		Boot b = (Boot) session.get(Boot.class, bootId);
		Set<Person> ruderer = new HashSet<Person>();
		for(Integer personId : sitz) {
			ruderer.add((Person) session.get(Person.class, personId));
		}
		Fahrt f = new Fahrt();
		f.setBoot(b);
		f.setRuderer(ruderer);
		try {
			session.saveOrUpdate(f);
		} catch (DataAccessException e) {
			throw new DaoException("Ein und der selbe Ruderer kann nicht zwei Mal an einer Fahrt teilnehmen.");
		}
	}

	/**
	 * Ends a trip by adding an ankunft date.
	 * @param id
	 */
	@Transactional
	public void beende(int id) {
		Session session = sessionFactory.getCurrentSession();
		Fahrt f = (Fahrt) session.get(Fahrt.class, id);
		f.setAnkunft(new Date());
		session.saveOrUpdate(f);
	}

	/**
	 * <p>returns all Fahrt objects as FahrtBootPersonDTO objects.</p>
	 * <p>In order to access lazy loaded properties of those, a HibernateCallback is used.</p>
	 * @return A sorted set of FahrtPersonenDTO objects
	 */
	@Transactional
	public List<FahrtBootPersonDTO> findAllVoll() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Fahrt.class);
		@SuppressWarnings("unchecked")
		List<Fahrt> alleFahrten = criteria.list();
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

	@Transactional
	public Set<Fahrt> findByBoot(Boot boot) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Fahrt.class);
		crit.add(Restrictions.eq("boot", boot));
		@SuppressWarnings("unchecked")
		List<Fahrt> tmp = crit.list();
		Set<Fahrt> result = new HashSet<Fahrt>();
		for (Fahrt f : tmp) {
			result.add(f);
		}
		return result;
	}

}
