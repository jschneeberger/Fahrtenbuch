package de.thd.pms.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.thd.pms.model.Boot;
import de.thd.pms.model.Fahrt;


/**
 * The Data access class for {@link Boot} objects. All Interaction with the database
 * regarding the entity bean {@link Boot} should be handled by this class!
 * @author josef.schneeberger@th-deg.de
 */
@Repository
@Transactional
public class BootDao {
    @PersistenceContext
    private EntityManager entityManager;
	@Autowired
	private FahrtDao fahrtDao;

	/**
	 * Creates a new {@link Boot} and saves it in the DB.
	 * @param name
	 * @param anzahlSitze
	 * @param klasse
	 */
	public void create(String name, int anzahlSitze, String klasse) {
		Boot b = new Boot(name, klasse, anzahlSitze);
		entityManager.persist(b);
	}

	/**
	 * Returns a single boat by its primary db key
	 * @param id the primary key of a {@link Boot}
	 * @return a single Boot
	 */
	public Boot findById(int id) {
		return (Boot) entityManager.find(Boot.class, id);
	}
	
	/**
	 * Returns all boats from the database.
	 * @return a list of Boot
	 * @see Boot
	 */
	public List<Boot> findAll() {
		CriteriaQuery<Boot> criteria = entityManager.getCriteriaBuilder().createQuery(Boot.class);
		criteria.select(criteria.from(Boot.class));
		return (List<Boot>) entityManager.createQuery(criteria).getResultList();
	}

	/**
	 * Saves the {@link Boot} specified by the parameter in the database.
	 * @param boot a {@link Boot} object that should be saved in the db.
	 * @return the object specified by the parameter
	 */
	public Boot save(Boot boot) {
		entityManager.persist(boot);
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
		Session session = entityManager.unwrap(Session.class);
		List<Fahrt> aktuelleFahrten = session.createQuery("from Fahrt f where f.ankunft is null").list();
		List<Boot> alle = session.createCriteria(Boot.class).list();
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
	 * @param id the primary key of the {@link Boot} to be deleted.
	 * @throws DaoException if the specified {@link Boot} is included in a {@link Fahrt}
	 */
	public void delete(Integer id) throws DaoException {
		Boot boot = findById(id);
		Set<Fahrt> fahrtenVonBoot = fahrtDao.findByBoot(boot);
		if (fahrtenVonBoot.size() == 0) {
			entityManager.unwrap(Session.class).delete(boot);
		} else {
			throw new DaoException("Das Boot kann nicht gelöscht werden, da bereits Fahrten mit diesem Boot durchgeführt worden sind.");
		}
	}

}