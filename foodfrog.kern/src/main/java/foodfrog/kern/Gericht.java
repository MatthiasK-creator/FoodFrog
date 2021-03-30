package foodfrog.kern;

import java.util.Date;
import java.util.List;

public class Gericht extends Entitaet{
	
	private String name;
	
	private String beschreibung;
	
	private int aufwand;
	
	private List<Bild> bilder;
	private List<Zutat> zutaten;
	private List<Kategorie> kategorien;
	
	
	public Gericht(int id, String name, String beschreibung, int aufwand) {
		super(id);
		this.name = name;
		this.beschreibung = beschreibung;
		this.aufwand = aufwand;
	}
	public Gericht(String name, String beschreibung, int aufwand) {
		super();
		this.name = name;
		this.beschreibung = beschreibung;
		this.aufwand = aufwand;
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
	public int getAufwand() {
		return aufwand;
	}
	public void setAufwand(int aufwand) {
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

	
	@Override
	public String toString() {
		return "ID: " + this.getId() + ", Name: " + this.name;
	}
	
	
	
	
	
	

}
