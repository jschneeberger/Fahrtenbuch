package de.thd.pms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Josef Schneeberger (josef.schneeberger@th-deg.de)
 */
@Entity
@Table(name = "tbl_boot")
public class Boot {
    @Id
    @GeneratedValue
	@Column(name = "pk_boot")
	private Integer id;
	private Date created;
    private String name;
    private int sitze;
    private String klasse;

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

	/**
	 * Added to simplify thymeleaf input.
	 * @return an array of size sitze filled with integers starting from 0
	 */
	public Integer[] getSitzeArray() {
		Integer[] result = new Integer[sitze];
		for (int i = 0; i < sitze; i++) {
			result[i] = i;
		}
		return result;
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
