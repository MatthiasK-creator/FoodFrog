package foodfrog.plugin.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import foodfrog.applikation.EntiaetVerwalter;
import foodfrog.applikation.Gerichtverwaltung;
import foodfrog.applikation.Wochenplanverwaltung;
import foodfrog.kern.Bild;
import foodfrog.kern.Einheit;
import foodfrog.kern.Entitaet;
import foodfrog.kern.Gericht;
import foodfrog.kern.Kategorie;
import foodfrog.kern.Zutat;

class GerichtVerwaltungTest {
	private Gerichtverwaltung verwaltung;
	private EntiaetVerwalter verwalter;
	private Gericht gericht;
	
	
	@BeforeEach
	void richteEin() throws Exception {
		verwalter = EasyMock.createMock(EntiaetVerwalter.class);
		verwaltung = new Gerichtverwaltung(verwalter);
		gericht = new Gericht(1, "Spaghetti Cabonara", "Nudeln ins Wasser und leckere Cabonora-Soße kochen", 60);
		Zutat zutat = new Zutat(1, "Spaghetti Nudeln", 500);
		zutat.setEinheit(Einheit.g);
		gericht.setZutaten(Arrays.asList(zutat));
		gericht.setKategorien(Arrays.asList(new Kategorie(1, "vegetarisch")));
		gericht.setBilder(Arrays.asList(new Bild(1, "Testbild", "Test".getBytes())));

	}
	
	@Test
	void holeZufaelligMitFilter() {
		verwalter = EasyMock.createMock(EntiaetVerwalter.class);

		
		List<Kategorie> kategorieListe = Arrays.asList(new Kategorie(1, "vegetarisch"));
		gericht.setKategorien(kategorieListe);
		EasyMock.expect(verwalter.holeZufaelligMitFilter(1, kategorieListe)).andReturn(Arrays.asList(gericht));
		EasyMock.replay(verwalter);

		Wochenplanverwaltung wochenplanVerwaltung = new Wochenplanverwaltung(verwalter);
		List<Gericht> gefilterteGerichte = wochenplanVerwaltung.generiereWochenplan(1, kategorieListe);
		
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
	
	@Test
	void holeZufaellig() {
		// Auf Mock auslagern
		EasyMock.expect(verwalter.holeZufaellig(Gericht.class)).andReturn(gericht);
		EasyMock.replay(verwalter);
		
		Gericht geholtesGericht = (Gericht) verwalter.holeZufaellig(Gericht.class);

		
		assertEquals(geholtesGericht.getId(), 1);
		assertEquals(geholtesGericht.getName(), "Spaghetti Cabonara");
		assertEquals(geholtesGericht.getBeschreibung(), "Nudeln ins Wasser und leckere Cabonora-Soße kochen");
		assertEquals(geholtesGericht.getAufwand(), 60);

	}
	
	@Test
	void holeGerichtMitId() {
		EasyMock.expect(verwalter.hole(Gericht.class, 1)).andReturn(gericht);
		EasyMock.replay(verwalter);
		
		Gericht geholtesGericht = this.verwaltung.holeGericht(1);
		
		assertEquals(gericht.getId(), geholtesGericht.getId());
		assertEquals(gericht.getName(), geholtesGericht.getName());
		assertEquals(gericht.getBeschreibung(), geholtesGericht.getBeschreibung());
		assertEquals(gericht.getAufwand(), geholtesGericht.getAufwand());
	}
	
	@Test
	void aendereGericht() {
		EasyMock.expect(verwalter.aendere(Gericht.class, 1, gericht)).andReturn(gericht);
		EasyMock.replay(verwalter);

		Gericht aenderungsGericht = verwaltung.aendereGericht(1, gericht);
		assertEquals(gericht.getId(), aenderungsGericht.getId());
		assertEquals(gericht.getName(), aenderungsGericht.getName());
		assertEquals(gericht.getBeschreibung(), aenderungsGericht.getBeschreibung());
		assertEquals(gericht.getAufwand(), aenderungsGericht.getAufwand());
		EasyMock.verify(verwalter);
	}

	@Test
	void loescheGerichtMitMissErfolg() {
		EasyMock.expect(verwalter.loesche(Gericht.class, 5)).andReturn(false);
		EasyMock.replay(verwalter);

		boolean misserfolg = verwaltung.loescheGericht(Gericht.class, 5);
		assertFalse(misserfolg);
		EasyMock.verify(verwalter);
	}

	@Test
	void loescheGerichtMitErfolg() {
		EasyMock.expect(verwalter.loesche(Gericht.class, 1)).andReturn(true);
		EasyMock.replay(verwalter);

		boolean erfolgreich = verwaltung.loescheGericht(Gericht.class, 1);
		assertTrue(erfolgreich);
		EasyMock.verify(verwalter);
	}
	
	@Test
	void erstelleGericht() {
		EasyMock.expect(verwalter.erstellle(Gericht.class, gericht)).andReturn(gericht);
		EasyMock.replay(verwalter);
		
		Gericht erstelltesGericht = verwaltung.erstelleGericht(gericht);
		assertEquals(erstelltesGericht.getName(), "Spaghetti Cabonara");
		assertEquals(erstelltesGericht.getBeschreibung(), "Nudeln ins Wasser und leckere Cabonora-Soße kochen");
		assertEquals(erstelltesGericht.getAufwand(),  60);
		EasyMock.verify(verwalter);
	}
	
	
	
	@Test
	void ladeAlleGerichte() {
		List<Entitaet> gerichte = Arrays.asList(gericht);
		EasyMock.expect(verwalter.holeAlle(Gericht.class)).andReturn(gerichte);
		
		EasyMock.replay(verwalter);

		List<Gericht> alleGerichte = verwaltung.holeAlle();
		assertEquals(alleGerichte.size(), 1);
		assertEquals(alleGerichte.get(0).getName(), "Spaghetti Cabonara");
		assertEquals(alleGerichte.get(0).getBeschreibung(), "Nudeln ins Wasser und leckere Cabonora-Soße kochen");
		assertEquals(alleGerichte.get(0).getAufwand(),  60);
		EasyMock.verify(verwalter);
	}

}
