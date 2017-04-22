package de.dit.pms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Boot {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private int sitze;
	private String klasse;
	
	public Boot() {
	}
	
	public Boot(String name, String klasse, int sitze) {
		super();
		this.name = name;
		this.sitze = sitze;
		this.klasse = klasse;
	}

	@Override
	public String toString() {
		return "Boot [id=" + id + ", name=" + name + ", sitze=" + sitze
				+ ", klasse=" + klasse + "]";
	}

	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSitze() {
		return sitze;
	}

	public void setSitze(int sitze) {
		this.sitze = sitze;
	}

	public String getKlasse() {
		return klasse;
	}

	public void setKlasse(String klasse) {
		this.klasse = klasse;
	}
	
}
