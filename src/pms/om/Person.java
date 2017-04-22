/*
 * Created on 08.11.2006 14:40:29 Author: Josef Schneeberger (josef.schneeberger@fh-deggendorf.de)
 */
package pms.om;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author josef@dr-schneeberger.de
 */
@Entity
@Table(name="tbl_person")
public class Person {
    @Id
    @GeneratedValue
	@Column(name="pk_person")
    private int id;
    private String vorname;
    private String nachname;
    private String telefon;
	@ManyToMany(mappedBy = "ruderer", fetch = FetchType.LAZY)
	private Set<Fahrt> fahrten;
	
    public Person() {
    	vorname = "";
    	nachname = "";
    	telefon = "";
	}

	public Person(String vn, String nn, String tel) {
    	vorname = vn;
    	nachname = nn;
    	telefon = tel;
	}

	public Person(String vn, String nn) {
    	vorname = vn;
    	nachname = nn;
    	telefon = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public Set<Fahrt> getFahrten() {
		return fahrten;
	}

	public void setFahrten(Set<Fahrt> fahrten) {
		this.fahrten = fahrten;
	}

}
