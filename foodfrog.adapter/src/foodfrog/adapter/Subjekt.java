package foodfrog.adapter;

import java.util.ArrayList;
import java.util.List;

public abstract class Subjekt {
	private List<Beobachter> beobachterListe = new ArrayList<>();
	
	public void meldeAn(Beobachter beobachter) {
		if(!beobachterListe.contains(beobachter)) {
			this.beobachterListe.add(beobachter);
		}
	};
	
	public void meldeAb(Beobachter beobachter) {
		if(!beobachterListe.contains(beobachter)) {
			this.beobachterListe.remove(beobachter);
		}
	};
	
	public void benachrichtige(Object o) {
		for (Beobachter beobachter : beobachterListe) {
			beobachter.aktualisiere(o);
		}
	}
	
	public Object gibZustand() {
		return null;
	};

}
