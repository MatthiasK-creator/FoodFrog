package foodfrog.plugin.komponenten;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import foodfrog.kern.Einheit;

public class ZutatenMengenKomponente extends JPanel {
	private JTextField tfMenge;
	private JTextField tfZutat;
	private JComboBox<Einheit> einheitbox;
	
	public ZutatenMengenKomponente() {
		this.setLayout(new GridLayout(1,3));
		tfMenge = new JTextField();
		einheitbox = new JComboBox(Einheit.values());
		tfZutat = new JTextField();
	
		this.add(tfMenge);
		this.add(einheitbox);
		this.add(tfZutat);
	}
	
	


}
