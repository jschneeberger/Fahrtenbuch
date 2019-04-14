package de.thd.pms.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.thd.pms.model.Boot;
import de.thd.pms.model.Fahrt;
import de.thd.pms.repository.BootRepository;
import de.thd.pms.repository.FahrtRepository;


/**
 * The Data access class for {@link Boot} objects. All Interaction with the database
 * regarding the entity bean {@link Boot} should be handled by this class!
 * @author josef.schneeberger@th-deg.de
 */
@Service
@Transactional
public class BootDao {
	@Autowired
	private FahrtDao fahrtDao;
	@Autowired
	private BootRepository bootRepository;
	@Autowired
	private FahrtRepository fahrtRepository;

	/**
	 * Creates a new {@link Boot} and saves it in the DB.
	 * @param name
	 * @param anzahlSitze
	 * @param klasse
	 */
	public void create(String name, int anzahlSitze, String klasse) {
		Boot b = new Boot(name, klasse, anzahlSitze);
		bootRepository.save(b);
	}

	/**
	 * Returns a single boat by its primary db key
	 * @param id the primary key of a {@link Boot}
	 * @return a single Boot
	 */
	public Boot findById(Long id) {
		return bootRepository.findById(id).get();
	}
	
	/**
	 * Returns all boats from the database.
	 * @return a list of Boot
	 * @see Boot
	 */
	public Iterable<Boot> findAll() {
		return bootRepository.findAll();
	}

	/**
	 * Saves the {@link Boot} specified by the parameter in the database.
	 * @param boot a {@link Boot} object that should be saved in the db.
	 * @return the object specified by the parameter
	 */
	public Boot save(Boot boot) {
		bootRepository.save(boot);
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
		List<Fahrt> aktuelleFahrten = fahrtRepository.findByAnkunftIsNull();
		Iterable<Boot> alle = bootRepository.findAll();
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
	public void delete(Long id) throws DaoException {
		Boot boot = bootRepository.findById(id).get();
		Set<Fahrt> fahrtenVonBoot = fahrtDao.findByBoot(boot);
		if (fahrtenVonBoot.size() == 0) {
			bootRepository.delete(boot);
		} else {
			throw new DaoException("Das Boot kann nicht gelöscht werden, da bereits Fahrten mit diesem Boot durchgeführt worden sind.");
		}
	}

}