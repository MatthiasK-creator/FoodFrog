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
		if (connection != null)
			return;

		System.out.println("Datenbankverbindung wird aufgebaut...");
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
			if (!connection.isClosed()) {
				System.out.println("Datenbankverbindung hergestellt.");
			}

		} catch (SQLException e) {
			System.err.println("Datenbankverbindung fehlgeschlagen: ");
			e.printStackTrace();
		}
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					if (!connection.isClosed() && connection != null) {
						connection.close();
						if (connection.isClosed())
							System.out.println("Datenbankverbindung wird geschlossen");
					}
				} catch (final SQLException e) {
					e.printStackTrace();
				}
			}
		});
		this.pruefeObDatenbankExistiert();
	}

	private void pruefeObDatenbankExistiert() {
		try (ResultSet rs = fuehreAnweisungAus("SELECT Count(*) FROM sqlite_schema WHERE type='table' ORDER BY name")) {
			final int numberOfTables = rs.getInt(1);
			if (numberOfTables <= 0) {
				this.erstelleDatenbankSchema();
				this.erstelleTestDaten();
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}

	}
	
	public int holeIdVomErstelltenGericht() {
		String idAnweisung = "SELECT seq FROM sqlite_sequence WHERE NAME = \"gerichte\"";
		int id = -1;
		try {
			Statement anweisung = connection.createStatement();
			ResultSet idSet = anweisung.executeQuery(idAnweisung);
			id = idSet.getInt("seq");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
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
		final String[] testDatenAnweisungen = new String[] {
				// Gericht 1
				"INSERT INTO gerichte (name, beschreibung, aufwand) VALUES ('Seehecht mit Kräuter-Bohnen-Risotto', 'Rezept mit Schritt für Schritt Anleitung', 40 );",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Wenig Kalorien', (SELECT id FROM gerichte WHERE id=1));",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Fischgericht', (SELECT id FROM gerichte WHERE id=1));",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Seehecht', 250, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=1))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Risottoreis', 80, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=1))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Gemüsebrühe', 8, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=1))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Dill', 1, (SELECT id FROM einheiten WHERE einheit = 'Bund'), (SELECT id FROM gerichte WHERE id=1))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Petersilie', 1, (SELECT id FROM einheiten WHERE einheit = 'Bund'), (SELECT id FROM gerichte WHERE id=1))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Buschbohne', 1, (SELECT id FROM einheiten WHERE einheit = 'Bund'), (SELECT id FROM gerichte WHERE id=1))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Haselnüsse', 50, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=1))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Gewürmischung Hello Grünzeug', 1, (SELECT id FROM einheiten WHERE einheit = 'Packung'), (SELECT id FROM gerichte WHERE id=1))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('geriebener Hartkäse', 50, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=1))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Knoblauch', 1, (SELECT id FROM einheiten WHERE einheit = 'Zehe'), (SELECT id FROM gerichte WHERE id=1))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Schalotte', 1, (SELECT id FROM einheiten WHERE einheit = 'Stück'), (SELECT id FROM gerichte WHERE id=1))",

				// Gericht 2

				"INSERT INTO gerichte (name, beschreibung, aufwand) VALUES ('Spaghetti alla panna', 'Die Butter aufschäumen lassen, dann 100 ml Sahne zugeben und auf die Hälfte reduzieren. Mit Salz und geriebenem Muskat abschmecken. Die Spaghetti nach Packungsanweisung bissfest kochen. In einen Seiher abgießen, abtropfen lassen und zurück in den Topf geben. Die Sahne-Butter-Reduktion nun zu den Spaghetti geben, die restliche Sahne und der geriebene Parmesan zufügen. Gut vermischen.', 20 );",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Pastagericht', (SELECT id FROM gerichte WHERE id=2));",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Italienisch', (SELECT id FROM gerichte WHERE id=2));",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Butter', 50, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=2))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Sahne', 200, (SELECT id FROM einheiten WHERE einheit = 'ml'), (SELECT id FROM gerichte WHERE id=2))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Salz', 1, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=2))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('geriebener Muskat', 1, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=2))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('geriebener Parmesan', 75, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=2))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Spaghetti', 500, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=2))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Pfeffer', 1, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=2))",

				// Gericht 3

				"INSERT INTO gerichte (name, beschreibung, aufwand) VALUES ('Nudeln mit Räucherlachs und Ziegenkäse', 'Die Zwiebel sanft in Butter anbraten, salzen und pfeffern. Die Sahne angießen, den Käse hineinkrümeln und die Sauce zum Kochen bringen. Häufig umrühren und bei kleiner Flamme etwas eindicken lassen. Zitronenschale, 1 Spritzer Worcestersauce, einen EL Minze und die Hälfte der Lachsstreifen in die Sauce rühren. Nochmal mit Salz, Pfeffer und Worcestersauce abschmecken und ggf. noch getrocknete Minze dazugeben, da diese meistens noch einen intensiveren Geschmack hat als die frische. Die Nudeln in Salzwasser al dente kochen. Mit der Sauce in der Pfanne verrühren. Jede Portion mit einigen Lachsstreifen und frischer Minze garnieren.', 15 );",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Pastagericht', (SELECT id FROM gerichte WHERE id=3));",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Fischgericht', (SELECT id FROM gerichte WHERE id=3));",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Butter', 2, (SELECT id FROM einheiten WHERE einheit = 'EL'), (SELECT id FROM gerichte WHERE id=3))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Sahne', 200, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=3))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Ziegenfrischkäse', 150, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=3))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('geriebener Zitronenschale', 1, (SELECT id FROM einheiten WHERE einheit = 'EL'), (SELECT id FROM gerichte WHERE id=3))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Minze', 2, (SELECT id FROM einheiten WHERE einheit = 'EL'), (SELECT id FROM gerichte WHERE id=3))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Lachs', 200, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=3))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Nudeln', 400, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=3))",

				// Gericht 4

				"INSERT INTO gerichte (name, beschreibung, aufwand) VALUES ('Zucchini-Zitronen-Nudeln', 'Die Nudeln nach Packungsanweisung bissfest kochen. Die Zucchini in lange dünne, ca. 2 cm breite Streifen schneiden und in einer großen Pfanne im erhitzten Öl so lange braten, bis sie gebräunt sind. Abgeriebene Zitronenschale, gehackten oder gepressten Knoblauch und die gehackte Petersilie zugeben, kurz weiter braten. Die abgetropften Nudeln unter die Zucchini mischen. Den Parmesan bzw. den Pecorino unterrühren und mit Salz, Pfeffer und Zitronensaft abschmecken.', 20 );",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Pastagericht', (SELECT id FROM gerichte WHERE id=4));",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Vegetarisch', (SELECT id FROM gerichte WHERE id=4));",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Petersilie', 1, (SELECT id FROM einheiten WHERE einheit = 'Bund'), (SELECT id FROM gerichte WHERE id=4))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Zitrone', 1, (SELECT id FROM einheiten WHERE einheit = 'Stück'), (SELECT id FROM gerichte WHERE id=4))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Knoblauch', 2, (SELECT id FROM einheiten WHERE einheit = 'Zehe'), (SELECT id FROM gerichte WHERE id=4))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Minze', 2, (SELECT id FROM einheiten WHERE einheit = 'EL'), (SELECT id FROM gerichte WHERE id=4))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Zucchini', 500, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=4))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Nudeln', 500, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=4))",

				// Gericht 5

				"INSERT INTO gerichte (name, beschreibung, aufwand) VALUES ('Kartoffel-Gemüse-Fleischwurst-Eintopf', 'Kartoffeln schälen, waschen, in Würfel schneiden und normal kochen. Das Gemüse nach Packungsanweisung kochen. Nach Ende der Kochzeit noch etwas Wasser hinzufügen, so dass das Gemüse gut mit Wasser bedeckt ist. Jetzt mit Salz, Pfeffer und gekörnter Brühe nach Geschmack würzen. Die Käseecke zugeben und schmelzen lassen. Die Fleischwurst in kleine Würfel schneiden und ebenfalls hinzufügen. Nach Ende der Kochzeit die Kartoffeln abkippen und gleichfalls hinzugeben. Zum Schluss das Ganze mit Mehlschwitze andicken. Am Ende kann man das Ganze noch mit Petersilie bestreuen.', 20 );",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Eintopf', (SELECT id FROM gerichte WHERE id=5));",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Kartoffeln', 3, (SELECT id FROM einheiten WHERE einheit = 'Stück'), (SELECT id FROM gerichte WHERE id=5))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Kaisergemüse', 1, (SELECT id FROM einheiten WHERE einheit = 'Packung'), (SELECT id FROM gerichte WHERE id=5))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Fleischwurst', 1, (SELECT id FROM einheiten WHERE einheit = 'Stück'), (SELECT id FROM gerichte WHERE id=5))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Schmelzkäse', 100, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=5))",

				// Gericht 6

				"INSERT INTO gerichte (name, beschreibung, aufwand) VALUES ('Polentasuppe pikant', 'Zwiebeln, Knoblauch und die Chilischote kleinschneiden und in wenig Butter alles andämpfen. Mit der Gemüsebrühe aufgießen und zum Kochen bringen. Die Polenta einrieseln lassen und köcheln lassen, bis die Polenta gar ist. Immer wieder durchrühren damit die Polenta nicht anbrennt. Am Schluss einen Schuss Milch dazugeben und mit Salz abschmecken.', 15 );",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Suppe', (SELECT id FROM gerichte WHERE id=6));",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Vegetarisch', (SELECT id FROM gerichte WHERE id=6));",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Gemüsebrühe', 1, (SELECT id FROM einheiten WHERE einheit = 'l'), (SELECT id FROM gerichte WHERE id=6))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Zwiebel klein', 1, (SELECT id FROM einheiten WHERE einheit = 'Stück'), (SELECT id FROM gerichte WHERE id=6))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Knoblauch', 1, (SELECT id FROM einheiten WHERE einheit = 'Zehe'), (SELECT id FROM gerichte WHERE id=6))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Butter', 1, (SELECT id FROM einheiten WHERE einheit = 'EL'), (SELECT id FROM gerichte WHERE id=6))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Chilischote klein', 1, (SELECT id FROM einheiten WHERE einheit = 'Stück'), (SELECT id FROM gerichte WHERE id=6))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Milch', 3, (SELECT id FROM einheiten WHERE einheit = 'EL'), (SELECT id FROM gerichte WHERE id=6))",

				// Gericht 7

				"INSERT INTO gerichte (name, beschreibung, aufwand) VALUES ('Schinken - Nudel - Auflauf', 'Spiralnudeln nach Anleitung auf der Packung kochen, abgießen, abschrecken und gut abtropfen lassen. Abwechselnd mit Käse und dem kleingeschnittenen Schinken oder dem Selchfleisch in eine gut befettete und mit Semmelbröseln ausgestreute Auflaufform schichten. Eier mit Rahm, Salz und Muskat verquirlen. Das Ganze über den Auflauf gießen, Brösel und Butterflocken oben draufgeben, anschließend etwa 20-30 Minuten im vorgeheizten Backofen bei 200 Grad backen.', 30 );",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Auflauf', (SELECT id FROM gerichte WHERE id=7));",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Pastagericht', (SELECT id FROM gerichte WHERE id=7));",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Eier', 3, (SELECT id FROM einheiten WHERE einheit = 'Stück'), (SELECT id FROM gerichte WHERE id=7))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Schinken', 250, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=7))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('geriebener Käse', 100, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=7))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Rahm / Schmand', 250, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=7))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Nudeln', 330, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=7))",

				// Gericht 8

				"INSERT INTO gerichte (name, beschreibung, aufwand) VALUES ('Lauch-Kartoffel-Suppe', 'Das Gemüse klein schneiden und in etwas Öl anbraten. Die Cashewnüsse im Mixer fein mahlen (oder vorher einweichen und mit dem Pürierstab und etwas Wasser pürieren), mit ca. 3/4 l Wasser vermischen. Die entstandene Cashewsahne über das Gemüse geben. Die Gewürze hinzugeben und ca. 10 min. köcheln lassen, bis die Kartoffeln gar sind. Nach Vorliebe weiter abschmecken (z. B. mit etwas Senf, für noch käsigeren Geschmack), evtl. noch Wasser hinzufügen.', 20 );",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Suppe', (SELECT id FROM gerichte WHERE id=8));",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Vegan', (SELECT id FROM gerichte WHERE id=8));",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Zwiebeln', 1, (SELECT id FROM einheiten WHERE einheit = 'Stück'), (SELECT id FROM gerichte WHERE id=8))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Möhre', 1, (SELECT id FROM einheiten WHERE einheit = 'Stück'), (SELECT id FROM gerichte WHERE id=8))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Lauchstange', 2, (SELECT id FROM einheiten WHERE einheit = 'Stück'), (SELECT id FROM gerichte WHERE id=8))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Cashewnüsse', 100, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=8))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Haferflocken', 1, (SELECT id FROM einheiten WHERE einheit = 'EL'), (SELECT id FROM gerichte WHERE id=8))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Kurkuma', 2, (SELECT id FROM einheiten WHERE einheit = 'TL'), (SELECT id FROM gerichte WHERE id=8))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Gemüsebrühe', 1, (SELECT id FROM einheiten WHERE einheit = 'EL'), (SELECT id FROM gerichte WHERE id=8))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Öl', 1, (SELECT id FROM einheiten WHERE einheit = 'EL'), (SELECT id FROM gerichte WHERE id=8))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Senf', 1, (SELECT id FROM einheiten WHERE einheit = 'EL'), (SELECT id FROM gerichte WHERE id=8))",

				// Gericht 9

				"INSERT INTO gerichte (name, beschreibung, aufwand) VALUES ('Kartoffelpuffer mit Speck und Käse', 'Die Kartoffeln und die Zwiebel schälen und reiben. Den Speck würfeln. Käse mit einer groben Reibe reiben. Die Eier, den gewürfelten Speck und Käse unter die Kartoffelmasse rühren und alles mit Salz und Pfeffer abschmecken. In heißem Öl ausbacken.', 30 );",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Kartoffelgericht', (SELECT id FROM gerichte WHERE id=9));",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Kartoffel', 1, (SELECT id FROM einheiten WHERE einheit = 'kg'), (SELECT id FROM gerichte WHERE id=9))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Eier', 3, (SELECT id FROM einheiten WHERE einheit = 'Stück'), (SELECT id FROM gerichte WHERE id=9))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Zwiebel', 1, (SELECT id FROM einheiten WHERE einheit = 'Stück'), (SELECT id FROM gerichte WHERE id=9))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Öl', 2, (SELECT id FROM einheiten WHERE einheit = 'EL'), (SELECT id FROM gerichte WHERE id=9))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Speck', 250, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=9))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Gouda', 250, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=9))",

				// Gericht 10

				"INSERT INTO gerichte (name, beschreibung, aufwand) VALUES ('Riso di Napoli', 'Die Tomaten einritzen, überbrühen, häuten und in Scheiben schneiden. Im Winter nehme ich alternativ eine Dose stückige Tomaten und verwende etwas weniger Fleischbrühe. Die Zwiebel schälen, fein würfeln und in einer beschichteten Pfanne im Olivenöl goldbraun anbraten. Den rohen Reis einrühren und glasig anrösten. Die Tomaten zugeben und mit der Brühe aufgießen. Die Pfanne mit einem Deckel schließen und den Reis bei geringer Wärmezufuhr in 20 - 25 min garen. Ab und zu umrühren und bei Bedarf ggf. noch etwas Fleischbrühe nachgießen. Der Reis sollte wirklich gar sein - deshalb ggf. die Garzeit nach Bedarf verlängern. Vor dem Servieren mit Salz, Pfeffer und ggf. Oregano abschmecken und mit dem geriebenen Käse bestreuen. Die Flüssigkeit sollte fast vollständig verkocht bzw. vom Reis aufgesaugt sein.', 15 );",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Reisgericht', (SELECT id FROM gerichte WHERE id=10));",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Vegetarisch', (SELECT id FROM gerichte WHERE id=10));",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Italienisch', (SELECT id FROM gerichte WHERE id=10));",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Zwiebeln', 1, (SELECT id FROM einheiten WHERE einheit = 'Stück'), (SELECT id FROM gerichte WHERE id=10))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Fleischtomaten', 2, (SELECT id FROM einheiten WHERE einheit = 'Stück'), (SELECT id FROM gerichte WHERE id=10))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Öl', 2, (SELECT id FROM einheiten WHERE einheit = 'EL'), (SELECT id FROM gerichte WHERE id=10))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Gouda', 100, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=10))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Reis', 180, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=10))",

				// Gericht 11

				"INSERT INTO gerichte (name, beschreibung, aufwand) VALUES ('Cous Cous - Salat', 'Cous Cous in eine große Schüssel geben. Wasser, Kräuterwürfel, Tomatenmark und Gemüsebrühe über den Cous Cous gießen. Das ganze 5 Minuten quellen lassen. In der Zwischenzeit das Gemüse würfeln und anschließend zum Cous Cous geben. Lauchzwiebeln in Ringe dazu. Den Salat mit Essig, Öl, Salz, frischem Pfeffer und Salatkräuter abschmecken. Nach Belieben gewürfelten Schafskäse oder den milderen Feta unterheben.', 20 );",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Salat', (SELECT id FROM gerichte WHERE id=11));",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Vegetarisch', (SELECT id FROM gerichte WHERE id=11));",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Orientalisch', (SELECT id FROM gerichte WHERE id=11));",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Paprikaschote', 1, (SELECT id FROM einheiten WHERE einheit = 'Stück'), (SELECT id FROM gerichte WHERE id=11))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Tomaten gewürfelt', 1, (SELECT id FROM einheiten WHERE einheit = 'Stück'), (SELECT id FROM gerichte WHERE id=11))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Knoblauch', 2, (SELECT id FROM einheiten WHERE einheit = 'Zehe'), (SELECT id FROM gerichte WHERE id=11))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Tomatenmark', 2, (SELECT id FROM einheiten WHERE einheit = 'EL'), (SELECT id FROM gerichte WHERE id=11))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Gemüsebrühe', 2, (SELECT id FROM einheiten WHERE einheit = 'EL'), (SELECT id FROM gerichte WHERE id=11))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Salatgurke', 1, (SELECT id FROM einheiten WHERE einheit = 'Stück'), (SELECT id FROM gerichte WHERE id=11))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Couscous', 500, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=11))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Wasser', 600, (SELECT id FROM einheiten WHERE einheit = 'ml'), (SELECT id FROM gerichte WHERE id=11))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Lauchzwiebeln', 1, (SELECT id FROM einheiten WHERE einheit = 'Bund'), (SELECT id FROM gerichte WHERE id=11))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Essig', 1, (SELECT id FROM einheiten WHERE einheit = 'EL'), (SELECT id FROM gerichte WHERE id=11))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Öl', 1, (SELECT id FROM einheiten WHERE einheit = 'EL'), (SELECT id FROM gerichte WHERE id=11))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Schafskäse', 200, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=11))",

				// Gericht 12

				"INSERT INTO gerichte (name, beschreibung, aufwand) VALUES ('Arabisches Hähnchen 1001 Nacht', 'Hähnchen waschen, trocken tupfen und innen und außen salzen und pfeffern. Für die Füllung die Hälfte des Öls erhitzen und gehackte Zwiebeln glasig dünsten, Reis zugeben, mit der Brühe ablöschen. Flüssigkeit einziehen lassen bis sich an der Oberfläche kleine Löcher bilden. Gewaschene Rosinen, Pinienkerne und gehackte Petersilie untermengen. Hähnchen füllen und mit Zwirn oder Rouladennadeln verschließen. In eine feuerfeste Form geben, Brühe und etwas Weißwein angießen. Mit restlichem Öl bepinseln und in den auf 250 Grad vorgeheizten Backofen schieben. Auf 180 - 200 Grad reduzieren und immer wieder mit Brühe bepinseln. Nach ca. 3/4 Std. prüfen ob das Fleisch gar ist. Dann den Reis, der vielleicht nicht mehr in den Vogel reingegangen ist, um das Hähnchen legen und Hähnchen mit Honig bepinseln. Im Ofen bräunen lassen (aber dabei bleiben, damit aus dem Vogel kein Rabe wird!)', 20 );",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Arabisch', (SELECT id FROM gerichte WHERE id=12));",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Reisgericht', (SELECT id FROM gerichte WHERE id=12));",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Hähnchen groß', 1, (SELECT id FROM einheiten WHERE einheit = 'Stück'), (SELECT id FROM gerichte WHERE id=12))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Öl', 1, (SELECT id FROM einheiten WHERE einheit = 'Tasse'), (SELECT id FROM gerichte WHERE id=12))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Zwiebel', 2, (SELECT id FROM einheiten WHERE einheit = 'Stück'), (SELECT id FROM gerichte WHERE id=12))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Gemüsebrühe', 2, (SELECT id FROM einheiten WHERE einheit = 'Tasse'), (SELECT id FROM gerichte WHERE id=12))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Weißwein', 1, (SELECT id FROM einheiten WHERE einheit = 'Tasse'), (SELECT id FROM gerichte WHERE id=12))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Rosinen', 50, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=12))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Pinienkerne', 50, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=12))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Petersilie', 1, (SELECT id FROM einheiten WHERE einheit = 'Bund'), (SELECT id FROM gerichte WHERE id=12))",

				// Gericht 13

				"INSERT INTO gerichte (name, beschreibung, aufwand) VALUES ('Spargelsalat italienische Art', 'Spargel waschen, großzügig schälen, die holzigen Enden abschneiden, in mundgerechte Stücke schneiden. Wasser in einem großen Topf aufkochen, salzen. Den Spargel dazugeben und bei schwacher Hitze in etwa 12 Min. gar, aber nicht zu weich kochen. Herausnehmen und abkühlen lassen. trocknete Tomaten abtropfen lassen und fein würfeln. Pinienkerne ohne Fett in einer Pfanne leicht anrösten. Aceto Balsamico, Senf und Öl vermengen und mit Salz und Pfeffer abschmecken. Die getrockneten Tomaten hinzugeben. Die Mischung gleichmäßig über den Spargel geben und mindestens eine Stunde marinieren lassen. Die Kirsch-/Cocktailtomaten vierteln oder halbieren und über den Spargel geben, ebenso die Pinienkerne und den grob gehobelten Parmesan. Den Salat vermengen und eventuell noch etwas Pfeffer hinzufügen.', 30 );",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Italienisch', (SELECT id FROM gerichte WHERE id=13));",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Vegetarisch', (SELECT id FROM gerichte WHERE id=13));",
				"INSERT INTO kategorien (bezeichnung, gericht) VALUES ('Salat', (SELECT id FROM gerichte WHERE id=13));",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Spargel weiß oder grün', 1, (SELECT id FROM einheiten WHERE einheit = 'kg'), (SELECT id FROM gerichte WHERE id=13))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Tomaten', 4, (SELECT id FROM einheiten WHERE einheit = 'Stück'), (SELECT id FROM gerichte WHERE id=13))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Aceto Balsamico', 3, (SELECT id FROM einheiten WHERE einheit = 'EL'), (SELECT id FROM gerichte WHERE id=13))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Dijonsenf', 1, (SELECT id FROM einheiten WHERE einheit = 'EL'), (SELECT id FROM gerichte WHERE id=13))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Cocktailtomanten', 200, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=13))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Parmesan', 50, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=13))",
				"INSERT INTO zutaten (bezeichnung, menge, einheit, gericht) VALUES ('Pinienkerne', 30, (SELECT id FROM einheiten WHERE einheit = 'g'), (SELECT id FROM gerichte WHERE id=13))",

		};
		for (String string : testDatenAnweisungen) {
			try {
				Statement anweisung = connection.createStatement();
				anweisung.execute(string);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		final String[] bilderAnweisungen = new String[] {
				"INSERT INTO BILDER (titel, grafik, gericht) VALUES('Seehecht', ?, (SELECT id FROM gerichte WHERE id=1));",
				"INSERT INTO BILDER (titel, grafik, gericht) VALUES('Spaghetti alla panna', ?, (SELECT id FROM gerichte WHERE id=2));",
				"INSERT INTO BILDER (titel, grafik, gericht) VALUES('Nudeln mit Räucherlachs und Ziegenkäse', ?, (SELECT id FROM gerichte WHERE id=3));",
				"INSERT INTO BILDER (titel, grafik, gericht) VALUES('Zucchini-Zitronen-Nudeln', ?, (SELECT id FROM gerichte WHERE id=4));",
				"INSERT INTO BILDER (titel, grafik, gericht) VALUES('Kartoffel-Gemüse-Fleischwurst-Eintopf', ?, (SELECT id FROM gerichte WHERE id=5));",
				"INSERT INTO BILDER (titel, grafik, gericht) VALUES('Polentasuppe pikant', ?, (SELECT id FROM gerichte WHERE id=6));",
				"INSERT INTO BILDER (titel, grafik, gericht) VALUES('Schinken - Nudel - Auflauf', ?, (SELECT id FROM gerichte WHERE id=7));",
				"INSERT INTO BILDER (titel, grafik, gericht) VALUES('Lauch-Kartoffel-Suppe', ?, (SELECT id FROM gerichte WHERE id=8));",
				"INSERT INTO BILDER (titel, grafik, gericht) VALUES('Kartoffelpuffer mit Speck und Käse', ?, (SELECT id FROM gerichte WHERE id=9));",
				"INSERT INTO BILDER (titel, grafik, gericht) VALUES('Riso di Napoli', ?, (SELECT id FROM gerichte WHERE id=10));",
				"INSERT INTO BILDER (titel, grafik, gericht) VALUES('Cous Cous - Salat', ?, (SELECT id FROM gerichte WHERE id=11));",
				"INSERT INTO BILDER (titel, grafik, gericht) VALUES('Arabisches Hähnchen 1001 Nacht', ?, (SELECT id FROM gerichte WHERE id=12));",
				"INSERT INTO BILDER (titel, grafik, gericht) VALUES('Spargelsalat italienische Art', ?, (SELECT id FROM gerichte WHERE id=13));"};
		try {
			int zaehler = 1;
			for (int i = 0; i < bilderAnweisungen.length; i++) {
				this.fuehreVorbereiteteAnweisungAus(bilderAnweisungen[i], IOUtils.toByteArray(getClass().getClassLoader().getResource("gerichtBilder/"+zaehler+".jpeg").toURI()));
				zaehler++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public void erstelleEinheiten() {
		for (Einheit einheit : Einheit.values()) {
			String einfuegenEinheit = "INSERT INTO einheiten (einheit) VALUES ('" + einheit.einheit + "')";
			try {
				Statement anweisung = connection.createStatement();
				anweisung.execute(einfuegenEinheit);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void fuehreVorbereiteteAnweisungAus(String sqlAnweisung, byte[] daten) {
		try {
			PreparedStatement hinzuAnweisung = connection.prepareStatement(sqlAnweisung);
			hinzuAnweisung.setBytes(1, daten);
			hinzuAnweisung.execute();
		} catch (final SQLException e) {
			System.err.println("Ausführung der Anweisung: " + sqlAnweisung + " fehlgeschlagen.");
			e.printStackTrace();
		}
	}
	

	public ResultSet fuehreAnweisungAus(String sqlAnweisung) {
		Statement anweisung = null;
		try {
			anweisung = connection.createStatement();
			if (anweisung.execute(sqlAnweisung)) {
				return anweisung.getResultSet();
			}
		} catch (final SQLException e) {
			System.err.println("Ausführung der Anweisung: " + sqlAnweisung + " fehlgeschlagen.");
			e.printStackTrace();
		}
		return null;
	}

}
