package foodfrog.kern;

import java.util.Date;

public class Gericht {
	
	private String name;
	private String beschreibung;
	private Date aufwand;
	
	
	public Gericht(String name, String beschreibung, Date aufwand) {
		super();
		this.name = name;
		this.beschreibung = beschreibung;
		this.aufwand = aufwand;
	}
	
	public Gericht() {
		super();
		// TODO Auto-generated constructor stub
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
	
	

}
