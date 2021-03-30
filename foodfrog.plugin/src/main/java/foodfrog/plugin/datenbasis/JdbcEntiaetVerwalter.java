package foodfrog.plugin.datenbasis;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import foodfrog.applikation.EntiaetVerwalter;
import foodfrog.kern.Bild;
import foodfrog.kern.Einheit;
import foodfrog.kern.Entitaet;
import foodfrog.kern.Gericht;
import foodfrog.kern.Kategorie;
import foodfrog.kern.Zutat;

public class JdbcEntiaetVerwalter implements EntiaetVerwalter {

	private JdbcVerbinder verbinder;

	public JdbcEntiaetVerwalter() {
		verbinder = JdbcVerbinder.holeInstanz();
	}

	public Entitaet erstellle(Class c, Entitaet gericht) {
		return null;
	}

	public boolean loesche(Class c, long id) {
		return false;
	}

	public Entitaet aendere(Class c, long id, Entitaet gericht) {
		return null;
	}

	public Entitaet hole(Class c, long id) {
		return null;
	}

	public List<Gericht> holeZufaelligMitFilter(int anzahl, List<Kategorie> filter) {
		String anweisung = "SELECT DISTINCT * FROM gerichte LEFT JOIN kategorien ON (gerichte.id = kategorien.gericht)";
		for (Kategorie kategorie : filter) {
			anweisung += " AND kategorien.bezeichnung = \"" + kategorie.getBezeichnung()+"\"";
		}
		anweisung += " ORDER BY RANDOM() LIMIT 5";
		System.out.println("Aweisung: " + anweisung);
		ResultSet alleGerichte = this.verbinder.fuehreAnweisungAus(anweisung);
		List<Gericht> listeMitAllenGerichten = new ArrayList<Gericht>();
		try {
			while (alleGerichte.next()) {
				listeMitAllenGerichten.add(this.erstelleGericht(alleGerichte));
			}
			return listeMitAllenGerichten;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Entitaet> holeAlle(Class c) {
		if (c == Kategorie.class) {
			String anweisung = "SELECT * FROM kategorien";
			ResultSet alleKategorien = this.verbinder.fuehreAnweisungAus(anweisung);
			List<Entitaet> listeMitAllenGerichten = new ArrayList<>();
			try {
				while(alleKategorien.next()) {
					Kategorie kat = new Kategorie(alleKategorien.getInt("id"), alleKategorien.getString("bezeichnung"));
					listeMitAllenGerichten.add(kat);
					return listeMitAllenGerichten;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else if (c == Gericht.class) {
			String anweisung = "SELECT * FROM gerichte";
			ResultSet alleGerichte = this.verbinder.fuehreAnweisungAus(anweisung);
			List<Entitaet> listeMitAllenGerichten = new ArrayList();
			try {
				while (alleGerichte.next()) {
					listeMitAllenGerichten.add(this.erstelleGericht(alleGerichte));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return listeMitAllenGerichten;
		}
		return new ArrayList<Entitaet>();

	}

	private Gericht erstelleGericht(ResultSet alleGerichte) throws SQLException {
		Gericht gericht = null;
		gericht = new Gericht(alleGerichte.getInt("id"), alleGerichte.getString("name"),
				alleGerichte.getString("beschreibung"), alleGerichte.getInt("aufwand"));
		
		// Hole alle Bilder
		String bilderAnweisung = "SELECT * FROM bilder, gerichte WHERE gerichte.id = " + gericht.getId();
		ResultSet alleBilder = this.verbinder.fuehreAnweisungAus(bilderAnweisung);
		List<Bild> bilderListe = new ArrayList<Bild>();
		while (alleBilder.next()) {
			Bild bild = new Bild(alleBilder.getInt("id"), alleBilder.getString("titel"), alleBilder.getBytes("grafik"));
			bilderListe.add(bild);
		}
		gericht.setBilder(bilderListe);
		
		// Hole alle Kategorien
		String kategorieAnweisung = "SELECT * FROM kategorien, gerichte WHERE kategorien.gericht = "+ gericht.getId();
		ResultSet alleKategorien = this.verbinder.fuehreAnweisungAus(kategorieAnweisung);
		List<Kategorie> kategorienListe = new ArrayList<Kategorie>();
		while (alleKategorien.next()) {
			Kategorie kategorie = new Kategorie(alleKategorien.getInt("id"), alleKategorien.getString("bezeichnung"));
			kategorienListe.add(kategorie);
		}
		gericht.setKategorien(kategorienListe);

		//Hole alle Zutaten
		String zutatenAnweisung = "SELECT * FROM zutaten, gerichte WHERE zutaten.gericht = " + gericht.getId();
		ResultSet alleZutaten = this.verbinder.fuehreAnweisungAus(zutatenAnweisung);
		List<Zutat> zutatenListe = new ArrayList<Zutat>();
		while (alleZutaten.next()) {
			Zutat zutat = new Zutat(alleZutaten.getInt("id"), alleZutaten.getString("bezeichnung"), alleZutaten.getInt("menge"));
			String einheitenAnweisung = "SELECT * FROM einheiten, zutaten WHERE einheiten.id = zutaten.id AND zutaten.id = " + zutat.getId();
			this.verbinder.fuehreAnweisungAus(einheitenAnweisung);
			ResultSet alleEinheiten = this.verbinder.fuehreAnweisungAus(einheitenAnweisung);
			while(alleEinheiten.next()) {
				zutat.setEinheit(Einheit.valueOf(alleEinheiten.getString("einheit")));
			}
			zutatenListe.add(zutat);
		}
		gericht.setZutaten(zutatenListe);
		
		return gericht;

	}

	public Entitaet holeZufaellig(Class c) {
		// TODO Auto-generated method stub
		return null;
	}

}
