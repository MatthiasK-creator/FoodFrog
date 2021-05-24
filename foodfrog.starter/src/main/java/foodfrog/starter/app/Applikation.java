package foodfrog.starter.app;


import foodfrog.adapter.regler.GerichtRegler;
import foodfrog.adapter.regler.WochenplanRegler;
import foodfrog.applikation.Gerichtverwaltung;
import foodfrog.applikation.Wochenplanverwaltung;
import foodfrog.plugin.Hauptfenster;
import foodfrog.plugin.datenbasis.JdbcEntitaetVerwalter;
import foodfrog.plugin.datenbasis.JdbcVerbinder;

public class Applikation {
	
	public static void main(String[] args) {
		JdbcVerbinder verbinder = JdbcVerbinder.holeInstanz();
		JdbcEntitaetVerwalter verwalter = new JdbcEntitaetVerwalter(verbinder);
	    Gerichtverwaltung verwaltung = new Gerichtverwaltung(verwalter);
	    Wochenplanverwaltung wochenplanVerwaltung = new Wochenplanverwaltung(verwalter);
	    GerichtRegler gerichtRegler = new GerichtRegler(verwaltung);
	    WochenplanRegler wochenplanRegler = new WochenplanRegler(wochenplanVerwaltung);
		Hauptfenster hauptfenster = new Hauptfenster(gerichtRegler, wochenplanRegler);
	}

}

