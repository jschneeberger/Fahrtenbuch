package de.dit.pms.service;

import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.dit.pms.model.Person;

@Service("personService")
public class PersonService implements UserDetailsService {
	private HibernateTemplate template;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
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

	@Override
	public UserDetails loadUserByUsername(final String username)
			throws UsernameNotFoundException {
		return template.execute(new HibernateCallback<UserDetails>() {
			@Override
			public UserDetails doInHibernate(Session hibernateSession)
					throws HibernateException, SQLException {
				Criteria criteria = hibernateSession.createCriteria(Person.class);
				criteria.add(Restrictions.eq("userid", username));
				Person p = (Person) criteria.uniqueResult();
				Collection<GrantedAuthority> authorities = new LinkedList<GrantedAuthority>();
				User user = new User(p.getUserid(), p.getPassword(), authorities);
				return user;
			}
		});
	}

}
