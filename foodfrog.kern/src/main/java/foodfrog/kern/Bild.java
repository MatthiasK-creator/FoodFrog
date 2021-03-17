package foodfrog.kern;


public class Bild {
	private int id;
	private String titel;
	private byte[] grafik;
	
	
	public Bild(int id, String titel, byte[] grafik) {
		super();
		this.id = id;
		this.titel = titel;
		this.grafik = grafik;
	}
	public String getTitel() {
		return titel;
	}
	public void setTitel(String titel) {
		this.titel = titel;
	}
	public byte[] getGrafik() {
		return grafik;
	}
	public void setGrafik(byte[] grafik) {
		this.grafik = grafik;
	}
	
	public int getId() {
		return id;
	}
	
	

}
