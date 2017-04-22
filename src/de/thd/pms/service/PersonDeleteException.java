package de.thd.pms.service;

/**
 * @author josef.schneeberger@th-deg.de
 */
public class PersonDeleteException extends Exception {
	private static final long serialVersionUID = -8536780914680532570L;

	public PersonDeleteException() {
		super("Die Person kann nicht gelöscht werden, da sie bereits Fahrten unternommen hat.");
	}
}
