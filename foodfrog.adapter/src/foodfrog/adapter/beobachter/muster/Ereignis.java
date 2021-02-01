package foodfrog.adapter.beobachter.muster;

public class Ereignis {
	private Object quelle;
	private String kommando;
	private Object daten;
	public Ereignis(Object quelle, String kommando, Object daten) {
		this.quelle = quelle;
		this.kommando = kommando;
		this.daten = daten;
	}
	public Object getQuelle() {
		return quelle;
	}
	public void setQuelle(Object quelle) {
		this.quelle = quelle;
	}
	public String getKommando() {
		return kommando;
	}
	public void setKommando(String kommando) {
		this.kommando = kommando;
	}
	public Object getDaten() {
		return daten;
	}
	public void setDaten(Object daten) {
		this.daten = daten;
	}
	
	
	
	
	
}
