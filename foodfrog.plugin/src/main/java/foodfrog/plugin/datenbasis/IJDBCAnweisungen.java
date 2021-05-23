package foodfrog.plugin.datenbasis;

import java.sql.ResultSet;

public interface IJDBCAnweisungen {
	public void fuehreVorbereiteteAnweisungAus(String sqlAnweisung, byte[] daten);
	public ResultSet fuehreAnweisungAus(String sqlAnweisung);
}
