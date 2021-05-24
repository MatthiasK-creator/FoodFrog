package foodfrog.adapter.regler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import foodfrog.adapter.beobachter.muster.Beobachter;
import foodfrog.adapter.beobachter.muster.Ereignis;
import foodfrog.adapter.beobachter.muster.Subjekt;
import foodfrog.applikation.Wochenplanverwaltung;
import foodfrog.kern.Gericht;
import foodfrog.kern.Kategorie;

public class WochenplanRegler implements Subjekt{
	
	private Wochenplanverwaltung wochenplanVerwaltung;
	
	private List<Beobachter> beobachter;
	

	public WochenplanRegler(Wochenplanverwaltung wochenplanVerwaltung) {
		this.wochenplanVerwaltung = wochenplanVerwaltung;
		this.beobachter = new ArrayList<>();
	}
	
	
	public List<Gericht> generiereWochenplan(int anzahl, List<Kategorie> kategorien){
		return this.wochenplanVerwaltung.generiereWochenplan(anzahl, kategorien);
	}
	
	public boolean gerichtLoeschen(Gericht gericht) {
		return this.wochenplanVerwaltung.gerichtLoeschen(gericht);
	}
	
	public void gerichtHinzufuegen(Gericht gericht) {
		this.wochenplanVerwaltung.gerichtHinzufuegen(gericht);
	}
	
	public ArrayList<Kategorie> holeAlleKategorien(){
		return this.wochenplanVerwaltung.holeAlleKategorien();
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
			beobachter.aktualisiere(this.wochenplanVerwaltung.getWochenplan());
		}
		
	}

	

}
