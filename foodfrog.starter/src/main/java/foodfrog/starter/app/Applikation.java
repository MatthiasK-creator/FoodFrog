package foodfrog.starter.app;


import foodfrog.adapter.regler.GerichtRegler;
import foodfrog.applikation.Gerichtverwaltung;
import foodfrog.plugin.Hauptfenster;
import foodfrog.plugin.datenbasis.JdbcEntiaetVerwalter;
import foodfrog.plugin.datenbasis.JdbcVerbinder;

public class Applikation {
	
	public static void main(String[] args) {
		JdbcVerbinder verbinder = JdbcVerbinder.holeInstanz();
		JdbcEntiaetVerwalter verwalter = new JdbcEntiaetVerwalter();
	    Gerichtverwaltung verwaltung = new Gerichtverwaltung(verwalter);
	    GerichtRegler regler = new GerichtRegler(verwaltung);
		Hauptfenster hauptfenster = new Hauptfenster(regler);
	}

}

