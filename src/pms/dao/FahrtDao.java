package pms.dao;

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
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import pms.dto.FahrtBootPersonDTO;
import pms.dto.FahrtPersonenDTO;
import pms.om.Boot;
import pms.om.Fahrt;
import pms.om.Person;


/**
 * @author josef@dr-schneeberger.de
 */
public class FahrtDao extends HibernateDaoSupport {
	SimpleDateFormat deutschesDatumsFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");

	/**
	 * <p>returns all Fahrt objects, which are not yet termiated, i.e. which have a ankunft property == null.
	 * All the results are returned as FahrtPersonenDTO objects.</p>
	 * <p>In order to access lazy loaded properties, a HibernateCallback is used.</p>
	 * @return A sorted set of FahrtPersonenDTO objects
	 * @see http://static.springframework.org/spring/docs/2.5.x/reference/orm.html#orm-hibernate-template
	 */
	@SuppressWarnings("unchecked")
	public Set<FahrtPersonenDTO> findNichtBeendetDTO() {
		HibernateTemplate template = getHibernateTemplate();
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
	@SuppressWarnings("unchecked")
	public Set<FahrtPersonenDTO> findAllDTO() {
		HibernateTemplate template = getHibernateTemplate();
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
	public List<Fahrt> findAll() {
		HibernateTemplate template = getHibernateTemplate();
		@SuppressWarnings("rawtypes")
		List alleFahrten = template.loadAll(Fahrt.class);
		return (List<Fahrt>) alleFahrten;
	}

	/**
	 * Starts a trip specifying a Boot id and a set of Person ids.
	 * @param bootId
	 * @param personenIds
	 */
	public void beginne(int bootId, Set<Integer> personenIds) {
		HibernateTemplate template = getHibernateTemplate();
		Boot b = (Boot) template.get(Boot.class, bootId);
		Set<Person> ruderer = new HashSet<Person>();
		for(Integer personId : personenIds) {
			ruderer.add((Person) template.get(Person.class, personId));
		}
		Fahrt f = new Fahrt();
		f.setBoot(b);
		f.setRuderer(ruderer);
		template.saveOrUpdate(f);
	}

	/**
	 * Ends a trip by adding an ankunft date.
	 * @param id
	 */
	public void beende(int id) {
		HibernateTemplate template = getHibernateTemplate();
		Fahrt f = (Fahrt) template.get(Fahrt.class, id);
		f.setAnkunft(new Date());
		template.saveOrUpdate(f);
	}

	/**
	 * <p>returns all Fahrt objects as FahrtBootPersonDTO objects.</p>
	 * <p>In order to access lazy loaded properties of those, a HibernateCallback is used.</p>
	 * @return A sorted set of FahrtPersonenDTO objects
	 * @see http://static.springframework.org/spring/docs/2.5.x/reference/orm.html#orm-hibernate-template
	 */
	@SuppressWarnings("unchecked")
	public List<FahrtBootPersonDTO> findAllVoll() {
		HibernateTemplate template = getHibernateTemplate();
		return (List<FahrtBootPersonDTO>) template.execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Fahrt.class);
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
		});
	}

}
