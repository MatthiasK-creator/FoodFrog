package foodfrog.plugin;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Hauptfenster extends JFrame {

	private JButton btnWochenplan;
	private JButton btnEinkaufliste;
	private JButton btnRezepterstellung;
	private JLabel label;
	private JLabel copyright;
	private JButton foodfrogLogo;
	private JPanel hauptPanel;
	private JPanel buttonPanel;
	private GerichtFormular gerichtformular;

	public Hauptfenster() {

		hauptPanel = new JPanel();
		hauptPanel.setLayout(new GridLayout(4, 1));

		
		// Foodfrog Logo
		foodfrogLogo = new JButton();
		foodfrogLogo.setText("Foodfrog Logo");

//		try {
//			Image img = ImageIO.read(getClass().getResource("resources/foodfrogLogo.png"));
//			foodfrogLogo.setIcon(new ImageIcon(img));
//		} catch (Exception ex) {
//			System.out.println(ex);
//		}

		label = new JLabel();
		label.setText("... der elegante Weg das Essen der Woche zu planen");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(label.getFont().deriveFont(20f));
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,3));

		btnWochenplan = new JButton("zum Wochenplan");
		btnWochenplan.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btnEinkaufliste = new JButton("zur Einkaufsliste");
		btnEinkaufliste.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		

		btnRezepterstellung = new JButton("zur Rezepterstellung");
		btnRezepterstellung.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				GerichtFormular gerichtformular = new GerichtFormular();
				hauptPanel.removeAll();
				hauptPanel.add(gerichtformular);
				Hauptfenster.this.repaint();
				Hauptfenster.this.revalidate();
				
			}
		});
		
		
		buttonPanel.add(btnWochenplan);
		buttonPanel.add(btnEinkaufliste);
		buttonPanel.add(btnRezepterstellung);
		
		copyright = new JLabel();
		copyright.setText("\u00a9 2020 Viktoria Kunzmann & Matthias Klee");
		copyright.setHorizontalAlignment(SwingConstants.CENTER);
		copyright.setFont(copyright.getFont().deriveFont(12f));
		
		hauptPanel.add(foodfrogLogo);
		hauptPanel.add(label);
		hauptPanel.add(buttonPanel);
		hauptPanel.add(copyright);
		
		this.add(hauptPanel);
		this.setTitle("foodfrog");
		this.setSize(800, 500);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}
