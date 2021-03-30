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

	public Entitaet erstellle(Class c, Entitaet entitaet) {
		Gericht gericht = null;

		if (c == Gericht.class) {
			gericht = (Gericht) entitaet;
			String erzeugeGericht = "INSERT INTO gerichte (name, beschreibung, aufwand) VALUES ('" + gericht.getName()
					+ "', '" + gericht.getBeschreibung() + "', " + gericht.getAufwand() + " );";
			this.verbinder.fuehreAnweisungAus(erzeugeGericht);
			for (Kategorie kategorie : gericht.getKategorien()) {
				String erzeugeKategorie = "INSERT INTO kategorien (bezeichnung, gericht) VALUES ('"
						+ kategorie.getBezeichnung() + "', (SELECT id FROM gerichte WHERE id="
						+ this.verbinder.holeIdVomErstelltenGericht() + "));";
				this.verbinder.fuehreAnweisungAus(erzeugeKategorie);
			}
			for (Zutat zutat : gericht.getZutaten()) {
				String erzeugeZutaten = "INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('"
						+ zutat.getBezeichnung() + "', " + zutat.getMenge()
						+ ", (SELECT id FROM einheiten WHERE einheit = '" + zutat.getEinheit().einheit
						+ "'), (SELECT id FROM gerichte WHERE id="+this.verbinder.holeIdVomErstelltenGericht()+"))";
			}

			for (Bild bild : gericht.getBilder()) {
				String erzeugeBilder = "INSERT INTO BILDER (titel, grafik, gericht) VALUES('"+bild.getTitel()+"', ?, (SELECT id FROM gerichte WHERE id="+this.verbinder.holeIdVomErstelltenGericht()+"));";
				this.verbinder.fuehreVorbereiteteAnweisungAus(erzeugeBilder, bild.getGrafik());
			}
		}
		return gericht;
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
		if(filter.size() > 0) {
			anweisung += " WHERE";
			anweisung += " kategorien.bezeichnung = \"" + filter.get(0).getBezeichnung() + "\"";

		}
		for (int i = 1; i < filter.size(); i++) {
			anweisung += " OR kategorien.bezeichnung = \"" + filter.get(i).getBezeichnung() + "\"";
		}
		anweisung += " GROUP BY gerichte.id";
		anweisung += " ORDER BY RANDOM() LIMIT " + anzahl;
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
			String anweisung = "SELECT DISTINCT bezeichnung FROM kategorien";
			ResultSet alleKategorien = this.verbinder.fuehreAnweisungAus(anweisung);
			List<Entitaet> listeMitAllenGerichten = new ArrayList<>();
			try {
				while (alleKategorien.next()) {
					Kategorie kat = new Kategorie(alleKategorien.getString("bezeichnung"));
					listeMitAllenGerichten.add(kat);
				}
				return listeMitAllenGerichten;
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
		String bilderAnweisung = "SELECT * FROM bilder WHERE bilder.gericht = " + gericht.getId();
		ResultSet alleBilder = this.verbinder.fuehreAnweisungAus(bilderAnweisung);
		List<Bild> bilderListe = new ArrayList<Bild>();
		while (alleBilder.next()) {
			Bild bild = new Bild(alleBilder.getInt("id"), alleBilder.getString("titel"), alleBilder.getBytes("grafik"));
			bilderListe.add(bild);
		}
		gericht.setBilder(bilderListe);

		// Hole alle Kategorien
		String kategorieAnweisung = "SELECT * FROM kategorien WHERE kategorien.gericht = " + gericht.getId();
		ResultSet alleKategorien = this.verbinder.fuehreAnweisungAus(kategorieAnweisung);
		List<Kategorie> kategorienListe = new ArrayList<Kategorie>();
		while (alleKategorien.next()) {
			Kategorie kategorie = new Kategorie(alleKategorien.getInt("id"), alleKategorien.getString("bezeichnung"));
			kategorienListe.add(kategorie);
		}
		gericht.setKategorien(kategorienListe);

		// Hole alle Zutaten
		String zutatenAnweisung = "SELECT * FROM zutaten WHERE zutaten.gericht = " + gericht.getId();
		ResultSet alleZutaten = this.verbinder.fuehreAnweisungAus(zutatenAnweisung);
		List<Zutat> zutatenListe = new ArrayList<Zutat>();
		while (alleZutaten.next()) {
			Zutat zutat = new Zutat(alleZutaten.getInt("id"), alleZutaten.getString("bezeichnung"),
					alleZutaten.getInt("menge"));
			String einheitenAnweisung = "SELECT * FROM einheiten, zutaten WHERE einheiten.id = "
					+ alleZutaten.getString("einheit");
			this.verbinder.fuehreAnweisungAus(einheitenAnweisung);
			ResultSet alleEinheiten = this.verbinder.fuehreAnweisungAus(einheitenAnweisung);
			while (alleEinheiten.next()) {
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
