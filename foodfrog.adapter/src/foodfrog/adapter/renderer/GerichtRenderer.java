package foodfrog.adapter.renderer;

import java.util.Calendar;
import java.util.Iterator;
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
		kategorienListe += gericht.getKategorien().get(0);
		for (int i = 1; i < gericht.getKategorien().size(); i++) {
			kategorienListe += ","+gericht.getKategorien().get(i).getBezeichnung();
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
	
	public String renderBeschreibung(Gericht gericht) {
		return gericht.getBeschreibung();
	}
	
	public String renderAlleZutaten(List<Gericht> gerichte) {
		String zutatenListe = "";
		for (Gericht gericht : gerichte) {
			zutatenListe += "\n" + this.renderZutaten(gericht.getZutaten());
		}
		return zutatenListe;
	}
	
}
