package foodfrog.kern;

public class Zutat {
	
	private String bezeichnung;
	private int menge;
	private Einheit einheit;
	
	
	public Zutat() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Zutat(String bezeichnung, int menge) {
		super();
		this.bezeichnung = bezeichnung;
		this.menge = menge;
	}
	
	public String getBezeichnung() {
		return bezeichnung;
	}
	
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	public int getMenge() {
		return menge;
	}
	
	public void setMenge(int menge) {
		this.menge = menge;
	}

	public Einheit getEinheit() {
		return einheit;
	}

	public void setEinheit(Einheit einheit) {
		this.einheit = einheit;
	}
	
	
	

}
