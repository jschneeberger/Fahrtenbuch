package de.thd.pms.service;

/**
 * @author josef.schneeberger@th-deg.de
 */
public class BootDeleteException extends Exception {
	private static final long serialVersionUID = 6090554192367955585L;

	public BootDeleteException() {
		super("Das Boot kann nicht gelöscht werden, da bereits Fahrten mit diesem Boot durchgeführt worden sind.");
	}
}
