package de.thd.pms.dto;

import java.time.LocalDateTime;

/**
 * Data transfer object
 * used to represent a special version of Fahrt with all the rowers denoted in a String.
 * @author josef.schneeberger@th-deg.de
 */
public class FahrtPersonenDTO implements Comparable<FahrtPersonenDTO> {
	private LocalDateTime abfahrtDate;
	private Long id;
	private String bootsname;
	private String abfahrt;
	private String ankunft;
	private String mannschaft = "";
	
	public FahrtPersonenDTO(LocalDateTime abfahrtDate, Long id, String bootsname,
			String abfahrt, String ankunft, String mannschaft) {
		this.abfahrtDate = abfahrtDate;
		this.id = id;
		this.bootsname = bootsname;
		this.abfahrt = abfahrt;
		this.ankunft = ankunft;
		this.mannschaft = mannschaft;
	}

	public Long getId() {
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
