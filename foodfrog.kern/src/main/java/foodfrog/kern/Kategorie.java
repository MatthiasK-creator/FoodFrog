package foodfrog.kern;

import java.util.ArrayList;
import java.util.List;

public class Kategorie {
	private String kategorie;
	private List<Kategorie> unterKategorien;
	
	public Kategorie() {
		super();
	}

	public Kategorie(String kategorie) {
		super();
		this.kategorie = kategorie;
		this.unterKategorien = new ArrayList<Kategorie>();
	}
	public Kategorie(String kategorie, List<Kategorie> kategorien) {
		this.kategorie = kategorie;
		this.unterKategorien = kategorien;
	}

	public String getKategorie() {
		return kategorie;
	}

	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}
	
	public void setUnterKategorien(List<Kategorie> unterKategorien) {
		this.unterKategorien = unterKategorien;
	}
	public List<Kategorie> getUnterKategorien() {
		return unterKategorien;
	}
	
	

}
