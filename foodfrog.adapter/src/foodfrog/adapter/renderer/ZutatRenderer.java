package foodfrog.adapter.renderer;

import foodfrog.kern.Zutat;

public class ZutatRenderer {

	private static ZutatRenderer zutatRenderer = new ZutatRenderer();
	
	private ZutatRenderer() {
	}
	
	public static ZutatRenderer holeInstanz() {
		return zutatRenderer;
	}
	
	public String renderZutat(Zutat zutat){
		return zutat.getMenge() + " " + zutat.getEinheit().einheit + " " +zutat.getBezeichnung();
	}

}
