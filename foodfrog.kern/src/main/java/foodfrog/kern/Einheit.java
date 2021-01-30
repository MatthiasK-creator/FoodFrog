package foodfrog.kern;

public enum Einheit {
	
	GRAMM("g"), MILLILITER("ml"), KILOGRAMM("kg"), TEELOEFFEL("TL"), ESSLOEFFEL("EL"), LITER("l"),
	MESSERSPITZE("Messerspitze"), TASSE("Tasse"), SPRITZER("Spritzer"), BECHER("Becher"), BUND("Bund"), DOSE("Dose"),
	FLASCHE("Flasche"), GLAS("Glas"), PACKUNG("Packung"), STUECK("Stück"), TROPFEN("Tropfen"), WÜRFEL("Würfel"),
	ZEHE("Zehe");

	public String einheit;

	Einheit(String einheit) {

		this.einheit = einheit;
	}

}
