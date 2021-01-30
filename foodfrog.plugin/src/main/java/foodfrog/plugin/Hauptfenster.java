package foodfrog.plugin;

import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Hauptfenster extends JFrame{
	
	public JButton btnWochenplan;
	public JButton btnEinkaufliste;
	public JButton btnRezepterstellung;
	public JLabel label;
	public JLabel copyright;
	public JButton foodfrogLogo;
	public JPanel hauptPanel;
	public JPanel buttonPanel;
	
	public Hauptfenster () {
		
		hauptPanel = new JPanel();
		hauptPanel.setLayout(new GridLayout(4,1));
		
		foodfrogLogo = new JButton();
		foodfrogLogo.setText("Foodfrog Logo");
		
		label = new JLabel();
		label.setText("... der elegante Weg das Essen der Woche zu planen");
		
		btnWochenplan = new JButton();
		
		btnEinkaufliste = new JButton();
		
		btnRezepterstellung = new JButton();
		
		this.setTitle("foodfrog");
		this.setSize(800,500);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	

}
