package foodfrog.applikation;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import foodfrog.kern.Entitaet;
import foodfrog.kern.Gericht;
import foodfrog.kern.Kategorie;

public class Gerichtverwaltung {
	
	public EntiaetVerwalter verwalter;
	
	public Gerichtverwaltung(EntiaetVerwalter verwalter) {
		this.verwalter = verwalter;
	}
	
	public boolean loescheGericht(Class c, long id){
		if(this.verwalter.hole(c, id) != null) {
			this.verwalter.loesche(c, id);
			return true;
		}
		else {
			return false;
		}
	}
	
	public Gericht aendereGericht(long id, Gericht gericht){
		return (Gericht) this.verwalter.aendere(Gericht.class, id, gericht);
	}
	
	public Gericht erstelleGericht(Gericht gericht){
		return (Gericht) this.verwalter.erstellle(Gericht.class, gericht);
	}
	
	public Gericht holeGericht(long id){
		return (Gericht) this.verwalter.hole(Gericht.class, id);
	}
	
	// Eigentlich ist das die Aufgabe von einem KategorieRegler.
	public ArrayList<Kategorie> holeAlleKategorien(){
		ArrayList<Kategorie> kategorienliste = new ArrayList<>();
		this.verwalter.holeAlle(Kategorie.class).forEach(x -> kategorienliste.add((Kategorie) x));
		return kategorienliste;
	}

	
	public List<Gericht> holeAlle() {
		List<Gericht> alleGerichte = new ArrayList<Gericht>();
		for (Entitaet entitaet : this.verwalter.holeAlle(Gericht.class)) {
			alleGerichte.add((Gericht)entitaet);
		}

		return alleGerichte ;
	}

}
