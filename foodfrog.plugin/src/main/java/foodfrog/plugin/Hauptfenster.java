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
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;
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
import foodfrog.adapter.regler.WochenplanRegler;
import foodfrog.applikation.Gerichtverwaltung;
import foodfrog.kern.Entitaet;
import foodfrog.kern.Gericht;
import foodfrog.kern.Zutat;

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
	private GerichtRegler gerichtRegler;
	private WochenplanRegler wochenplanRegler;
	private JDialog	alleGerichteUebersicht;
	private JList<Gericht> gerichtListe;

	public Hauptfenster(GerichtRegler gerichtRegler, WochenplanRegler wochenplanRegler) {
		this.gerichtRegler = gerichtRegler;
		this.gerichtRegler.meldeAn(this);
		this.wochenplanRegler = wochenplanRegler;
		hauptPanel = new JPanel();
		hauptPanel.setLayout(new BorderLayout());

		// Foodfrog Logo
		foodfrogLogo = new JLabel();
		foodfrogLogo.setSize((new Dimension(200, 200)));
		foodfrogLogo.setHorizontalAlignment(SwingConstants.CENTER);

		// Logo im Label verankern

		try {
			Image imgLogo = ImageIO.read(getClass().getClassLoader().getResource("foodfrogLogoName.png"));
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

		// Button Panel f�r die Buttons Wochenplan, Einklaufsliste, Rezepterstellung

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 4));

		// Button f�r Wochenplan

		btnWochenplan = new JButton(" zum Wochenplan");
		btnWochenplan.setFont(new Font("Arial", Font.PLAIN, 20));

		try {
			Image imgWochenplan = ImageIO.read(getClass().getClassLoader().getResource("wochenplan.png"));
			imgWochenplan = imgWochenplan.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
			btnWochenplan.setIcon(new ImageIcon(imgWochenplan));
		} catch (Exception ex) {
			System.out.println(ex);
		}

		btnWochenplan.addActionListener(new ActionListener() {
			
			

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String[] options = new String[] {};
				WochenplanKomponente wochenplanKomponente = new WochenplanKomponente(Hauptfenster.this, Hauptfenster.this.wochenplanRegler);
				JDialog	dialogWochenplan = new JDialog();
				dialogWochenplan.setTitle("Gerichformular");
				dialogWochenplan.add(wochenplanKomponente);
				dialogWochenplan.setSize(1700,1080);
				// dialogFormular.pack();
				dialogWochenplan.setVisible(true);
			}
		});

		// Button f�r Rezepterstellung

		btnRezepterstellung = new JButton(" zur Rezepterstellung");
		btnRezepterstellung.setFont(new Font("Arial", Font.PLAIN, 20));

		try {
			Image imgRezepterstellung = ImageIO.read(getClass().getClassLoader().getResource("rezeptbuch.png"));
			imgRezepterstellung = imgRezepterstellung.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
			btnRezepterstellung.setIcon(new ImageIcon(imgRezepterstellung));
		} catch (Exception ex) {
			System.out.println(ex);
		}

		btnRezepterstellung.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				String[] options = new String[] {};
				GerichtFormular gerichtFormular = new GerichtFormular(Hauptfenster.this, Hauptfenster.this.gerichtRegler);
				JDialog	dialogFormular = new JDialog();
				dialogFormular.setTitle("Wochenplan");
				dialogFormular.add(gerichtFormular);
				dialogFormular.setSize(1700,1080);
				// dialogFormular.pack();
				dialogFormular.setVisible(true);
				
			}
		});
		
		// Button f�r die �bersicht aller Gerichte

		btnAlleGerichte = new JButton(" zur Übersicht der Gerichte");
		btnAlleGerichte.setFont(new Font("Arial", Font.PLAIN, 20));
		
		try {
			Image imgAlleGerichte = ImageIO.read(getClass().getClassLoader().getResource("menu.png"));
			imgAlleGerichte = imgAlleGerichte.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
			btnAlleGerichte.setIcon(new ImageIcon(imgAlleGerichte));
		} catch (Exception ex) {
			System.out.println(ex);
		} 
		
		btnAlleGerichte.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				ArrayList<Gericht> alleGerichte = (ArrayList<Gericht>) ((GerichtRegler)Hauptfenster.this.gerichtRegler).holeAlle();
				alleGerichteUebersicht = new JDialog();
				alleGerichteUebersicht.setTitle("Übersicht der Gerichte");
				gerichtListe = new JList<Gericht>();
				gerichtListe.setListData(alleGerichte.toArray(new Gericht[alleGerichte.size()]));
				alleGerichteUebersicht.add(gerichtListe);
				alleGerichteUebersicht.setSize(1700,1080);
				// dialogFormular.pack();
				alleGerichteUebersicht.setVisible(true);

			}
		});
		// ButtonPanel die Buttons hinzuf�gen

		buttonPanel.add(btnWochenplan);
		buttonPanel.add(btnRezepterstellung);
		buttonPanel.add(btnAlleGerichte);

		// Panel Mitte bef�llen

		pnlMitte.add(label);
		pnlMitte.add(buttonPanel);

		// Fu�zeile hinzuf�gen

		copyright = new JLabel();
		copyright.setText("\u00a9 2021 Viktoria Kunzmann & Matthias Klee");
		copyright.setHorizontalAlignment(SwingConstants.CENTER);
		copyright.setFont(new Font("Arial", Font.PLAIN, 15));

		// Hauptpanel alle Komponenten hinzuf�gen
		hauptPanel.add(foodfrogLogo, BorderLayout.NORTH);
		hauptPanel.add(pnlMitte, BorderLayout.CENTER);
		hauptPanel.add(copyright, BorderLayout.SOUTH);

		// Klasse Hauptfenster alles hinzuf�gen

		this.add(hauptPanel);
		this.setTitle("foodfrog");
		this.setSize(1700, 1080);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
	}

	public void aktualisiere(Object o) {
		ArrayList<Gericht> alleGerichte = (ArrayList<Gericht>) ((GerichtRegler)Hauptfenster.this.gerichtRegler).holeAlle();
		gerichtListe.setListData(alleGerichte.toArray(new Gericht[alleGerichte.size()]));
		if(gerichtListe != null) {
			alleGerichteUebersicht.remove(gerichtListe);
		}
		alleGerichteUebersicht.add(gerichtListe);
		alleGerichteUebersicht.setSize(1700,1080);
		alleGerichteUebersicht.repaint();
		alleGerichteUebersicht.revalidate();
		
	}

}
