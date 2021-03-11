package foodfrog.plugin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GerichtFormular extends JPanel {

	private Hauptfenster hauptfenster;

	private JButton btnStartseite;
	private JButton btnSpeichern;
	private JButton btnPlus;

	private JPanel pnlNavigationsleiste;
	private JPanel pnlErstellung;
	private JPanel pnlRezeptname;
	private JPanel pnlPortion;
	private JPanel pnlKategorie;
	private JPanel pnlZutatenMengen;

	private JTextField tfRezeptname;
	private JTextField tfPortion;
	private JTextField tfMenge;
	private JTextField tfZutatenname;
	
	private JLabel rezept;
	private JLabel rezeptname;
	private JLabel kategorie;
	private JLabel zutatenMengen;
	private JLabel zubereitung;
	private JLabel portionen;
	private JLabel zubereitungszeit;
	private JLabel logoRezeptFrog;
	
	private JComboBox landbox;
	private JComboBox zubereitungbox;
	private JComboBox sonstigesbox;
	private JComboBox einheitenbox;
	

	public GerichtFormular() {

		// Navigationsleiste konfigurieren

		pnlNavigationsleiste = new JPanel();
		pnlNavigationsleiste.setLayout(new GridLayout(1, 4));

		// Button Startseite
		btnStartseite = new JButton("Startseite");
		btnStartseite.setFont(new Font("Arial", Font.PLAIN, 20));
		try {
			Image imgStartseite = ImageIO.read(getClass().getClassLoader().getResource("gingerbread.png"));
			System.out.println(imgStartseite);
			imgStartseite = imgStartseite.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			btnStartseite.setIcon(new ImageIcon(imgStartseite));
		} catch (Exception ex) {
			System.out.println(ex);
		}

		// Button Speichern
		btnSpeichern = new JButton("Speichern");
		btnSpeichern.setFont(new Font("Arial", Font.PLAIN, 20));
		try {
			Image imgSpeichern = ImageIO.read(getClass().getClassLoader().getResource("speichern.png"));
			System.out.println(imgSpeichern);
			imgSpeichern = imgSpeichern.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			btnSpeichern.setIcon(new ImageIcon(imgSpeichern));
		} catch (Exception ex) {
			System.out.println(ex);
		}

		// Label Rezepterstellung

		rezept = new JLabel("Rezepterstellung");
		rezept.setFont(new Font("Arial", Font.PLAIN, 30));
		rezept.setHorizontalAlignment(SwingConstants.CENTER);

		// Label Logo Foodfrog für Rezeperstellung

		logoRezeptFrog = new JLabel();

		try {
			Image imgRezeptFrog = ImageIO.read(getClass().getClassLoader().getResource("foodfrogLogo.png"));
			System.out.println(imgRezeptFrog);
			imgRezeptFrog = imgRezeptFrog.getScaledInstance(80, 100, Image.SCALE_SMOOTH);
			logoRezeptFrog.setIcon(new ImageIcon(imgRezeptFrog));
		} catch (Exception ex) {
			System.out.println(ex);
		}

		// Zum Panel Navigationsleiste hinzufügen

		pnlNavigationsleiste.add(btnStartseite);
		pnlNavigationsleiste.add(btnSpeichern);
		pnlNavigationsleiste.add(rezept);
		pnlNavigationsleiste.add(logoRezeptFrog);

		// Panel Rezeptname

		pnlRezeptname = new JPanel();
		pnlRezeptname.setLayout(new GridLayout(2, 1));
		rezeptname = new JLabel("Rezeptname");
		rezeptname.setFont(new Font("Arial", Font.BOLD, 20));
		tfRezeptname = new JTextField();

		pnlRezeptname.add(rezeptname);
		pnlRezeptname.add(tfRezeptname);

		// Panel Portionen

		pnlPortion = new JPanel();
		pnlPortion.setLayout(new GridLayout(1, 2));

		portionen = new JLabel("Portionen: ");
		portionen.setFont(new Font("Arial", Font.BOLD, 20));
		tfPortion = new JTextField();

		pnlPortion.add(portionen);
		pnlPortion.add(tfPortion);

		// Panel Kategorie
		
		pnlKategorie = new JPanel();
		pnlKategorie.setLayout(new GridLayout(2,1));
		
		kategorie = new JLabel("Kateogrie");
		kategorie.setFont(new Font("Arial", Font.BOLD, 20));
		
		JPanel pnlunterKategorie = new JPanel();
		pnlunterKategorie.setLayout(new GridLayout(2,3));
		
		JLabel land = new JLabel("Länderkategorie: ");
		land.setFont(new Font("Arial", Font.ITALIC, 15));
		JLabel zubereitung = new JLabel("Zubereitungsart: ");
		zubereitung.setFont(new Font("Arial", Font.ITALIC, 15));
		JLabel sonstiges = new JLabel("Sonstiges: ");
		sonstiges.setFont(new Font("Arial", Font.ITALIC, 15));
		
		landbox = new JComboBox();
		zubereitungbox = new JComboBox();
		sonstigesbox = new JComboBox();
		
		pnlunterKategorie.add(land);
		pnlunterKategorie.add(zubereitung);
		pnlunterKategorie.add(sonstiges);
		pnlunterKategorie.add(landbox);
		pnlunterKategorie.add(zubereitungbox);
		pnlunterKategorie.add(sonstigesbox);
		
		
		pnlKategorie.add(kategorie);
		pnlKategorie.add(pnlunterKategorie);
		
		// Komponente Zutaten und Mengenangaben
		
		pnlZutatenMengen = new JPanel();
		pnlZutatenMengen.setLayout(new GridLayout(3,1));
		
		
		zutatenMengen = new JLabel("Zutaten und Mengenangaben");
		zutatenMengen.setFont(new Font("Arial", Font.BOLD, 20));
		
		JPanel pnlZutatenMengenErstellung = new JPanel();
		pnlZutatenMengenErstellung.setLayout(new GridLayout(1,3));
		
		JLabel menge = new JLabel("Menge ");
		menge.setFont(new Font("Arial", Font.ITALIC, 15));
		JLabel einheit = new JLabel("Einheit ");
		einheit.setFont(new Font("Arial", Font.ITALIC, 15));
		JLabel zutatenname = new JLabel("Zutatenname ");
		zutatenname.setFont(new Font("Arial", Font.ITALIC, 15));
		
		pnlZutatenMengenErstellung.add(menge);
		pnlZutatenMengenErstellung.add(einheit);
		pnlZutatenMengenErstellung.add(zutatenname);
	
		
		pnlZutatenMengen.add(zutatenMengen);		
		pnlZutatenMengen.add(pnlZutatenMengenErstellung);		
		

		// Rezepterstellungs Komponenente

		pnlErstellung = new JPanel();

		// Hauptpanel hinzufügen

		pnlErstellung.add(pnlRezeptname);
		pnlErstellung.add(pnlPortion);
		pnlErstellung.add(pnlKategorie);
		pnlErstellung.add(pnlZutatenMengen);

		this.setLayout(new GridLayout(2, 1));
		this.add(pnlNavigationsleiste);
		this.add(pnlErstellung);
		this.setSize(800, 500);
		this.setVisible(true);
	}

	public JPanel getNavigationsleiste() {
		return pnlNavigationsleiste;
	}

}
