/*
 * Created on 08.11.2006 14:38:43 Author: Josef Schneeberger (josef.schneeberger@fh-deggendorf.de)
 */
package pms.om;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author josef@dr-schneeberger.de
 */
@Entity
@Table(name="tbl_fahrt")
public class Fahrt {
    @Id
    @GeneratedValue
	@Column(name="pk_fahrt")
    private int id;
    private Date abfahrt;
    private Date ankunft;
	@ManyToOne
	@JoinColumn(name = "fk_boot", nullable = false)
	private Boot boot;
	@ManyToMany(targetEntity = pms.om.Person.class,
			cascade={CascadeType.PERSIST},
			fetch = FetchType.LAZY)
	@JoinTable(
			name="tbl_personauffahrt",
			joinColumns={@JoinColumn(name="fk_fahrt")},
			inverseJoinColumns={@JoinColumn(name="fk_person")})
	private Set<Person> ruderer;

	public Fahrt() {
		abfahrt = new Date();
	}

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

	public Set<Person> getRuderer() {
		return ruderer;
	}

	public void setRuderer(Set<Person> ruderer) {
		this.ruderer = ruderer;
	}
	
	
}
