package de.dit.pms.service;

import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

import de.dit.pms.model.Boot;
import de.dit.pms.model.Fahrt;


/**
 * The Data access class for boats. All Interaction with the database
 * regarding the entity bean Boot should be handled by this class!
 * @author josef@dr-schneeberger.de
 */
@Service
public class BootService {
	private HibernateTemplate template;
	@Autowired
	private FahrtService fahrtService;

	@Autowired
	public BootService(SessionFactory sessionFactory) {
		super();
		this.template = new HibernateTemplate(sessionFactory);
	}

	
	/**
	 * Creates a new {@link Boot} and saves it in the DB.
	 * @param name
	 * @param anzahlSitze
	 * @param klasse
	 */
	public void create(String name, int anzahlSitze, String klasse) {
		Boot b = new Boot(name, klasse, anzahlSitze);
		save(b);
	}

	/**
	 * Returns a single boat by its primary db key
	 * @param id the primary key of a {@link Boot}
	 * @return a single Boot
	 */
	public Boot findById(int id) {
		return (Boot) template.get(Boot.class, id);
	}
	
	/**
	 * Returns all boats from the database.
	 * @return a list of Boot
	 * @see Boot
	 */
	public List<Boot> findAll() {
		return template.loadAll(Boot.class);
	}

	/**
	 * Saves the {@link Boot} specified by the parameter in the database.
	 * @param boot a {@link Boot} object that should be saved in the db.
	 * @return the object specified by the parameter
	 */
	public Boot save(Boot boot) {
		template.saveOrUpdate(boot);
		return boot;
	}

	/**
	 * Returns the list of {@link Boot} objects which are currently not on a trip.
	 * The method works as follows:
	 * <ul>
	 * <li>An HQL query is specified and stored in a string variable</li>
	 * <li>Retrieve all {@link Boot} objects from the db</li>
	 * <li>Create an empty list for results and empty boats</li>
	 * </ul>
	 * @return a List of Boot
	 */
	public List<Boot> findFreie() {
		List<Boot> boote = findAll();
		for (Fahrt f : fahrtService.findAll()) {
			if (f.getAnkunft() == null) {
				boote.remove(f.getBoot());
			}
		}
		return boote;
	}

	/**
	 * Deletes the specified {@link Boot} object from the database.
	 * @param id the primary key of the {@link Boot} to be deleted.
	 * @throws DaoException if the specified {@link Boot} is included in a {@link Fahrt}
	 */
	public void delete(Integer id) throws DaoException {
		Boot boot = findById(id);
		Set<Fahrt> fahrtenVonBoot = fahrtService.findByBoot(boot);
		if (fahrtenVonBoot.size() == 0) {
			template.delete(boot);
		} else {
			throw new DaoException("Das Boot kann nicht gelöscht werden, da bereits Fahrten mit diesem Boot durchgeführt worden sind.");
		}
	}

}