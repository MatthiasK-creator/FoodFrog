package foodfrog.adapter.renderer;

import java.util.Calendar;
import java.util.List;

import foodfrog.kern.Gericht;
import foodfrog.kern.Kategorie;
import foodfrog.kern.Zutat;

public class GerichtRenderer{
	private static GerichtRenderer gerichtRenderer = new GerichtRenderer();
	private ZutatRenderer zutatRenderer = ZutatRenderer.holeInstanz();
	
	private GerichtRenderer() {
	}
	
	public static GerichtRenderer holeInstanz() {
		return gerichtRenderer;
	}
	
	public String renderKopfzeile(Gericht gericht) {
		return gericht.getName();
	}
	public String renderKategorien(Gericht gericht) {
		String kategorienListe = "";
		for (Kategorie kategorie: gericht.getKategorien()) {
			kategorienListe += ","+kategorie.getBezeichnung();
		}
		
		return kategorienListe;
	}
	
	public String renderDauer(Gericht gericht) {
		return "Dauer: " + gericht.getAufwand() + " min";
	}
	
	public String renderZutaten(List<Zutat> zutaten) {
		String zutatenListe = "";
		for (Zutat zutat : zutaten) {
			zutatenListe += "\n" + zutatRenderer.renderZutat(zutat);
		}
		return zutatenListe;
	}
	
}
