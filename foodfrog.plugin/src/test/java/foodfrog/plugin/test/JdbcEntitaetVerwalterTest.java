package foodfrog.plugin.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.easymock.EasyMock;
import org.easymock.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import foodfrog.applikation.EntiaetVerwalter;
import foodfrog.applikation.Gerichtverwaltung;
import foodfrog.kern.Gericht;
import foodfrog.plugin.datenbasis.IJDBCAnweisungen;
import foodfrog.plugin.datenbasis.JdbcEntiaetVerwalter;
import foodfrog.plugin.datenbasis.JdbcVerbinder;

class JdbcEntitaetVerwalterTest {
	
	private JdbcVerbinder datenbankVerbindung;
	private JdbcEntiaetVerwalter jdbcVerwalter;
	private final static String HOLEZUFAELLIG_MIT_ANWEISUNG="SELECT DISTINCT * FROM gerichte LEFT JOIN kategorien ON (gerichte.id = kategorien.gericht) WHERE kategorien.bezeichnung = 'Vegetarisch' GROUP BY gerichte.id ORDER BY RANDOM() LIMIT 1";
	@BeforeEach
	void richteEin() throws Exception {
		datenbankVerbindung = EasyMock.createMock(IJDBCAnweisungen.class);
		this.jdbcVerwalter = new JdbcEntiaetVerwalter(datenbankVerbindung);
	}

	@Test
	void holeZufaelligMitFilter() throws SQLException {
		ResultSet set = EasyMock.createMock(ResultSet.class);
		EasyMock.expect(set.getInt("id")).andReturn(1);
		EasyMock.expect(datenbankVerbindung.fuehreAnweisungAus(HOLEZUFAELLIG_MIT_ANWEISUNG)).andReturn(set);
	}

}
