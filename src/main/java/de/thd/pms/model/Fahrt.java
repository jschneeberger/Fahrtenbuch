package de.thd.pms.model;

import java.time.LocalDateTime;
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
    private Long id;
	private LocalDateTime created;
    private LocalDateTime abfahrt;
    private LocalDateTime ankunft;
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
		abfahrt = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getAbfahrt() {
		return abfahrt;
	}

	public void setAbfahrt(LocalDateTime abfahrt) {
		this.abfahrt = abfahrt;
	}

	public LocalDateTime getAnkunft() {
		return ankunft;
	}

	public void setAnkunft(LocalDateTime ankunft) {
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
