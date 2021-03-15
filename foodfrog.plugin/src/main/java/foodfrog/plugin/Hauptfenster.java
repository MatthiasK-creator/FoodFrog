package foodfrog.plugin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import foodfrog.adapter.beobachter.muster.Beobachter;
import foodfrog.adapter.beobachter.muster.Subjekt;
import foodfrog.adapter.regler.GerichtRegler;
import foodfrog.applikation.Gerichtverwaltung;
import foodfrog.kern.Gericht;

public class Hauptfenster extends JFrame implements Beobachter{

	private JButton btnWochenplan;
	private JButton btnEinkaufliste;
	private JButton btnRezepterstellung;
	private JButton btnAlleGerichte;
	private JLabel label;
	private JLabel copyright;
	private JLabel foodfrogLogo;
	private JPanel hauptPanel;
	private JPanel buttonPanel;
	private GerichtFormular gerichtformular;
	private Subjekt subjekt;

	public Hauptfenster(Subjekt subjekt) {
		this.subjekt = subjekt;
		this.subjekt.meldeAn(this);
		hauptPanel = new JPanel();
		hauptPanel.setLayout(new BorderLayout());

		// Foodfrog Logo
		foodfrogLogo = new JLabel();
		foodfrogLogo.setSize((new Dimension(200, 200)));
		foodfrogLogo.setHorizontalAlignment(SwingConstants.CENTER);

		// Logo im Label verankern

		try {
			Image imgLogo = ImageIO.read(getClass().getClassLoader().getResource("foodfrogLogoName.png"));
			System.out.println(imgLogo);
			imgLogo = imgLogo.getScaledInstance(220, 250, Image.SCALE_SMOOTH);
			foodfrogLogo.setIcon(new ImageIcon(imgLogo));
		} catch (Exception ex) {
			System.out.println(ex);
		}

		JPanel pnlMitte = new JPanel();
		pnlMitte.setLayout(new GridLayout(2, 1));

		// Label unter Logo

		label = new JLabel();
		label.setText("... der elegante Weg das Essen der Woche zu planen");
		label.setFont(new Font("Arial", Font.PLAIN, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);

		// Button Panel für die Buttons Wochenplan, Einklaufsliste, Rezepterstellung

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 4));

		// Button für Wochenplan

		btnWochenplan = new JButton("zum Wochenplan");
		btnWochenplan.setFont(new Font("Arial", Font.PLAIN, 20));

		try {
			Image imgWochenplan = ImageIO.read(getClass().getClassLoader().getResource("wochenplan.png"));
			System.out.println(imgWochenplan);
			imgWochenplan = imgWochenplan.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
			btnWochenplan.setIcon(new ImageIcon(imgWochenplan));
		} catch (Exception ex) {
			System.out.println(ex);
		}

		btnWochenplan.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});

		// Button für Einkaufsliste

		btnEinkaufliste = new JButton("zur Einkaufsliste");
		btnEinkaufliste.setFont(new Font("Arial", Font.PLAIN, 20));

		try {
			Image imgEinkaufsliste = ImageIO.read(getClass().getClassLoader().getResource("einkaufen.png"));
			System.out.println(imgEinkaufsliste);
			imgEinkaufsliste = imgEinkaufsliste.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
			btnEinkaufliste.setIcon(new ImageIcon(imgEinkaufsliste));
		} catch (Exception ex) {
			System.out.println(ex);
		}

		btnEinkaufliste.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// Button für Rezepterstellung

		btnRezepterstellung = new JButton("zur Rezepterstellung");
		btnRezepterstellung.setFont(new Font("Arial", Font.PLAIN, 20));

		try {
			Image imgRezepterstellung = ImageIO.read(getClass().getClassLoader().getResource("rezeptbuch.png"));
			System.out.println(imgRezepterstellung);
			imgRezepterstellung = imgRezepterstellung.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
			btnRezepterstellung.setIcon(new ImageIcon(imgRezepterstellung));
		} catch (Exception ex) {
			System.out.println(ex);
		}

		btnRezepterstellung.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				String[] options = new String[] {};
				GerichtFormular gerichtFormular = new GerichtFormular(Hauptfenster.this, Hauptfenster.this.subjekt);
				JDialog	dialogFormular = new JDialog();
				dialogFormular.setTitle("Gerichformular");
				dialogFormular.add(gerichtFormular);
				dialogFormular.setSize(1700,1080);
				// dialogFormular.pack();
				dialogFormular.setVisible(true);
				
			}
		});

		btnAlleGerichte = new JButton("Übersicht der Gerichte");
		btnAlleGerichte.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				ArrayList<Gericht> alleGerichte = (ArrayList<Gericht>) ((GerichtRegler)Hauptfenster.this.subjekt).holeAlleGerichte();
				JDialog	dialogFormular = new JDialog();
				dialogFormular.setTitle("Übersicht der Gerichte");
				JList<Gericht> gerichtListe = new JList<Gericht>();
				gerichtListe.setListData(alleGerichte.toArray(new Gericht[alleGerichte.size()]));
				dialogFormular.add(gerichtListe);
				dialogFormular.setSize(1700,1080);
				// dialogFormular.pack();
				dialogFormular.setVisible(true);

			}
		});
		// ButtonPanel die Buttons hinzufügen

		buttonPanel.add(btnWochenplan);
		buttonPanel.add(btnEinkaufliste);
		buttonPanel.add(btnRezepterstellung);
		buttonPanel.add(btnAlleGerichte);

		// Panel Mitte befüllen

		pnlMitte.add(label);
		pnlMitte.add(buttonPanel);

		// Fußzeile hinzufügen

		copyright = new JLabel();
		copyright.setText("\u00a9 2021 Viktoria Kunzmann & Matthias Klee");
		copyright.setHorizontalAlignment(SwingConstants.CENTER);
		copyright.setFont(new Font("Arial", Font.PLAIN, 15));

		// Hauptpanel alle Komponenten hinzufügen
		hauptPanel.add(foodfrogLogo, BorderLayout.NORTH);
		hauptPanel.add(pnlMitte, BorderLayout.CENTER);
		hauptPanel.add(copyright, BorderLayout.SOUTH);

		// Klasse Hauptfenster alles hinzufügen

		this.add(hauptPanel);
		this.setTitle("foodfrog");
		this.setSize(1700, 1080);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
	}

	public void aktualisiere(Object o) {
		System.out.println("Gerichte haben sich geändert jetzt aktualisieren!!!");
		
	}

}
