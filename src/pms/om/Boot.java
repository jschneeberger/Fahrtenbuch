/*
 * Created on 08.11.2006 14:36:38 Author: Josef Schneeberger (josef.schneeberger@fh-deggendorf.de)
 */
package pms.om;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author josef@dr-schneeberger.de
 */
@Entity
@Table(name = "tbl_boot")
public class Boot {
    @Id
    @GeneratedValue
	@Column(name = "pk_boot")
	private int id;
    private String name;
    private int sitze;
    private String klasse;
	@OneToMany(mappedBy = "boot")
	private Set<Fahrt> fahrten;

    public Boot() {
    }
    
    @Override
    public boolean equals(Object o) {
    	return id == ((Boot) o).getId();
    }

    public Boot(String n, String k, int s) {
    	name = n;
    	klasse = k;
    	sitze = s;
	}

	public Boot(String n) {
    	name = n;
    	klasse = "";
    	sitze = 0;
	}

	public int getId() {
		return id;
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

	public Set<Fahrt> getFahrten() {
		return fahrten;
	}

	public void setFahrten(Set<Fahrt> fahrten) {
		this.fahrten = fahrten;
	}

}
