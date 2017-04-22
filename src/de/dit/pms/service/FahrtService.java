package de.dit.pms.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

import de.dit.pms.dto.FahrtBootPersonDTO;
import de.dit.pms.dto.FahrtPersonenDTO;
import de.dit.pms.model.Boot;
import de.dit.pms.model.Fahrt;
import de.dit.pms.model.Person;


/**
 * @author josef@dr-schneeberger.de
 */
@Service
public class FahrtService {
	SimpleDateFormat deutschesDatumsFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
	private HibernateTemplate template;

	@Autowired
	public FahrtService(SessionFactory sessionFactory) {
		super();
		this.template = new HibernateTemplate(sessionFactory);
	}

	
	/**
	 * <p>returns all Fahrt objects, which are not yet termiated, i.e. which have a ankunft property == null.
	 * All the results are returned as FahrtPersonenDTO objects.</p>
	 * <p>In order to access lazy loaded properties, a HibernateCallback is used.</p>
	 * @return A sorted set of FahrtPersonenDTO objects
	 * @see http://static.springframework.org/spring/docs/2.5.x/reference/orm.html#orm-hibernate-template
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Set<FahrtPersonenDTO> findNichtBeendetDTO() {
		return (Set<FahrtPersonenDTO>) template.execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Fahrt.class);
				criteria.add(Restrictions.isNull("ankunft"));
				List<Fahrt> alleFahrten = criteria.list();
				return createDtoSet(alleFahrten);
			}
		});
	}

	/**
	 * <p>returns all Fahrt objects as FahrtPersonenDTO objects.</p>
	 * <p>In order to access lazy loaded properties of those, a HibernateCallback is used.</p>
	 * @return A sorted set of FahrtPersonenDTO objects
	 * @see http://static.springframework.org/spring/docs/2.5.x/reference/orm.html#orm-hibernate-template
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Set<FahrtPersonenDTO> findAllDTO() {
		return (Set<FahrtPersonenDTO>) template.execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Fahrt.class);
				List<Fahrt> alleFahrten = criteria.list();
				return createDtoSet(alleFahrten);
			}
		});
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
					mannschaftToString(f.getPersonen())));
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
		List<Fahrt> alleFahrten = template.loadAll(Fahrt.class);
		return alleFahrten;
	}

	/**
	 * Starts a trip specifying a Boot id and a set of Person ids.
	 * @param bootId
	 * @param sitz ids von den Personen
	 * @throws DaoException 
	 */
	public void beginne(int bootId, Integer[] sitz) throws DaoException {
		// Hole Boot aus der DB
		Boot b = (Boot) template.get(Boot.class, bootId);
		// Lege neue Fahrt an
		Fahrt f = new Fahrt();
		f.setBoot(b);
		f.setAbfahrt(new Date());
		// Jeden Ruderer der Fahrt zuordnen
		Set<Person> personen = new HashSet<Person>();
		for(Integer personId : sitz) {
			Person r = template.get(Person.class, personId);
			personen.add(r);
		}
		f.setPersonen(personen);
		template.save(f);
	}

	/**
	 * Ends a trip by adding an ankunft date.
	 * @param id
	 */
	public void beende(int id) {
		Fahrt f = (Fahrt) template.get(Fahrt.class, id);
		f.setAnkunft(new Date());
		template.saveOrUpdate(f);
	}

	/**
	 * <p>returns all {@link Fahrt} objects as {@link FahrtBootPersonDTO} objects.</p>
	 * <p>In order to access lazy loaded properties of those, a HibernateCallback is used.</p>
	 * @return A sorted set of FahrtPersonenDTO objects
	 * @see http://static.springframework.org/spring/docs/2.5.x/reference/orm.html#orm-hibernate-template
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<FahrtBootPersonDTO> findAllVoll() {
		return (List<FahrtBootPersonDTO>) template.execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Fahrt.class);
				List<Fahrt> alleFahrten = criteria.list();
				List<FahrtBootPersonDTO> liste = new LinkedList<FahrtBootPersonDTO>();
				for (Fahrt f : alleFahrten) {
					for (Person p : f.getPersonen()) {
						FahrtBootPersonDTO fbpDto = new FahrtBootPersonDTO(f, p);
						liste.add(fbpDto);
					}
				}
				return liste;
			}
		});
	}

	/**
	 * @param boot
	 * @return
	 * @see http://docs.jboss.org/hibernate/orm/3.3/reference/en/html/querycriteria.html
	 */
	@SuppressWarnings("unchecked")
	public Set<Fahrt> findByBoot(Boot boot) {
		Session hibernateSession = template.getSessionFactory().getCurrentSession();
		Criteria crit = hibernateSession.createCriteria(Fahrt.class);
		crit.add(Restrictions.eq("boot", boot));
		List<Fahrt> tmp = crit.list();
		// Typumwandlung von List<Fahrt> nach Set<Fahrt>
		Set<Fahrt> result = new HashSet<Fahrt>();
		for (Fahrt f : tmp) {
			result.add(f);
		}
		return result;
	}

}
