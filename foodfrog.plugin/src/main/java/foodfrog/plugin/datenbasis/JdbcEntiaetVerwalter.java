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
import foodfrog.kern.Einheit;
import foodfrog.kern.Gericht;
import foodfrog.kern.Kategorie;

public class JdbcEntiaetVerwalter implements EntiaetVerwalter{
	
	private JdbcVerbinder verbinder;
	public JdbcEntiaetVerwalter() {
		verbinder = JdbcVerbinder.holeInstanz();
	}

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

	public List<Gericht> holeZufaelligMitFilter(int anzahl, List<Kategorie> filter) {
		String anweisung = "SELECT DISTINCT * FROM gerichte LEFT JOIN kategorien ON (gerichte.id = kategorien.gericht)";
		for (Kategorie kategorie : filter) {
			anweisung += "AND kategorien.bezeichnung = " + kategorie.getBezeichnung();
		}
		anweisung += " ORDER BY RANDOM() LIMIT 5";
		return null;
	}

	public List<Gericht> holeAlleGerichte() {
		String anweisung = "SELECT * FROM gerichte";
		ResultSet alleGerichte = this.verbinder.fuehreAnweisungAus(anweisung);
		List<Gericht> listeMitAllenGerichten = new ArrayList<Gericht>();
		
		try {
			while(alleGerichte.next()) {
				Gericht gericht = new Gericht(alleGerichte.getInt("id"), alleGerichte.getString("name"), alleGerichte.getString("beschreibung"), alleGerichte.getInt("aufwand"));
				listeMitAllenGerichten.add(gericht);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listeMitAllenGerichten;
	}

	
	

}
