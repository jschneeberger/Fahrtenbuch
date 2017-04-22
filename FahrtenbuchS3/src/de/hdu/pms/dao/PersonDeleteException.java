package de.hdu.pms.dao;

/**
 * @author josef@dr-schneeberger.de
 */
public class PersonDeleteException extends Exception {
	private static final long serialVersionUID = -8536780914680532570L;

	public PersonDeleteException() {
		super("Die Person kann nicht gelöscht werden, da sie bereits Fahrten unternommen hat.");
	}
}
