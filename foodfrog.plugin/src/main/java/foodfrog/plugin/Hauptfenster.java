package foodfrog.plugin;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Hauptfenster extends JFrame{
	
	public JButton btnWochenplan;
	public JButton btnEinkaufliste;
	public JButton btnRezepterstellung;
	public JLabel label;
	public JLabel copyright;
	public JButton foodfrogLogo;
	
	public Hauptfenster () {
		
		foodfrogLogo = new JButton();
		
		this.setTitle("foodfrog");
		this.setSize(800,500);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	

}
