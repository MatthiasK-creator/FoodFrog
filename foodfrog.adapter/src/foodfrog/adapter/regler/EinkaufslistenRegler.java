package foodfrog.adapter.regler;

import java.util.HashMap;
import java.util.List;

import foodfrog.adapter.beobachter.muster.Beobachter;
import foodfrog.adapter.beobachter.muster.Ereignis;
import foodfrog.adapter.beobachter.muster.Subjekt;
import foodfrog.applikation.Wochenplanverwaltung;
import foodfrog.kern.Zutat;

public class EinkaufslistenRegler implements Subjekt{
	
	private Wochenplanverwaltung wochenplanVerwaltung;
	private List<Beobachter> beobachter;
	
	public EinkaufslistenRegler(Wochenplanverwaltung verwaltung) {
		this.wochenplanVerwaltung = verwaltung;
	}
	
	public HashMap<String, List<Zutat>> generiereEinkaufsliste(){
		return this.wochenplanVerwaltung.generiereEinkaufsliste();
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
			beobachter.aktualisiere(this.wochenplanVerwaltung.generiereEinkaufsliste());
		}
	}
}
