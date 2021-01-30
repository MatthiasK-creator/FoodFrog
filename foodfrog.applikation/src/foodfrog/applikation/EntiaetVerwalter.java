package foodfrog.applikation;

import foodfrog.kern.Gericht;

public interface EntiaetVerwalter {
	public Gericht erstellle(Gericht gericht);
	public boolean loesche(long id);
	public Gericht aendere(long id, Gericht gericht);
	public Gericht hole(long id);

}
