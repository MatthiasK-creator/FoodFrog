package foodfrog.plugin.komponenten;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import foodfrog.adapter.renderer.GerichtRenderer;
import foodfrog.kern.Gericht;

public class GerichtKomponente extends JPanel {

	private JPanel pnlWest;
	private JPanel pnlMitte;
	private JPanel pnlOsten;
	private JPanel pnlButton;

	private JLabel lblWochentag;
	private JLabel lblZubereitungszeit;
	private JLabel lblKategorie;
	private JLabel lblGerichtname;
	private JLabel lblGerichtbild;
	private GerichtRenderer gerichtRenderer;
	private JButton btnZumRezept;
	private Image img;

	private Gericht gericht;
	public GerichtKomponente(String wochentag, Gericht gericht) {
		this.gericht = gericht;
		// String gerichtname, String wochentag, String[] kategorie, int
		// zubereitungsZeit
		gerichtRenderer = GerichtRenderer.holeInstanz();

		// Panel Westen für Wochentag

		pnlWest = new JPanel();
		pnlWest.setPreferredSize(new Dimension(150,100));
		lblWochentag = new JLabel(wochentag);
		pnlWest.add(lblWochentag);

		// Panel Mitte für Gerichtname, Kategorie, Dauer und Buttons

		pnlMitte = new JPanel(new GridBagLayout());
		GridBagConstraints mitteConstraints = new GridBagConstraints();
		mitteConstraints.fill = GridBagConstraints.HORIZONTAL;
		mitteConstraints.anchor = GridBagConstraints.WEST;
		

		mitteConstraints.gridx = 0;
		mitteConstraints.gridy = 0;

		lblGerichtname = new JLabel();
		lblGerichtname.setText(this.gerichtRenderer.renderKopfzeile(gericht));

		pnlMitte.add(lblGerichtname, mitteConstraints);

		lblKategorie = new JLabel(this.gerichtRenderer.renderKategorien(gericht));

		mitteConstraints.gridx = 0;
		mitteConstraints.gridy = 1;
		mitteConstraints.insets = new Insets(10, 0, 0, 0);

		pnlMitte.add(lblKategorie, mitteConstraints);

		lblZubereitungszeit = new JLabel(this.gerichtRenderer.renderDauer(gericht));

		mitteConstraints.gridx = 0;
		mitteConstraints.gridy = 2;
		mitteConstraints.insets = new Insets(10, 0, 0, 0);

		pnlMitte.add(lblZubereitungszeit, mitteConstraints);

		pnlButton = new JPanel();

		btnZumRezept = new JButton("  zum Rezept");
		btnZumRezept.setSize(50, 50);
		btnZumRezept.setFont(new Font("Arial", Font.PLAIN, 15));
		try {
			Image imgZumRezept = ImageIO.read(getClass().getClassLoader().getResource("rezeptbuch.png"));
			System.out.println(imgZumRezept);
			imgZumRezept = imgZumRezept.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			btnZumRezept.setIcon(new ImageIcon(imgZumRezept));
		} catch (Exception ex) {
			System.out.println(ex);
		}

		pnlButton.add(btnZumRezept);

		mitteConstraints.gridx = 0;
		mitteConstraints.gridy = 3;
		pnlMitte.add(pnlButton, mitteConstraints);

		// Panel Mitte für Bild des Gerichts

		pnlOsten = new JPanel();

		ImageIcon icon = new ImageIcon(gericht.getBilder().get(0).getGrafik());
		Image img = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		lblGerichtbild = new JLabel();
		lblGerichtbild.setIcon(new ImageIcon(img));
		pnlOsten.add(lblGerichtbild);

		this.setLayout(new BorderLayout());
		this.add(pnlWest, BorderLayout.WEST);
		this.add(pnlMitte, BorderLayout.CENTER);
		this.add(pnlOsten, BorderLayout.EAST);
		this.setVisible(true);

	}

}
