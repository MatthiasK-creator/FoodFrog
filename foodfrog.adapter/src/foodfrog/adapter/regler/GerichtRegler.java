package foodfrog.adapter.regler;

import java.util.ArrayList;
import java.util.List;

import foodfrog.adapter.beobachter.muster.Beobachter;
import foodfrog.adapter.beobachter.muster.Ereignis;
import foodfrog.adapter.beobachter.muster.Subjekt;
import foodfrog.applikation.EntiaetVerwalter;
import foodfrog.applikation.Gerichtverwaltung;
import foodfrog.kern.Entitaet;
import foodfrog.kern.Gericht;
import foodfrog.kern.Kategorie;

public class GerichtRegler implements Subjekt{
	
	private Gerichtverwaltung verwaltung;
	
	private List<Beobachter> beobachter;
	
	
	public GerichtRegler(Gerichtverwaltung verwaltung) {
		this.beobachter = new ArrayList<>();
		this.verwaltung = verwaltung;
	}
	
	public Gericht erstelleGericht(Gericht gericht) {
		return this.verwaltung.erstelleGericht(gericht);
	}
	
	public Gericht aendereGericht(Gericht gericht) {
		return this.verwaltung.aendereGericht(gericht.getId(), gericht);
	}
	
	public boolean loescheGericht(long id) {
		return this.verwaltung.loescheGericht(Gericht.class, id);
	}
	
	public List<Gericht> holeAlle(){
		return this.verwaltung.holeAlle();
	}
	

	@Override
	public void meldeAn(Beobachter beobachter) {
		if(!this.beobachter.contains(beobachter)) {
			this.beobachter.add(beobachter);
		}
	}



	@Override
	public void meldeAb(Beobachter beobachter) {
		if(this.beobachter.contains(beobachter)) {
			this.beobachter.remove(beobachter);
		}
	}



	@Override
	public void benachrichtige() {
		for (Beobachter beobachter : beobachter) {
			beobachter.aktualisiere(this.verwaltung.holeAlle());
		}
	}




}
