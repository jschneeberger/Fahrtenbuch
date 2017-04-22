package de.hdu.pms.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdu.pms.model.Fahrt;
import de.hdu.pms.model.Person;

/**
 * Data transfer object used to represent a special version of Fahrt including a
 * rower and a boat.
 * 
 * @author josef@dr-schneeberger.de
 */
public class FahrtBootPersonDTO {
	private String nachname;
	private String vorname;
	private String abfahrt;
	private String ankunft;
	private String bootsname;

	public FahrtBootPersonDTO(Fahrt f, Person p) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm");
		nachname = p.getNachname();
		vorname = p.getVorname();
		abfahrt = sdf.format(f.getAbfahrt());
		Date a = f.getAnkunft();
		ankunft = (a == null ? "" : sdf.format(a));
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