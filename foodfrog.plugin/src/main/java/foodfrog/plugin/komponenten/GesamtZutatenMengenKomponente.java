package foodfrog.plugin.komponenten;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GesamtZutatenMengenKomponente extends JPanel {
	
	private int anzahlZutatenMengenKomponenten;
	private List<ZutatenMengenKomponente> komponentenListe;
	private static final int VGAP = 5; 

	public GesamtZutatenMengenKomponente() {
		this(5);
	}
	
	public GesamtZutatenMengenKomponente(int anzahl) {
		this.anzahlZutatenMengenKomponenten = anzahl;
		this.komponentenListe = new ArrayList<ZutatenMengenKomponente>();
		this.setLayout(new GridLayout(this.anzahlZutatenMengenKomponenten, 3, 0, VGAP));

		for (int i = 0; i < anzahlZutatenMengenKomponenten; i++) {
			ZutatenMengenKomponente mengenKomponente = new ZutatenMengenKomponente();
			this.add(mengenKomponente);
			this.komponentenListe.add(mengenKomponente); 
		}
	}
	
	public void fuegeMengenKomponenteHinzu(){
		this.setLayout(new GridLayout(++this.anzahlZutatenMengenKomponenten, 3, 0, VGAP));
		ZutatenMengenKomponente mengenKomponente = new ZutatenMengenKomponente();
		this.add(mengenKomponente);
		this.komponentenListe.add(mengenKomponente); 
		this.revalidate();
		this.repaint();
	}


}
