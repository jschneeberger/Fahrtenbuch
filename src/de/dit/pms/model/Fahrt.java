package de.dit.pms.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Fahrt {
	@Id
	@GeneratedValue
	private int id;
	private Date abfahrt;
	private Date ankunft;
	@ManyToOne(targetEntity = de.dit.pms.model.Boot.class)
	@JoinColumn(name = "fk_boot", nullable = false)
	private Boot boot;
	@ManyToMany
	@JoinTable(
		    name="fahrt_person",
		    joinColumns={@JoinColumn(name="fk_fahrt")},
		    inverseJoinColumns={@JoinColumn(name="fk_person")})
	private Set<Person> personen = new HashSet<Person>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getAbfahrt() {
		return abfahrt;
	}

	public void setAbfahrt(Date abfahrt) {
		this.abfahrt = abfahrt;
	}

	public Date getAnkunft() {
		return ankunft;
	}

	public void setAnkunft(Date ankunft) {
		this.ankunft = ankunft;
	}

	public Boot getBoot() {
		return boot;
	}

	public void setBoot(Boot boot) {
		this.boot = boot;
	}

	public Set<Person> getPersonen() {
		return personen;
	}

	public void setPersonen(Set<Person> personen) {
		this.personen = personen;
	}

}
