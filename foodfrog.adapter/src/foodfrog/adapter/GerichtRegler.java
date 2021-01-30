package foodfrog.adapter;

import foodfrog.applikation.Gerichtverwaltung;

public class GerichtRegler extends Beobachter{
	
	Gerichtverwaltung verwaltung;
	
	public GerichtRegler(Gerichtverwaltung verwaltung) {
		this.verwaltung = verwaltung;
	}
	
	
	@Override
	public void aktualisiere(Object o) {
	}

}
