package foodfrog.kern;

public enum Wochentag {
	
	Montag("Montag"), Dienstag("Dienstag"), Mittwoch("Mittwoch"), Donnerstag("Donnerstag"), Freitag("Freitag"),
	Samstag("Samstag"), Sonntag("Sonntag");

	public String wochentag;

	Wochentag(String wochentag) {

		this.wochentag = wochentag;
	}

}
