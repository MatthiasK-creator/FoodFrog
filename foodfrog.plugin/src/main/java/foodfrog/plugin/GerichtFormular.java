package foodfrog.plugin;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GerichtFormular extends JPanel {

	private JPanel navigationsleiste;
	private JButton startseite;
	private JButton speichern;
	private JLabel rezept;
	private Hauptfenster hauptfenster;
	private JPanel erstellung;
	private JPanel pnlRezeptname;

	private JLabel rezeptname;
	private JTextField tfRezeptname;
	
	private JLabel kategorie;
	private JLabel zutatenMengen;
	private JLabel zubereitung;
	private JLabel portionen;
	private JLabel zubereitungszeit;
	
	
	public GerichtFormular() {

		// Navigationsleiste konfigurieren

		navigationsleiste = new JPanel();
		navigationsleiste.setLayout(new GridLayout(1,3));
		startseite = new JButton("Startseite");
		speichern = new JButton("Speichern");

		rezept = new JLabel("Rezepterstellung");
		rezept.setFont(rezept.getFont().deriveFont(20f));

		navigationsleiste.add(startseite);
		navigationsleiste.add(speichern);
		navigationsleiste.add(rezept);
		
		// Rezepterstellungs Komponenente
		
		erstellung = new JPanel(new GridLayout(5,1));
		
		// Panel Rezeptname
		
		pnlRezeptname = new JPanel();
		pnlRezeptname.setLayout(new GridLayout(1,2));
		rezeptname = new JLabel("Rezeptname");

		tfRezeptname = new JTextField();
		
		pnlRezeptname.add(rezeptname);
		pnlRezeptname.add(tfRezeptname);
	
		
		erstellung.add(pnlRezeptname);
		
		
		
		this.setLayout(new GridLayout(2,1));
		this.add(navigationsleiste);
		this.add(erstellung);
		this.setSize(800, 500);
		this.setVisible(true);
	}
	
	public JPanel getNavigationsleiste() {
		return navigationsleiste;
	}

}
