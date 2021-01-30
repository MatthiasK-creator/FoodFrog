package foodfrog.applikation;

import foodfrog.kern.Gericht;

public class Gerichtverwaltung {
	
	public EntiaetVerwalter verwalter;
	
	public Gerichtverwaltung(EntiaetVerwalter verwalter) {
		this.verwalter = verwalter;
	}
	
	public boolean loescheGericht(long id){
		if(this.verwalter.hole(id) != null) {
			this.verwalter.loesche(id);
			return true;
		}
		else {
			return false;
		}
	}
	
	public Gericht aendereGericht(long id, Gericht gericht){
		return this.verwalter.aendere(id, gericht);
	}
	
	public Gericht erstelleGericht(Gericht gericht){
		return this.verwalter.erstellle(gericht);
	}
	
	public Gericht holeGericht(long id){
		return this.verwalter.hole(id);
	}
	

}
