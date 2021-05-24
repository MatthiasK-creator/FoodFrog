package foodfrog.plugin.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import foodfrog.applikation.EntiaetVerwalter;
import foodfrog.applikation.Gerichtverwaltung;
import foodfrog.kern.Entitaet;
import foodfrog.kern.Gericht;
import foodfrog.kern.Kategorie;
import foodfrog.plugin.datenbasis.IJDBCAnweisungen;
import foodfrog.plugin.datenbasis.JdbcEntiaetVerwalter;
import foodfrog.plugin.datenbasis.JdbcVerbinder;

class JdbcEntitaetVerwalterTest {
	
	private IJDBCAnweisungen datenbankVerbindung;
	private JdbcEntiaetVerwalter jdbcVerwalter;
	private final static String HOLEZUFAELLIG_MIT_ANWEISUNG="SELECT DISTINCT * FROM gerichte LEFT JOIN kategorien ON (gerichte.id = kategorien.gericht) WHERE kategorien.bezeichnung = 'Fischgericht' GROUP BY gerichte.id ORDER BY RANDOM() LIMIT 1";
	private final static String HOLE_ALLE_BILDER="SELECT * FROM bilder WHERE bilder.gericht = 1";
	private final static String HOLE_ALLE_KATEGORIEN="SELECT * FROM bilder WHERE bilder.gericht = 1";
	private final static String HOLE_ALLE_ZUTATEN="SELECT * FROM bilder WHERE bilder.gericht = 1";
	private final static String HOLE_EINHEIT="SELECT * FROM einheiten, zutaten WHERE einheiten.id = zutaten.id AND einheiten.id = ml";
	private ResultSet dbRueckgabe;
	
	@BeforeEach
	void richteEin() throws Exception {
		datenbankVerbindung = EasyMock.createMock(IJDBCAnweisungen.class);
		this.jdbcVerwalter = new JdbcEntiaetVerwalter(datenbankVerbindung);
		dbRueckgabe = EasyMock.createMock(ResultSet.class);
		EasyMock.expect(dbRueckgabe.getInt("id")).andReturn(1);
		EasyMock.expect(dbRueckgabe.getString("name")).andReturn("Seehecht mit KräuterBohnen-Risotto");
		EasyMock.expect(dbRueckgabe.getString("beschreibung")).andReturn("Rezept mit Schritt für Schritt Anleitung");
		EasyMock.expect(dbRueckgabe.getInt("aufwand")).andReturn(40);

	}

	@Test
	void holeZufaelligMitFilter() throws SQLException {
		EasyMock.expect(datenbankVerbindung.fuehreAnweisungAus(HOLEZUFAELLIG_MIT_ANWEISUNG)).andReturn(dbRueckgabe);
		EasyMock.replay(datenbankVerbindung);
		List<Gericht> zufaelligeGerichte = jdbcVerwalter.holeZufaelligMitFilter(1, Arrays.asList(new Kategorie(1, "Fischgericht")));
	}

}
