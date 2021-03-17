package foodfrog.plugin;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class WochenplanKomponente extends JPanel {

	private JLabel lblwochenplan;
	private JLabel foodfrogLogo;

	private JPanel pnlWPNavigation;

	private JButton btnNeueRezepte;
	private JButton btnStartseite;
	private JButton btnEinkaufsliste;
	private Hauptfenster hauptfenster;
	
	private JComboBox wochentagbox;

	public WochenplanKomponente() {

		// Navigationsleiste erstellen

		pnlWPNavigation = new JPanel();
		pnlWPNavigation.setLayout(new GridLayout(1, 6));

		// Button neue Rezepte

		btnNeueRezepte = new JButton("  neue Rezepte");
		btnNeueRezepte.setFont(new Font("Arial", Font.PLAIN, 20));
		try {
			Image imgNeueRezepte = ImageIO.read(getClass().getClassLoader().getResource("refresh.png"));
			System.out.println(imgNeueRezepte);
			imgNeueRezepte = imgNeueRezepte.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			btnNeueRezepte.setIcon(new ImageIcon(imgNeueRezepte));
		} catch (Exception ex) {
			System.out.println(ex);
		}

		// Button Startseite

		btnStartseite = new JButton("  Startseite");
		btnStartseite.setFont(new Font("Arial", Font.PLAIN, 20));
		try {
			Image imgStartseite = ImageIO.read(getClass().getClassLoader().getResource("gingerbread.png"));
			System.out.println(imgStartseite);
			imgStartseite = imgStartseite.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			btnStartseite.setIcon(new ImageIcon(imgStartseite));
		} catch (Exception ex) {
			System.out.println(ex);
		}

		btnStartseite.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				WochenplanKomponente.this.hauptfenster.requestFocus();

			}
		});

		// Button Einkaufsliste
		btnEinkaufsliste = new JButton("  Einkaufsliste");
		btnEinkaufsliste.setFont(new Font("Arial", Font.PLAIN, 20));
		try {
			Image imgEinkaufsliste = ImageIO.read(getClass().getClassLoader().getResource("einkaufen.png"));
			System.out.println(imgEinkaufsliste);
			imgEinkaufsliste = imgEinkaufsliste.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			btnEinkaufsliste.setIcon(new ImageIcon(imgEinkaufsliste));
		} catch (Exception ex) {
			System.out.println(ex);
		}

		lblwochenplan = new JLabel("Wochenplan");
		lblwochenplan.setFont(new Font("Arial", Font.PLAIN, 30));
		lblwochenplan.setHorizontalAlignment(SwingConstants.CENTER);

		
		// Label Logo Foodfrog f�r Rezeperstellung
		foodfrogLogo = new JLabel();
		
		try {
			Image imgFrog = ImageIO.read(getClass().getClassLoader().getResource("foodfrogLogo.png"));
			System.out.println(imgFrog);
			imgFrog = imgFrog.getScaledInstance(80, 100, Image.SCALE_SMOOTH);
			foodfrogLogo.setIcon(new ImageIcon(imgFrog));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		// Combobox mit Auswahl von Wochentagen
		
		wochentagbox = new JComboBox();

		pnlWPNavigation.add(btnNeueRezepte);
		pnlWPNavigation.add(btnStartseite);
		pnlWPNavigation.add(btnEinkaufsliste);
		pnlWPNavigation.add(lblwochenplan);
		pnlWPNavigation.add(foodfrogLogo);
		pnlWPNavigation.add(wochentagbox);

		// Alles hinzuf�gen

		this.add(pnlWPNavigation);
		this.setVisible(true);

	}

}
