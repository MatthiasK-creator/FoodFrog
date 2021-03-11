package foodfrog.kern;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Gericht {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "Beschreibung")
	private String beschreibung;
	
	@Column(name = "Aufwand")
	private Date aufwand;
	
	@ManyToOne
	private List<Bild> bilder;
	@ManyToOne
	private List<Zutat> zutaten;
	@ManyToMany
	private List<Kategorie> kategorien;
	
	
	public Gericht(String name, String beschreibung, Date aufwand) {
		super();
		this.name = name;
		this.beschreibung = beschreibung;
		this.aufwand = aufwand;
	}
	
	public Gericht() {
		super();
	}

	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBeschreibung() {
		return beschreibung;
	}
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
	public Date getAufwand() {
		return aufwand;
	}
	public void setAufwand(Date aufwand) {
		this.aufwand = aufwand;
	}

	public List<Bild> getBilder() {
		return bilder;
	}

	public void setBilder(List<Bild> bilder) {
		this.bilder = bilder;
	}

	public List<Zutat> getZutaten() {
		return zutaten;
	}

	public void setZutaten(List<Zutat> zutaten) {
		this.zutaten = zutaten;
	}

	public List<Kategorie> getKategorien() {
		return kategorien;
	}

	public void setKategorien(List<Kategorie> kategorien) {
		this.kategorien = kategorien;
	}

	public long getId() {
		return id;
	}
	
	
	
	
	
	

}
