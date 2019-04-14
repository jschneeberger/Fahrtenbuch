package de.thd.pms.dto;


import java.time.LocalDateTime;

import de.thd.pms.model.Fahrt;
import de.thd.pms.model.Person;
import de.thd.pms.utility.DateTimeUtility;

/**
 * Data transfer object used to represent a special version of Fahrt including a
 * rower and a boat.
 * 
 * @author josef.schneeberger@th-deg.de
 */
public class FahrtBootPersonDTO {
	private String nachname;
	private String vorname;
	private String abfahrt;
	private String ankunft;
	private String bootsname;

	public FahrtBootPersonDTO(Fahrt f, Person p) {
		nachname = p.getNachname();
		vorname = p.getVorname();
		abfahrt = f.getAbfahrt().format(DateTimeUtility.germanDateFormat);
		LocalDateTime a = f.getAnkunft();
		ankunft = (a == null ? "" : a.format(DateTimeUtility.germanDateFormat));
		bootsname = f.getBoot().getName();
	}

	public String getNachname() {
		return nachname;
	}

	public String getVorname() {
		return vorname;
	}

	public String getAbfahrt() {
		return abfahrt;
	}

	public String getAnkunft() {
		return ankunft;
	}

	public String getBootsname() {
		return bootsname;
	}
}