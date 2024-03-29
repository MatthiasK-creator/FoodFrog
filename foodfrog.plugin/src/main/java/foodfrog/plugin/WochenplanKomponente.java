package foodfrog.plugin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import foodfrog.adapter.beobachter.muster.Subjekt;
import foodfrog.adapter.regler.GerichtRegler;
import foodfrog.adapter.regler.WochenplanRegler;
import foodfrog.adapter.renderer.GerichtRenderer;
import foodfrog.kern.Gericht;
import foodfrog.kern.Kategorie;
import foodfrog.kern.Wochentag;
import foodfrog.plugin.komponenten.GerichtKomponente;

public class WochenplanKomponente extends JPanel {

	private JLabel lblwochenplan;
	private JLabel foodfrogLogo;

	private JPanel pnlWPNavigation;
	private JPanel pnlGerichtKomponente;

	private JButton btnNeueRezepte;
	private JButton btnStartseite;
	private JButton btnEinkaufsliste;
	private Hauptfenster hauptfenster;
	
	
	private JList<Wochentag> wochentagListe;
	private JList<Kategorie> kategorieListe;
	
	private ArrayList<GerichtKomponente> gerichtKomponenten;
	private WochenplanRegler wochenplanRegler;
	private List<Gericht> aktuelleListeVonGerichten;
	private GerichtRenderer gerichtRenderer;

	private JScrollPane scrollGerichte;

	public WochenplanKomponente(Hauptfenster hauptfenster, WochenplanRegler subjekt) {
		this.wochenplanRegler = subjekt;
		this.hauptfenster = hauptfenster;
		gerichtRenderer = GerichtRenderer.holeInstanz();

		pnlWPNavigation = new JPanel();
		pnlWPNavigation.setLayout(new GridLayout(1, 7));

		// Button neue Rezepte

		btnNeueRezepte = new JButton("  neue Rezepte");
		btnNeueRezepte.setFont(new Font("Arial", Font.PLAIN, 20));
		try {
			Image imgNeueRezepte = ImageIO.read(getClass().getClassLoader().getResource("refresh.png"));
			imgNeueRezepte = imgNeueRezepte.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			btnNeueRezepte.setIcon(new ImageIcon(imgNeueRezepte));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		btnNeueRezepte.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(WochenplanKomponente.this.wochentagListe.getSelectedValuesList().size() < 0) {
					JOptionPane.showConfirmDialog(WochenplanKomponente.this, "Bitte w�hle mindestens ein Wochentag aus.");
				}
				WochenplanKomponente.this.aktuelleListeVonGerichten = WochenplanKomponente.this.wochenplanRegler.generiereWochenplan(WochenplanKomponente.this.wochentagListe.getSelectedValuesList().size(), 
																			   WochenplanKomponente.this.kategorieListe.getSelectedValuesList());
				pnlGerichtKomponente = new JPanel(new GridLayout(WochenplanKomponente.this.wochentagListe.getSelectedValuesList().size(),1));

				for (int i = 0; i < aktuelleListeVonGerichten.size(); i++) {
					GerichtKomponente gerichtKomp = new GerichtKomponente(Wochentag.values()[i].wochentag , aktuelleListeVonGerichten.get(i));
					pnlGerichtKomponente.add(gerichtKomp);
				}
				
				
				if(scrollGerichte != null) {
					WochenplanKomponente.this.remove(scrollGerichte);
					WochenplanKomponente.this.repaint();
					WochenplanKomponente.this.revalidate();
				}
				scrollGerichte = new JScrollPane(pnlGerichtKomponente);
				WochenplanKomponente.this.add(scrollGerichte, BorderLayout.CENTER);
				WochenplanKomponente.this.repaint();
				WochenplanKomponente.this.revalidate();
			}
		});

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
				WochenplanKomponente.this.hauptfenster.requestFocus();
			}
		});

		// Button Einkaufsliste
		btnEinkaufsliste = new JButton("  Einkaufsliste");
		btnEinkaufsliste.setFont(new Font("Arial", Font.PLAIN, 20));
		try {
			Image imgEinkaufsliste = ImageIO.read(getClass().getClassLoader().getResource("einkaufen.png"));
			imgEinkaufsliste = imgEinkaufsliste.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			btnEinkaufsliste.setIcon(new ImageIcon(imgEinkaufsliste));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		btnEinkaufsliste.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(aktuelleListeVonGerichten != null) {
					JDialog	dialogFormular = new JDialog();
					dialogFormular.setTitle("Einkaufsliste");
					JTextArea beschreibungsFeld = new JTextArea();
					beschreibungsFeld.setText(gerichtRenderer.renderAlleZutaten(WochenplanKomponente.this.aktuelleListeVonGerichten));
					beschreibungsFeld.setEditable(false);
					dialogFormular.add(beschreibungsFeld);
					dialogFormular.setSize(1000,1000);
					dialogFormular.pack();
					dialogFormular.setVisible(true);
				}
				
				
			}
		});

		lblwochenplan = new JLabel("Wochenplan");
		lblwochenplan.setFont(new Font("Arial", Font.PLAIN, 30));
		lblwochenplan.setHorizontalAlignment(SwingConstants.CENTER);

		
		// Label Logo Foodfrog f�r Rezeperstellung
		foodfrogLogo = new JLabel();
		
		try {
			Image imgFrog = ImageIO.read(getClass().getClassLoader().getResource("foodfrogLogo.png"));
			imgFrog = imgFrog.getScaledInstance(80, 100, Image.SCALE_SMOOTH);
			foodfrogLogo.setIcon(new ImageIcon(imgFrog));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		// Combobox mit Auswahl von Wochentagen
		
		wochentagListe = new JList(Wochentag.values());
		kategorieListe = new JList(wochenplanRegler.holeAlleKategorien().toArray(new Kategorie[wochenplanRegler.holeAlleKategorien().size()]));
		wochentagListe.setVisibleRowCount(wochenplanRegler.holeAlleKategorien().size());
		// Navigationsleiste alles hinzuf�gen
		

		pnlWPNavigation.add(btnNeueRezepte);
		pnlWPNavigation.add(btnStartseite);
		pnlWPNavigation.add(btnEinkaufsliste);
		pnlWPNavigation.add(lblwochenplan);
		pnlWPNavigation.add(foodfrogLogo);
		pnlWPNavigation.add(wochentagListe);
	
		
		JScrollPane scrollKategorie = new JScrollPane(kategorieListe);
		pnlWPNavigation.add(scrollKategorie);
		
		// Panel Gerichtkomponente
		

		this.setLayout(new BorderLayout());
		this.add(pnlWPNavigation, BorderLayout.NORTH);
		this.setVisible(true);

	}


}
