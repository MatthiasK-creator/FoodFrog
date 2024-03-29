package foodfrog.kern;

public class Zutat {
	private int id;
	private String bezeichnung;
	private int menge;
	private Einheit einheit;
	
	
	public Zutat() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Zutat(int id, String bezeichnung, int menge) {
		super();
		this.id = id;
		this.bezeichnung = bezeichnung;
		this.menge = menge;
	}
	public Zutat(int id, String bezeichnung, int menge, Einheit einheit) {
		this(id, bezeichnung, menge);
		this.einheit = einheit; 
	}
	public Zutat( String bezeichnung, int menge, Einheit einheit) {
		this.bezeichnung = bezeichnung;
		this.menge = menge;
		this.einheit = einheit; 
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	

}
