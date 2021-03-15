package foodfrog.applikation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import foodfrog.kern.Gericht;
import foodfrog.kern.Kategorie;
import foodfrog.kern.Zutat;

public class Wochenplanverwaltung {
	
	private List<Gericht> wochenplan;
	private EntiaetVerwalter verwalter;
	
	public Wochenplanverwaltung(EntiaetVerwalter verwalter) {
		this.verwalter = verwalter;
		wochenplan = new ArrayList<>();
	}
	
	public List<Gericht> generiereWochenplan (int anzahl, List<Kategorie> filterListe){
		wochenplan.clear();
		this.wochenplan = verwalter.holeZufaelligMitFilter(anzahl, filterListe);
		return this.wochenplan;
	}
	
	public boolean gerichtLoeschen(Gericht gericht){
		if(wochenplan.contains(gericht)) {
			this.wochenplan.remove(gericht);
			return true;
		}
		return false;
	}
	
	public boolean gerichtHinzufuegen(Gericht gericht) {
		if(this.wochenplan.size() < 7 && !this.wochenplan.contains(gericht)) {
			this.wochenplan.add(holeNeuesGericht());
			return true;
		}else {
			return false;
		}

	}
	
	public HashMap<String, List<Zutat>>  generiereEinkaufsliste() {
		HashMap<String, List<Zutat>> wochenPlanZutaten = new HashMap<>();
		for (Gericht gericht : wochenplan) {
			wochenPlanZutaten.put(gericht.getName(), gericht.getZutaten());
		}
		return wochenPlanZutaten;
	}

	private Gericht holeNeuesGericht() {
		Gericht gericht = this.verwalter.holeZufaellig();
		while(this.wochenplan.contains(gericht)) {
			gericht = this.verwalter.holeZufaellig();
		}
		return gericht;
	}
	
	public List<Gericht> getWochenplan() {
		return wochenplan;
	}
	

	
	

}
