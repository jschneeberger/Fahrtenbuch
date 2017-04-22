package de.fhd.pms.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import de.fhd.pms.model.Boot;
import de.fhd.pms.model.Fahrt;


/**
 * The Data access class for boats. All Interaction with the database
 * regarding the entity bean Boot should be handled by this class!
 * @author josef@dr-schneeberger.de
 */
public class BootDao extends HibernateDaoSupport {
	private FahrtDao fahrtDao;
	
	public void setFahrtDao(FahrtDao fahrtDao) {
		this.fahrtDao = fahrtDao;
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
		HibernateTemplate template = getHibernateTemplate();
		return (Boot) template.get(Boot.class, id);
	}
	
	/**
	 * Returns all boats from the database.
	 * @return a list of Boot
	 * @see Boot
	 */
	public List<Boot> findAll() {
		HibernateTemplate template = getHibernateTemplate();
		return template.loadAll(Boot.class);
	}

	/**
	 * Saves the {@link Boot} specified by the parameter in the database.
	 * @param boot a {@link Boot} object that should be saved in the db.
	 * @return the object specified by the parameter
	 */
	public Boot save(Boot boot) {
		HibernateTemplate template = getHibernateTemplate();
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
	@SuppressWarnings("unchecked")
	public List<Boot> findFreie() {
		HibernateTemplate template = getHibernateTemplate();
		List<Fahrt> aktuelleFahrten = template.find("from Fahrt f where f.ankunft is null");
		List<Boot> alle = template.loadAll(Boot.class);
		List<Boot> result = new LinkedList<Boot>();
		List<Boot> belegteBoote = new LinkedList<Boot>();
		for (Fahrt fahrt : aktuelleFahrten) {
			belegteBoote.add(fahrt.getBoot());
		}
		for (Boot boot : alle) {
			if (!belegteBoote.contains(boot)) {
				result.add(boot);
			}
		}
		return result;
	}

	/**
	 * Deletes the specified {@link Boot} object from the database.
	 * @param boot the {@link Boot} to be deleted.
	 * @throws DaoException if the specified {@link Boot} is included in a {@link Fahrt}
	 */
	public void delete(Boot boot) throws DaoException {
		HibernateTemplate template = getHibernateTemplate();
		Set<Fahrt> fahrtenVonBoot = fahrtDao.findByBoot(boot);
		if (fahrtenVonBoot.size() == 0) {
			template.delete(boot);
		} else {
			throw new DaoException("Das Boot kann nicht gelöscht werden, da bereits Fahrten mit diesem Boot durchgeführt worden sind.");
		}
	}

}