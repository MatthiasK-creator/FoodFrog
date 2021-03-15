package foodfrog.kern;

import java.util.ArrayList;
import java.util.List;

public class Kategorie {
	private int id;
	private String bezeichnung;
	
	public Kategorie() {
		super();
	}

	public Kategorie(int id, String bezeichnung) {
		super();
		this.id = id;
		this.bezeichnung = bezeichnung;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	public int getId() {
		return id;
	}
	

	

}
