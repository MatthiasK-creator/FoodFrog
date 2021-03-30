package foodfrog.kern;

public class Kategorie extends Entitaet {
	private String bezeichnung;
	

	public Kategorie(int id, String bezeichnung) {
		super(id);
		this.bezeichnung = bezeichnung;
	}
	
	public Kategorie(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	@Override
	public String toString() {
		return "Kategorie: " + this.bezeichnung;
	}

	

}
