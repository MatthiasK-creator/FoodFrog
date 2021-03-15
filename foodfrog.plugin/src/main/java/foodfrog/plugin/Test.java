package foodfrog.plugin;

import foodfrog.adapter.regler.GerichtRegler;
import foodfrog.applikation.Gerichtverwaltung;

public class Test {
	
	public static void main(String[] args) {
		
	    Gerichtverwaltung verwaltung = new Gerichtverwaltung(null);
	    GerichtRegler regler = new GerichtRegler(verwaltung);
		Hauptfenster hauptfenster = new Hauptfenster(regler);
	}
	
}
