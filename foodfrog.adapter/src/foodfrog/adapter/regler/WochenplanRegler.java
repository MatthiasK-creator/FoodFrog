package foodfrog.adapter.regler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import foodfrog.adapter.beobachter.muster.Beobachter;
import foodfrog.adapter.beobachter.muster.Ereignis;
import foodfrog.adapter.beobachter.muster.Subjekt;
import foodfrog.applikation.Wochenplanverwaltung;
import foodfrog.kern.Gericht;
import foodfrog.kern.Kategorie;

public class WochenplanRegler implements Beobachter, Subjekt{
	
	Wochenplanverwaltung wochenplanVerwaltung;
	
	private List<Beobachter> beobachter;
	
	private static final String GENERIERE_WOCHENPLAN = "GENERIERE.WOCHENPLAN";
	private static final String ENTFERNE_GERICHT = "ENTFERNE.GERICHT";
	private static final String FUEGE_GERICHT_HINZU = "FUEGE.GERICHT.HINZU";
	private static final String ERZEUGE_EINKAUFSLISTE = "ERZEUGE.EINKAUFSLISTE";
	
	private static final String ANZAHL_SCHLUESSEL = "ANZAHL.SCHLUESSEL";
	private static final String KATEGORIEN_SCHLUESSEL = "KATEGORIEN.SCHLUESSEL";

	public WochenplanRegler(Wochenplanverwaltung wochenplanVerwaltung) {
		this.wochenplanVerwaltung = wochenplanVerwaltung;
		this.beobachter = new ArrayList<>();
	}
	
	@Override
	public void aktualisiere(Ereignis ereignis) {
		if(ereignis.getKommando().equals(GENERIERE_WOCHENPLAN)) {
			HashMap<String, Object> wochenplanDaten = (HashMap<String, Object>) ereignis.getDaten();
			int anzahl = (int) wochenplanDaten.get(ANZAHL_SCHLUESSEL);
			List<Kategorie> kategorien = (List<Kategorie>) wochenplanDaten.get(KATEGORIEN_SCHLUESSEL);
			this.wochenplanVerwaltung.generiereWochenplan(anzahl, kategorien);
			this.benachrichtige(new Ereignis(this, GENERIERE_WOCHENPLAN, wochenplanVerwaltung.getWochenplan()));
		}else if(ereignis.getKommando().equals(ENTFERNE_GERICHT)) {
			this.wochenplanVerwaltung.gerichtLoeschen((Gericht)ereignis.getDaten());
			this.benachrichtige(new Ereignis(this, ENTFERNE_GERICHT, wochenplanVerwaltung.getWochenplan()));
		}else if(ereignis.getKommando().equals(FUEGE_GERICHT_HINZU)) {
			this.wochenplanVerwaltung.gerichtHinzufuegen((Gericht)ereignis.getDaten());
			this.benachrichtige(new Ereignis(this, FUEGE_GERICHT_HINZU, wochenplanVerwaltung.getWochenplan()));
		}else if(ereignis.getKommando().equals(ERZEUGE_EINKAUFSLISTE)) {
			this.benachrichtige(new Ereignis(this, ERZEUGE_EINKAUFSLISTE, this.wochenplanVerwaltung.generiereEinkaufsliste()));
		}
		
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
	public void benachrichtige(Ereignis ereignis) {
		for (Beobachter beobachter : beobachter) {
			beobachter.aktualisiere(ereignis);
		}
		
	}
	
	

}
