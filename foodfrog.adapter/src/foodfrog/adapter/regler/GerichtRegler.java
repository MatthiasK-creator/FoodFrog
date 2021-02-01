package foodfrog.adapter.regler;

import java.util.ArrayList;
import java.util.List;

import foodfrog.adapter.beobachter.muster.Beobachter;
import foodfrog.adapter.beobachter.muster.Ereignis;
import foodfrog.adapter.beobachter.muster.Subjekt;
import foodfrog.applikation.Gerichtverwaltung;
import foodfrog.kern.Gericht;

public class GerichtRegler implements Beobachter, Subjekt{
	
	private Gerichtverwaltung verwaltung;
	private Gericht aktuellesGericht;
	
	private List<Beobachter> beobachter;
	
	private static final String KOMANNDO_GERICHT_LOESCHEN = "GERICHT.LOESCHEN";
	private static final String KOMANNDO_GERICHT_AENDERN = "GERICHT.AENDERN";
	private static final String KOMANNDO_GERICHT_ANLEGEN = "GERICHT.ANLEGEN";
	
	public GerichtRegler(Gerichtverwaltung verwaltung) {
		this.beobachter = new ArrayList<>();
		this.verwaltung = verwaltung;
	}
	
	@Override
	public void aktualisiere(Ereignis ereignis) {
		this.aktuellesGericht  = (Gericht) ereignis.getDaten();
		if(ereignis.getKommando().equals(KOMANNDO_GERICHT_ANLEGEN)) {
			this.verwaltung.erstelleGericht(this.aktuellesGericht);
		}else if(ereignis.getKommando().equals(KOMANNDO_GERICHT_AENDERN)) {
			this.verwaltung.aendereGericht(this.aktuellesGericht.getId(), aktuellesGericht);
		}else if(ereignis.getKommando().equals(KOMANNDO_GERICHT_LOESCHEN)) {
			this.verwaltung.loescheGericht(this.aktuellesGericht.getId());
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
