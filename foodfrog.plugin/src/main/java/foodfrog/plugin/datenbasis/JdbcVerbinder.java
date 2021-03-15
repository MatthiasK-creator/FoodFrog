package foodfrog.plugin.datenbasis;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.io.IOUtils;

import foodfrog.kern.Einheit;

public class JdbcVerbinder {
	private static final JdbcVerbinder dbVerbinder = new JdbcVerbinder();
	private Connection connection;
	private final String DB_PATH = "foodfrog.db";
	


	public static JdbcVerbinder holeInstanz() {
		return dbVerbinder;
	}
	
	
	private JdbcVerbinder() {
		if(connection != null) return;
		
		System.out.println("Datenbankverbindung wird aufgebaut...");
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:"+ DB_PATH);
			if(!connection.isClosed()) {
				System.out.println("Datenbankverbindung hergestellt.");
			}

		} catch (SQLException e) {
			System.err.println("Datenbankverbindung fehlgeschlagen: " );
			e.printStackTrace();
		}
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try
				{
					if (!connection.isClosed() && connection != null)
					{
						connection.close();
						if (connection.isClosed())
							System.out.println("Datenbankverbindung wird geschlossen");
					}
				}
				catch (final SQLException e)
				{
					e.printStackTrace();
				}
			}
		});
		this.erstelleDatenbankSchema();
		this.erstelleTestDaten();
	}



	private void erstelleDatenbankSchema() {
		System.out.println("Erstelle Bilder-Tabelle");
		System.out.println("----------------------------------------------------------------");
		final String erstelleBilderTabelle = "CREATE TABLE IF NOT EXISTS bilder (id INTEGER PRIMARY KEY AUTOINCREMENT, titel VARCHAR(255) NOT NULL, grafik BLOB NOT NULL, gericht INTEGER, FOREIGN KEY(gericht) REFERENCES gerichte(id))";
		try {
			Statement anweisung = connection.createStatement();
			anweisung.execute(erstelleBilderTabelle);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		System.out.println("Bilder-Tabelle erstellt.");
		System.out.println("----------------------------------------------------------------");
		
		System.out.println("Erstelle Kategorie-Tabelle");
		System.out.println("----------------------------------------------------------------");
		final String erstelleKategorieTabelle = "CREATE TABLE IF NOT EXISTS kategorien (id INTEGER PRIMARY KEY AUTOINCREMENT, bezeichnung VARCHAR(255) NOT NULL, gericht INTEGER, FOREIGN KEY(gericht) REFERENCES gerichte(id))";
		try {
			Statement anweisung = connection.createStatement();
			anweisung.execute(erstelleKategorieTabelle);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		System.out.println("Kategorien erstellt");
		System.out.println("----------------------------------------------------------------");
		System.out.println("Erstelle Gericht-Tabelle");
		System.out.println("----------------------------------------------------------------");
		final String erstelleGerichtTabelle = "CREATE TABLE IF NOT EXISTS gerichte (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(255) NOT NULL, beschreibung VARCHAR(255) NOT NULL, aufwand int NOT NULL);";
		try {
			Statement anweisung = connection.createStatement();
			anweisung.execute(erstelleGerichtTabelle);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		System.out.println("Gerichte erstellt");
		System.out.println("----------------------------------------------------------------");
		
		System.out.println("----------------------------------------------------------------");
		System.out.println("Erstelle Einheiten-Tabelle");
		final String erstelleEinheitenTabelle = "CREATE TABLE IF NOT EXISTS einheiten (id INTEGER PRIMARY KEY AUTOINCREMENT, einheit VARCHAR(255) NOT NULL);";
		try {
			Statement anweisung = connection.createStatement();
			anweisung.execute(erstelleEinheitenTabelle);
		} catch (SQLException e) {
			e.printStackTrace();
		} 

		System.out.println("Einheiten-Tabelle erstellt");
		System.out.println("----------------------------------------------------------------");

		
		System.out.println("----------------------------------------------------------------");
		System.out.println("Erstelle Zutat-Tabelle");
		final String erstelleZutatenTabelle = "CREATE TABLE IF NOT EXISTS zutaten (id INTEGER PRIMARY KEY AUTOINCREMENT, bezeichnung VARCHAR(255) NOT NULL, menge int NOT NULL, einheit INTEGER, gericht INTEGER, FOREIGN KEY(einheit) REFERENCES einheiten(id), FOREIGN KEY(gericht) REFERENCES gerichte(id));";
		try {
			Statement anweisung = connection.createStatement();
			anweisung.execute(erstelleZutatenTabelle);
		} catch (SQLException e) {
			e.printStackTrace();
		} 

		System.out.println("Zutat-Tabelle erstellt");
		System.out.println("----------------------------------------------------------------");
		
	}
	public void erstelleTestDaten() {
		this.erstelleEinheiten();
		final String[] testDatenAnweisungen= new String[] {
				"INSERT INTO gerichte (name, beschreibung, aufwand) VALUES ('Seehecht mit Kräuter-Bohnen-Risotto', 'Rezept mit Schritt für Schritt Anleitung', 40 );",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Wenig Kalorien', (SELECT id FROM gerichte WHERE id=1));",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Seehecht', 250, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=1))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Risottoreis', 80, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=1))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Gemüsebrühe', 8, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=1))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Dill', 1, (SELECT id FROM einheiten WHERE einheit = 'Bund'), (SELECT id FROM gerichte WHERE id=1))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Petersilie', 1, (SELECT id FROM einheiten WHERE einheit = 'Bund'), (SELECT id FROM gerichte WHERE id=1))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Buschbohne', 1, (SELECT id FROM einheiten WHERE einheit = 'Bund'), (SELECT id FROM gerichte WHERE id=1))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Haselnüsse', 50, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=1))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Gewürmischung Hello Grünzeug', 1, (SELECT id FROM einheiten WHERE einheit = 'Packung'), (SELECT id FROM gerichte WHERE id=1))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('geriebener Hartkäse', 50, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=1))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('geriebener Hartkäse', 1, (SELECT id FROM einheiten WHERE einheit = 'Zehe'), (SELECT id FROM gerichte WHERE id=1))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Schalotte', 1, (SELECT id FROM einheiten WHERE einheit = 'Stück'), (SELECT id FROM gerichte WHERE id=1))"
		};
		for (String string : testDatenAnweisungen) {
			try {
				Statement anweisung = connection.createStatement();
				anweisung.execute(string);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		final String datenHinzu = "INSERT INTO BILDER (titel, grafik, gericht) VALUES('Seehecht', ?, (SELECT id FROM gerichte WHERE id=1));";
		try {
			PreparedStatement hinzuAnweisung = connection.prepareStatement(datenHinzu);
			hinzuAnweisung.setBytes(1, IOUtils.toByteArray(getClass().getClassLoader().getResource("gerichtBilder/seehecht.jpeg").toURI()));
			hinzuAnweisung.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	
	public void erstelleEinheiten() {
		for (Einheit einheit : Einheit.values()) {
			String einfuegenEinheit = "INSERT INTO einheiten (einheit) VALUES ('"+einheit.einheit+"')";
			try {
				Statement anweisung = connection.createStatement();
				anweisung.execute(einfuegenEinheit);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	public ResultSet fuehreAnweisungAus(String sqlAnweisung){
		Statement anweisung = null;
		try {
			anweisung = connection.createStatement();
			if(anweisung.execute(sqlAnweisung)) {
				return anweisung.getResultSet();
			}
		}catch(final SQLException e) {
			System.err.println("Ausführung der Anweisung: " + sqlAnweisung + " fehlgeschlagen.");
			e.printStackTrace();
		}
		return null;
	}


	


}
