package de.hdu.pms.dto;


import java.util.Date;

/**
 * Data transfer object
 * used to represent a special version of Fahrt with all the rowers denoted in a String.
 * @author josef@dr-schneeberger.de
 */
public class FahrtPersonenDTO implements Comparable<FahrtPersonenDTO> {
	private Date abfahrtDate;
	private int id;
	private String bootsname;
	private String abfahrt;
	private String ankunft;
	private String mannschaft = "";
	
	public FahrtPersonenDTO(Date abfahrtDate, int id, String bootsname,
			String abfahrt, String ankunft, String mannschaft) {
		this.abfahrtDate = abfahrtDate;
		this.id = id;
		this.bootsname = bootsname;
		this.abfahrt = abfahrt;
		this.ankunft = ankunft;
		this.mannschaft = mannschaft;
	}

	public int getId() {
		return id;
	}

	public String getBootsname() {
		return bootsname;
	}
	
	public String getAbfahrt() {
		return abfahrt;
	}
	
	public String getMannschaft() {
		return mannschaft;
	}

	@Override
	public int compareTo(FahrtPersonenDTO o) {
		return this.abfahrtDate.compareTo(o.abfahrtDate);
	}

	public String getAnkunft() {
		return ankunft;
	}

}
