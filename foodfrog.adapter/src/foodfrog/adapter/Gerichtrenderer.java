package foodfrog.adapter;

import foodfrog.kern.Gericht;

public class Gerichtrenderer{
	
	public String renderGericht(Gericht gericht) {
		return gericht.getName() + "\n" + gericht.getBeschreibung() + "\n" + gericht.getAufwand();
	}
}
