package foodfrog.adapter;

import foodfrog.applikation.Gerichtverwaltung;
import foodfrog.kern.Gericht;

public class GerichtRegler implements Beobachter, Subjekt{
	
	private Gerichtverwaltung verwaltung;
	private Subjekt subjekt;
	private Gericht aktuellesGericht;
	
	public GerichtRegler(Gerichtverwaltung verwaltung, Subjekt subjekt) {
		this.verwaltung = verwaltung;
		this.subjekt = subjekt;
	}
	
	@Override
	public void aktualisiere() {
		this.aktuellesGericht  = (Gericht)subjekt.gibZustand();
		verwaltung.erstelleGericht(this.aktuellesGericht);
	}



	@Override
	public void meldeAn(Beobachter beobachter) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void meldeAb(Beobachter beobachter) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void benachrichtige(Object o) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public Object gibZustand() {
		return this.aktuellesGericht;
	}

}
