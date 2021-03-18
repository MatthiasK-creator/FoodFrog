package foodfrog.plugin.komponenten;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import foodfrog.adapter.renderer.GerichtRenderer;

public class GerichtKomponente extends JPanel{
	
	private String wochentag;
	private String[] kategorie ;
	private String gerichtname;
	private int zubereitungsZeit;
	
	private JPanel pnlWest;
	private JPanel pnlCenter;
	private JPanel pnlOsten;
	
	
	private JLabel lblWochentag;
	private JLabel lblZubereitungszeit;
	private JLabel lblKategorie;
	private JLabel lblGerichtname;
	private GerichtRenderer gerichtRenderer;
	private JButton btnZumRezept;
	private Image img;
	
	public GerichtKomponente(String gerichtname, String wochentag, String[] kategorie, int zubereitungsZeit) {
		super();
		gerichtRenderer = GerichtRenderer.holeInstanz();
		
		this.wochentag = wochentag;
		this.kategorie = kategorie;
		this.zubereitungsZeit = zubereitungsZeit;
		
		// Panel Westen für Wochentag
		
		pnlWest = new JPanel();
		lblWochentag = new JLabel("Wochentag");
		pnlWest.add(lblWochentag);	
		
		// Panel Mitte für Gerichtname, Kategorie, Dauer und Buttons
		
		pnlCenter = new JPanel();
		
		lblGerichtname = new JLabel();
		lblGerichtname.setText("Gerichtname");
		
		lblKategorie = new JLabel("Kategorie");
		
		
		lblZubereitungszeit = new JLabel("Dauer:");
		
		btnZumRezept = new JButton("  zum Rezept");
		btnZumRezept.setFont(new Font("Arial", Font.PLAIN, 15));
		try {
			Image imgZumRezept = ImageIO.read(getClass().getClassLoader().getResource("rezeptbuch.png"));
			System.out.println(imgZumRezept);
			imgZumRezept = imgZumRezept.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			btnZumRezept.setIcon(new ImageIcon(imgZumRezept));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		pnlCenter.add(lblGerichtname);
		pnlCenter.add(lblKategorie);
		pnlCenter.add(lblZubereitungszeit);
		pnlCenter.add(btnZumRezept);
		
		
		
		// Panel Mitte für Bild des Gerichts
		
		this.setLayout(new BorderLayout());
		this.add(pnlWest, BorderLayout.WEST);
		this.add(pnlCenter, BorderLayout.CENTER);
	
		this.setVisible(true);
		
		
		
	}
	
	

}
