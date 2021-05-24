package foodfrog.plugin.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import foodfrog.applikation.EntiaetVerwalter;
import foodfrog.applikation.Gerichtverwaltung;
import foodfrog.applikation.Wochenplanverwaltung;
import foodfrog.kern.Bild;
import foodfrog.kern.Einheit;
import foodfrog.kern.Gericht;
import foodfrog.kern.Kategorie;
import foodfrog.kern.Zutat;

class WochenplanVerwaltungTest {
	private EntiaetVerwalter verwalter;
	private Gericht gericht;
	private Wochenplanverwaltung verwaltung;
	private Zutat zutat;

	@BeforeEach
	void richteEin() throws Exception {
		verwalter = EasyMock.createMock(EntiaetVerwalter.class);
		verwaltung = new Wochenplanverwaltung(verwalter);
		gericht = new Gericht(1, "Spaghetti Cabonara", "Nudeln ins Wasser und leckere Cabonora-Soße kochen", 60);
		zutat = new Zutat(1, "Spaghetti Nudeln", 500);
		zutat.setEinheit(Einheit.g);
		gericht.setZutaten(Arrays.asList(zutat));
		gericht.setKategorien(Arrays.asList(new Kategorie(1, "vegetarisch")));
		gericht.setBilder(Arrays.asList(new Bild(1, "Testbild", "Test".getBytes())));
	}

	@Test
	void holeZufaelligMitFilter() {
		List<Kategorie> kategorieListe = Arrays.asList(new Kategorie(1, "vegetarisch"));
		gericht.setKategorien(kategorieListe);
		EasyMock.expect(verwalter.holeZufaelligMitFilter(1, kategorieListe)).andReturn(Arrays.asList(gericht));
		EasyMock.replay(verwalter);

		List<Gericht> gefilterteGerichte = verwaltung.generiereWochenplan(1, kategorieListe);
		
		assertEquals(gefilterteGerichte.size(), 1);
		assertEquals(gefilterteGerichte.get(0).getName(), "Spaghetti Cabonara");
		assertEquals(gefilterteGerichte.get(0).getBeschreibung(), "Nudeln ins Wasser und leckere Cabonora-Soße kochen");
		assertEquals(gefilterteGerichte.get(0).getAufwand(), 60);
		assertEquals(gefilterteGerichte.get(0).getKategorien().get(0).getId(), 1);
		assertEquals(gefilterteGerichte.get(0).getKategorien().get(0).getBezeichnung(), "vegetarisch");
		assertEquals(gefilterteGerichte.get(0).getZutaten().get(0).getId(), 1);
		assertEquals(gefilterteGerichte.get(0).getZutaten().get(0).getBezeichnung(), "Spaghetti Nudeln");
		assertEquals(gefilterteGerichte.get(0).getZutaten().get(0).getMenge(), 500);
		assertEquals(gefilterteGerichte.get(0).getZutaten().get(0).getEinheit(), Einheit.g);
		assertEquals(gefilterteGerichte.get(0).getBilder().get(0).getId(), 1);
		assertEquals(gefilterteGerichte.get(0).getBilder().get(0).getTitel(), "Testbild");
		assertArrayEquals(gefilterteGerichte.get(0).getBilder().get(0).getGrafik(), "Test".getBytes());
		
		EasyMock.verify(verwalter);
		
	}
	
	/**
	 * Doppelter Test. Siehe Dokumentation für Erklärung.
	 */
	@Test
	void holeAlleKategorien() {
		EasyMock.expect(verwalter.holeAlle(Kategorie.class)).andReturn(Arrays.asList(new Kategorie(1, "vegetarisch")));
		EasyMock.replay(verwalter);
		
		ArrayList<Kategorie> kategorien = this.verwaltung.holeAlleKategorien();
		
		assertEquals(kategorien.size(), 1);
		assertEquals(kategorien.get(0).getId(), 1);
		assertEquals(kategorien.get(0).getBezeichnung(), "vegetarisch");
		EasyMock.verify(verwalter);
	}
	
	
	@Test
	void fuegeGerichtHinzuErfolgreich() {
		EasyMock.expect(verwalter.holeZufaellig(Gericht.class)).andReturn(this.gericht);
		EasyMock.replay(verwalter);

		
		this.verwaltung.gerichtHinzufuegen(gericht);
		assertEquals(this.verwaltung.getWochenplan().size(), 1);
		assertEquals(this.verwaltung.getWochenplan().get(0).getName(), gericht.getName());
		assertEquals(this.verwaltung.getWochenplan().get(0).getBeschreibung(), gericht.getBeschreibung());
		assertEquals(this.verwaltung.getWochenplan().get(0).getAufwand(), gericht.getAufwand());
		EasyMock.verify(verwalter);
	}
	
	
	
	@Test
	void loescheGerichtErfolgreich() {
		EasyMock.expect(verwalter.holeZufaellig(Gericht.class)).andReturn(this.gericht);
		EasyMock.replay(verwalter);

		
		this.verwaltung.gerichtHinzufuegen(gericht);
		assertEquals(this.verwaltung.getWochenplan().size(), 1);
		boolean b = this.verwaltung.gerichtLoeschen(gericht);
		assertEquals(b, true);
		assertEquals(this.verwaltung.getWochenplan().size(), 0);
		EasyMock.verify(verwalter);
	}
	
	@Test
	void loescheGerichtMisserfolg() {
		EasyMock.expect(verwalter.holeZufaellig(Gericht.class)).andReturn(this.gericht);
		EasyMock.replay(verwalter);
		
		this.verwaltung.gerichtHinzufuegen(gericht);
		assertEquals(this.verwaltung.getWochenplan().size(), 1);
		boolean b = this.verwaltung.gerichtLoeschen(new Gericht("Gibt", "Es nicht", 45));
		assertEquals(b, false);
		assertEquals(this.verwaltung.getWochenplan().size(), 1);
		EasyMock.verify(verwalter);
	}
	
	@Test
	void holeNeuesGerichtBereitsVorhandenesGericht() {
		EasyMock.expect(verwalter.holeZufaellig(Gericht.class)).andReturn(this.gericht);
		EasyMock.expect(verwalter.holeZufaellig(Gericht.class)).andReturn(this.gericht);
		EasyMock.expect(verwalter.holeZufaellig(Gericht.class)).andReturn(new Gericht("Neues", "Gericht", 45));
		EasyMock.replay(verwalter);
		
		this.verwaltung.gerichtHinzufuegen(gericht);
		this.verwaltung.gerichtHinzufuegen(gericht);
		assertEquals(this.verwaltung.getWochenplan().size(), 2);
		assertEquals(this.verwaltung.getWochenplan().get(1).getName(), "Neues");
		assertEquals(this.verwaltung.getWochenplan().get(1).getBeschreibung(), "Gericht");
		assertEquals(this.verwaltung.getWochenplan().get(1).getAufwand(), 45);
		
		EasyMock.verify(verwalter);
	}
	
	@Test
	void generiereEinkaufsliste() {
		EasyMock.expect(verwalter.holeZufaellig(Gericht.class)).andReturn(this.gericht);
		EasyMock.replay(verwalter);
		this.verwaltung.gerichtHinzufuegen(gericht);
		
		HashMap<String, List<Zutat>> zutatenListe = this.verwaltung.generiereEinkaufsliste();

		assertEquals(zutatenListe.get(gericht.getName()).size(), 1);
		assertEquals(zutatenListe.get(gericht.getName()).get(0).getId(), 1);
		assertEquals(zutatenListe.get(gericht.getName()).get(0).getBezeichnung(), zutat.getBezeichnung());
		assertEquals(zutatenListe.get(gericht.getName()).get(0).getMenge(), 500);
		assertEquals(zutatenListe.get(gericht.getName()).get(0).getEinheit(), Einheit.g);
		
		
	}







}
