package foodfrog.kern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Bild {
	@Id
	private long id;
	@Column(name="Titel")
	private String titel;
	@Lob
	private byte[] grafik;
	
	public Bild() {
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
	
	

}
