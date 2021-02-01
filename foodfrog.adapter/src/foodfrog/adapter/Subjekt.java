package foodfrog.adapter;

public interface Subjekt {
	public void meldeAn(Beobachter beobachter);
	
	public void meldeAb(Beobachter beobachter);
	
	public void benachrichtige(Object o);
	
	public Object gibZustand();
}
