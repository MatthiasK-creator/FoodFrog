package foodfrog.plugin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.commons.io.IOUtils;

import foodfrog.adapter.regler.GerichtRegler;
import foodfrog.kern.Bild;
import foodfrog.kern.Gericht;
import foodfrog.kern.Kategorie;
import foodfrog.plugin.komponenten.GesamtZutatenMengenKomponente;

public class GerichtFormular extends JPanel {

	private JButton btnStartseite;
	private JButton btnSpeichern;
	private JButton btnPlus;
	private JButton btnBildHochladen;

	private JPanel pnlNavigationsleiste;
	private JPanel pnlErstellung;
	private JPanel pnlRezeptname;
	private JPanel pnlPortion;
	private JPanel pnlKategorie;
	private JPanel pnlZutatenMengen;
	private JPanel zutatenKomponente;

	private JTextField tfRezeptname;
	private JTextField tfPortion;
	private JTextField tfMenge;
	private JTextField tfZutatenname;
	private JTextField tfZubZeit;

	private JTextArea taBeschreibung;

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
	private JList<Kategorie> kategorieListe;
	private JList<Bild> bilderListe;
	private JComboBox einheitenbox;
	private JFrame hauptfenster;

	private GerichtRegler gerichtRegler;
	private GesamtZutatenMengenKomponente gesamtZutatenMengenKomponente;
	private List<Bild> bilderAuswahl;
	public GerichtFormular(Hauptfenster hauptfenster, GerichtRegler gerichtRegler) {
		this.gerichtRegler = gerichtRegler;
		this.hauptfenster = hauptfenster;

		// Navigationsleiste konfigurieren

		pnlNavigationsleiste = new JPanel();
		pnlNavigationsleiste.setLayout(new GridLayout(1, 6));

		// Button Startseite
		btnStartseite = new JButton("  Startseite");
		btnStartseite.setFont(new Font("Arial", Font.PLAIN, 20));
		try {
			Image imgStartseite = ImageIO.read(getClass().getClassLoader().getResource("gingerbread.png"));
			imgStartseite = imgStartseite.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			btnStartseite.setIcon(new ImageIcon(imgStartseite));
		} catch (Exception ex) {
			System.out.println(ex);
		}

		btnStartseite.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				GerichtFormular.this.hauptfenster.requestFocus();
			}
		});

		// Button Speichern
		btnSpeichern = new JButton("  Speichern");
		btnSpeichern.setFont(new Font("Arial", Font.PLAIN, 20));
		try {
			Image imgSpeichern = ImageIO.read(getClass().getClassLoader().getResource("speichern.png"));
			imgSpeichern = imgSpeichern.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			btnSpeichern.setIcon(new ImageIcon(imgSpeichern));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		btnSpeichern.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (GerichtFormular.this.tfRezeptname.getText().isEmpty()
						|| GerichtFormular.this.taBeschreibung.getText().isEmpty()
						|| GerichtFormular.this.tfZubZeit.getText().isEmpty() || GerichtFormular.this.kategorieListe.getSelectedValuesList().size() <= 0
						|| GerichtFormular.this.gesamtZutatenMengenKomponente.holeErstellteZutaten().size() <= 0
						|| GerichtFormular.this.bilderAuswahl.size() <= 0) {
					JOptionPane.showMessageDialog(GerichtFormular.this, "Bitte alle Felder ausf�llen.");
					return;
				}

				Gericht gericht = new Gericht(GerichtFormular.this.tfRezeptname.getText(),
						GerichtFormular.this.taBeschreibung.getText(),
						Integer.parseInt(GerichtFormular.this.tfZubZeit.getText()));
				// Kategorien
				gericht.setKategorien( GerichtFormular.this.kategorieListe.getSelectedValuesList());
				
				// Zutaten
				gericht.setZutaten(GerichtFormular.this.gesamtZutatenMengenKomponente.holeErstellteZutaten());
				
				// Bilder
				gericht.setBilder(GerichtFormular.this.bilderAuswahl);
				
				Gericht erstelltesGericht = GerichtFormular.this.gerichtRegler.erstelleGericht(gericht);
				if(erstelltesGericht != null) {
					JOptionPane.showMessageDialog(GerichtFormular.this, erstelltesGericht.getName() + " wurde erfolgreich erstellt.");
					GerichtFormular.this.gerichtRegler.benachrichtige();
				}

			}
		});

		// Label Rezepterstellung

		rezept = new JLabel("Rezepterstellung");
		rezept.setFont(new Font("Arial", Font.PLAIN, 30));
		rezept.setHorizontalAlignment(SwingConstants.CENTER);

		// Label Logo Foodfrog f�r Rezeperstellung

		logoRezeptFrog = new JLabel();

		try {
			Image imgRezeptFrog = ImageIO.read(getClass().getClassLoader().getResource("foodfrogLogo.png"));
			imgRezeptFrog = imgRezeptFrog.getScaledInstance(80, 100, Image.SCALE_SMOOTH);
			logoRezeptFrog.setIcon(new ImageIcon(imgRezeptFrog));
		} catch (Exception ex) {
			System.out.println(ex);
		}

		btnBildHochladen = new JButton("  Hochladen von Bildern");
		btnBildHochladen.setFont(new Font("Arial", Font.PLAIN, 20));

		try {
			Image imgUpload = ImageIO.read(getClass().getClassLoader().getResource("upload.png"));
			imgUpload = imgUpload.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			btnBildHochladen.setIcon(new ImageIcon(imgUpload));
		} catch (Exception ex) {
			System.out.println(ex);
		}

		btnBildHochladen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser dateiExplorer = new JFileChooser();
				dateiExplorer.setMultiSelectionEnabled(true);
				int speichern = dateiExplorer.showSaveDialog(GerichtFormular.this);
				
				if(speichern == JFileChooser.APPROVE_OPTION) {
					GerichtFormular.this.bilderAuswahl = new ArrayList<>();
					for (File file : dateiExplorer.getSelectedFiles()) {
						try {
							Bild bild = new Bild(file.getName(), IOUtils.toByteArray(file.toURI()));
							GerichtFormular.this.bilderAuswahl.add(bild);
							GerichtFormular.this.bilderListe.setListData(GerichtFormular.this.bilderAuswahl.toArray(new Bild[GerichtFormular.this.bilderAuswahl.size()]));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
				
			}
		});
		// Zum Panel Navigationsleiste hinzuf�gen

		pnlNavigationsleiste.add(btnStartseite);
		pnlNavigationsleiste.add(btnSpeichern);
		pnlNavigationsleiste.add(rezept);
		pnlNavigationsleiste.add(logoRezeptFrog);
		bilderListe = new JList<Bild>();
		pnlNavigationsleiste.add(bilderListe);
		pnlNavigationsleiste.add(btnBildHochladen);

		// Panel Rezeptname

		pnlRezeptname = new JPanel();
		pnlRezeptname.setLayout(new GridLayout(2, 1));
		pnlRezeptname.setSize(new Dimension(100, 100));

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
		pnlKategorie.setLayout(new GridLayout(2, 1));

		kategorie = new JLabel("Kateogrie");
		kategorie.setFont(new Font("Arial", Font.BOLD, 20));

		JPanel pnlunterKategorie = new JPanel();
		pnlunterKategorie.setLayout(new GridLayout(2, 1));

//		JLabel land = new JLabel("L�nderkategorie: ");
//		land.setFont(new Font("Arial", Font.ITALIC, 15));
//		JLabel zubereitung = new JLabel("Zubereitungsart: ");
//		zubereitung.setFont(new Font("Arial", Font.ITALIC, 15));
		JLabel lblKategorie = new JLabel("Auswahl an Kategorien: ");
		lblKategorie.setFont(new Font("Arial", Font.ITALIC, 15));

//		landbox = new JComboBox();
//		zubereitungbox = new JComboBox();
		kategorieListe = new JList<Kategorie>(this.gerichtRegler.holeAlleKategorien().toArray(new Kategorie[this.gerichtRegler.holeAlleKategorien().size()]));
		JScrollPane kategorieScroll = new JScrollPane(kategorieListe);

//		pnlunterKategorie.add(land);
//		pnlunterKategorie.add(zubereitung);
//		pnlunterKategorie.add(sonstiges);
//		pnlunterKategorie.add(landbox);
//		pnlunterKategorie.add(zubereitungbox);
		pnlunterKategorie.add(lblKategorie);
		pnlunterKategorie.add(kategorieScroll);

		pnlKategorie.add(kategorie);
		pnlKategorie.add(pnlunterKategorie);

		// Komponente Zutaten und Mengenangaben

		pnlZutatenMengen = new JPanel();
		pnlZutatenMengen.setLayout(new GridLayout(4, 1));

		zutatenMengen = new JLabel("Zutaten und Mengenangaben");
		zutatenMengen.setFont(new Font("Arial", Font.BOLD, 20));

		btnPlus = new JButton();
		btnPlus.setFont(new Font("Arial", Font.PLAIN, 20));
		try {
			Image imgPlus = ImageIO.read(getClass().getClassLoader().getResource("plus.png"));
			imgPlus = imgPlus.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			btnPlus.setIcon(new ImageIcon(imgPlus));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		btnPlus.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				GerichtFormular.this.gesamtZutatenMengenKomponente.fuegeMengenKomponenteHinzu();
			}
		});
		JPanel pnlZutatenMengenErstellung = new JPanel();
		pnlZutatenMengenErstellung.setLayout(new GridLayout(1, 3));

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
		pnlZutatenMengen.add(btnPlus);
		pnlZutatenMengen.add(pnlZutatenMengenErstellung);

		// ZutatenMengenKomponenten Klasse hinzuf�gen

		gesamtZutatenMengenKomponente = new GesamtZutatenMengenKomponente();

		this.zutatenKomponente = new JPanel(new BorderLayout());
		this.zutatenKomponente.add(gesamtZutatenMengenKomponente, BorderLayout.CENTER);

		// Zubereitungsbeschreibung

		JPanel pnlBeschreibung = new JPanel(new GridLayout(4, 1, 0, 0));

		JLabel lblZubereitung = new JLabel("Zubereitung");
		this.taBeschreibung = new JTextArea();

		JLabel lblZubZeit = new JLabel("Zubereitungszeit (min):");
		tfZubZeit = new JTextField();

		JScrollPane scrollBeschreibung = new JScrollPane(this.taBeschreibung);
		pnlBeschreibung.add(lblZubereitung);
		pnlBeschreibung.add(scrollBeschreibung);
		pnlBeschreibung.add(lblZubZeit);
		pnlBeschreibung.add(tfZubZeit);

		// Rezepterstellungs Komponenente
		pnlErstellung = new JPanel();
		pnlErstellung.setLayout(new GridLayout(6, 1, 0, 10));
		pnlErstellung.add(pnlRezeptname);
		pnlErstellung.add(pnlPortion);
		pnlErstellung.add(pnlKategorie);
		pnlErstellung.add(pnlZutatenMengen);
		pnlErstellung.add(zutatenKomponente);
		pnlErstellung.add(pnlBeschreibung);

		JScrollPane scrollErstellung = new JScrollPane(pnlErstellung);
		this.setLayout(new BorderLayout());
		this.add(pnlNavigationsleiste, BorderLayout.NORTH);
		this.add(scrollErstellung, BorderLayout.CENTER);
		this.setVisible(true);
	}


	public JPanel getNavigationsleiste() {
		return pnlNavigationsleiste;
	}

}
