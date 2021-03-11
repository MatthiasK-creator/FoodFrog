package foodfrog.adapter.beobachter.muster;

public interface Subjekt {
	public void meldeAn(Beobachter beobachter);
	
	public void meldeAb(Beobachter beobachter);
	
	public void benachrichtige();
	
}
