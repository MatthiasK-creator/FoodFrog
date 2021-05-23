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

	public JdbcEntiaetVerwalter(JdbcVerbinder verbinder) {
		this.verbinder = verbinder;
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
						+ "'), (SELECT id FROM gerichte WHERE id=" + this.verbinder.holeIdVomErstelltenGericht() + "))";
			}

			for (Bild bild : gericht.getBilder()) {
				String erzeugeBilder = "INSERT INTO BILDER (titel, grafik, gericht) VALUES('" + bild.getTitel()
						+ "', ?, (SELECT id FROM gerichte WHERE id=" + this.verbinder.holeIdVomErstelltenGericht()
						+ "));";
				this.verbinder.fuehreVorbereiteteAnweisungAus(erzeugeBilder, bild.getGrafik());
			}
		}
		return gericht;
	}

	public boolean loesche(Class c, int id) {
		return false;
	}

	public Entitaet aendere(Class c, int id, Entitaet gericht) {
		return null;
	}

	public Entitaet hole(Class c, int id) {
		return null;
	}

	public List<Gericht> holeZufaelligMitFilter(int anzahl, List<Kategorie> filter) {
		String anweisung = "SELECT DISTINCT * FROM gerichte LEFT JOIN kategorien ON (gerichte.id = kategorien.gericht)";
		if (filter.size() > 0) {
			anweisung += " WHERE";
			anweisung += " kategorien.bezeichnung = \"" + filter.get(0).getBezeichnung() + "\"";

		}
		for (int i = 1; i < filter.size(); i++) {
			anweisung += " OR kategorien.bezeichnung = \"" + filter.get(i).getBezeichnung() + "\"";
		}
		anweisung += " GROUP BY gerichte.id";
		anweisung += " ORDER BY RANDOM() LIMIT " + anzahl;
		System.out.println(anweisung);
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

	private List<Bild> holeAlleBilderVomGericht(ResultSet bilder) throws SQLException {
		List<Bild> bilderListe = new ArrayList<Bild>();
		while (bilder.next()) {
			Bild bild = new Bild(bilder.getInt("id"), bilder.getString("titel"), bilder.getBytes("grafik"));
			bilderListe.add(bild);
		}
		return bilderListe;
	}

	private List<Kategorie> holeAlleKategorienVomGericht(ResultSet kategorien) throws SQLException {
		List<Kategorie> kategorienListe = new ArrayList<Kategorie>();
		while (kategorien.next()) {
			Kategorie kategorie = new Kategorie(kategorien.getInt("id"), kategorien.getString("bezeichnung"));
			kategorienListe.add(kategorie);
		}
		return kategorienListe;
	}

	private List<Zutat> holeAlleZutatenVomGericht(ResultSet zutaten) throws SQLException {
		List<Zutat> zutatenListe = new ArrayList<Zutat>();
		while (zutaten.next()) {
			Zutat zutat = new Zutat(zutaten.getInt("id"), zutaten.getString("bezeichnung"), zutaten.getInt("menge"));
			zutat.setEinheit(this.holeEinheitVonZutat(this.verbinder.fuehreAnweisungAus(
					"SELECT * FROM einheiten, zutaten WHERE einheiten.id = zutaten.id AND einheiten.id = " + zutaten.getString("einheit"))));
			zutatenListe.add(zutat);
		}
		return zutatenListe;
	}

	private Einheit holeEinheitVonZutat(ResultSet einheiten) throws SQLException {
		Einheit einheit = null;
		while (einheiten.next()) {
			einheit = Einheit.valueOf(einheiten.getString("einheit"));
		}
		return einheit;
	}

	private Gericht erstelleGericht(ResultSet alleGerichte) throws SQLException {
		Gericht gericht = new Gericht(alleGerichte.getInt("id"), alleGerichte.getString("name"),
				alleGerichte.getString("beschreibung"), alleGerichte.getInt("aufwand"));

		gericht.setBilder(this.holeAlleBilderVomGericht(
				this.verbinder.fuehreAnweisungAus("SELECT * FROM bilder WHERE bilder.gericht = " + gericht.getId())));

		gericht.setKategorien(this.holeAlleKategorienVomGericht(this.verbinder
				.fuehreAnweisungAus("SELECT * FROM kategorien WHERE kategorien.gericht = " + gericht.getId())));

		gericht.setZutaten(this.holeAlleZutatenVomGericht(
				this.verbinder.fuehreAnweisungAus("SELECT * FROM zutaten WHERE zutaten.gericht = " + gericht.getId())));

		return gericht;
	}

	public Entitaet holeZufaellig(Class c) {
		// TODO Auto-generated method stub
		return null;
	}

}
