package de.thd.pms.model;

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
 * @author Josef Schneeberger (josef.schneeberger@th-deg.de)
 */
@Entity
@Table(name="tbl_fahrt")
public class Fahrt {
    @Id
    @GeneratedValue
	@Column(name="pk_fahrt")
    private Integer id;
	private Date created;
    private Date abfahrt;
    private Date ankunft;
	@ManyToOne
	@JoinColumn(name = "fk_boot", nullable = false)
	private Boot boot;
	@ManyToMany(targetEntity = de.thd.pms.model.Person.class,
			cascade={CascadeType.PERSIST, CascadeType.REMOVE},
			fetch = FetchType.LAZY)
	@JoinTable(
			name="tbl_personauffahrt",
			joinColumns={@JoinColumn(name="fk_fahrt")},
			inverseJoinColumns={@JoinColumn(name="fk_person")})
	private Set<Person> ruderer;

	public Fahrt() {
		abfahrt = new Date();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
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
