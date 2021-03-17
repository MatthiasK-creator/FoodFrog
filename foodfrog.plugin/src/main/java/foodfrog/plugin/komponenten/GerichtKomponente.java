package foodfrog.plugin.komponenten;

import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GerichtKomponente extends JPanel{
	
	private String wochentag;
	private String[] kategorie ;
	private String gerichtname;
	private int zubereitungsZeit;
	
	private JButton btnZumRezept;
	
	private JLabel lblWochentag;
	private JLabel lblZubereitungszeit;
	private JLabel lblKategorie;
	private JLabel lblGerichtname;
	
	private Image img;
	
	public GerichtKomponente(String gerichtname, String wochentag, String[] kategorie, int zubereitungsZeit) {
		super();
		
		this.wochentag = wochentag;
		this.kategorie = kategorie;
		this.zubereitungsZeit = zubereitungsZeit;
		
		
		
		
		
		this.setVisible(true);
		
		
		
	}
	
	

}
