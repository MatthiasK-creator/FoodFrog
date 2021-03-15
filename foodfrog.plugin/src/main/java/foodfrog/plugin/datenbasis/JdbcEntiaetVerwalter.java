package foodfrog.plugin.datenbasis;

import java.util.HashMap;
import java.util.List;

import foodfrog.applikation.EntiaetVerwalter;
import foodfrog.kern.Gericht;
import foodfrog.kern.Kategorie;

public class JdbcEntiaetVerwalter implements EntiaetVerwalter{

	public Gericht erstellle(Gericht gericht) {
		return null;
	}

	public boolean loesche(long id) {
		return false;
	}

	public Gericht aendere(long id, Gericht gericht) {
		return null;
	}

	public Gericht hole(long id) {
		return null;
	}

	public Gericht holeZufaellig() {
		return null;
	}

	public Gericht holeZufaelligMitFilter(List<Kategorie> filter) {
		return null;
	}

	public List<Gericht> holeAlleGerichte() {
		return null;
	}
	

}
