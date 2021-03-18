package foodfrog.kern;

public enum Einheit {
	
	g("g"), ml("ml"), kg("kg"), TL("TL"), EL("EL"), l("l"),
	Messerspitze("Messerspitze"), Tasse("Tasse"), Spritzer("Spritzer"), Becher("Becher"), Bund("Bund"), Dose("Dose"),
	Flasche("Flasche"), Glas("Glas"), Packung("Packung"), St�ck("St�ck"), Tropfen("Tropfen"), W�rfel("W�rfel"),
	Zehe("Zehe");

	public String einheit;

	Einheit(String einheit) {

		this.einheit = einheit;
	}

}
