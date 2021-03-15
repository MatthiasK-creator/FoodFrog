package foodfrog.plugin;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ZutatenMengenKomponente extends JPanel {
	
	private JTextField tfMenge;
	private JTextField tfZutat;
	private JComboBox einheitbox;

	public ZutatenMengenKomponente() {
		
		this.setLayout(new GridLayout(1,3));
		
		tfMenge = new JTextField();
		
		einheitbox = new JComboBox();
		
		tfZutat = new JTextField();
		
		this.add(tfMenge);
		this.add(einheitbox);
		this.add(tfZutat);
		
		
//		this.setVisible(true);
		
		
		
	}
	
	

}
