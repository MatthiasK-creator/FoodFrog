package foodfrog.applikation;

import java.util.List;

import foodfrog.kern.Gericht;
import foodfrog.kern.Kategorie;

public interface EntiaetVerwalter {
	public Gericht erstellle(Gericht gericht);
	public boolean loesche(long id);
	public Gericht aendere(long id, Gericht gericht);
	public Gericht hole(long id);
	public Gericht holeZufaellig();
	public Gericht holeZufaelligMitFilter(List<Kategorie> filter);
	public List<Gericht> holeAlleGerichte();

}
