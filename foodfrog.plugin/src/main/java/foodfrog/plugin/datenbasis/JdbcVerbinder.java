package foodfrog.plugin.datenbasis;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

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
			connection = DriverManager.getConnection("jdbc:sqlite"+ DB_PATH);
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
		this.erstelleDatenbank();
	}

	private void erstelleDatenbank() {
		System.out.println("Erstelle Bilder-Tabelle");
		final String erstelleBilder = "CREATE TABLE IF NOT EXISTS BILDER (id INT NOT NULL AUTO_INCREMENT, titel VARHCAR(255) NOT NULL, grafik BLOB NOT NULL, PRIMARY KEY (id))";
		final String datenHinzu = "INSERT INTO BILDER (Enpanadas mit Kaese, LOAD_FILE(" + getClass().getClassLoader().getResource("gerichtBilder/gericht1.jpeg").toString() +")";
		try {
			Statement anweisung = connection.createStatement();
			anweisung.executeQuery(erstelleBilder);
			anweisung.executeQuery(datenHinzu);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("----------------------------------------------------------------");
		
	}
	
	
	private ResultSet fuehreAnweisungAus(String sqlAnweisung){
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
