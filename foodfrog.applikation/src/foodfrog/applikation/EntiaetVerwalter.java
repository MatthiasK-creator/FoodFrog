package foodfrog.applikation;

import java.util.List;

import foodfrog.kern.Gericht;
import foodfrog.kern.Kategorie;
import foodfrog.kern.Entitaet;

public interface EntiaetVerwalter {
	public Entitaet erstellle(Class c, Entitaet gericht);
	public boolean loesche(Class c, int id);
	public Entitaet aendere(Class c, int id, Entitaet gericht);
	public Entitaet hole(Class c, int id);
	public Entitaet holeZufaellig(Class c);
	public List<Entitaet> holeAlle(Class c);
	public List<Gericht> holeZufaelligMitFilter(int anzahl, List<Kategorie> filter);
}
