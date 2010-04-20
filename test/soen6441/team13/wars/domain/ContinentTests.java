package soen6441.team13.wars.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Dimension2D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ContinentTests {
	Player player;
	State state;
	Continent continent;
	Point continentLoc, stateLoc;
	Dimension2D continentSize;

	@Before
	public void setUp() throws Exception {
		player = new Player("Troy");
		state = new State(1, player, null);
		continentSize = new Dimension(100, 100);
		continent = new Continent("NA");
		continent.setSize(continentSize);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void shouldDetectStateWithinContinent() throws Exception {
		continentLoc = new Point(0, 0);
		stateLoc = new Point(50, 50);
		continent.setLocation(continentLoc);
		state.setLocation(stateLoc);
		assertTrue(continent.isMyState(state));
	}

	@Test
	public void shouldDetectStateIsNotInContinent() throws Exception {
		continentLoc = new Point(125, 125);
		stateLoc = new Point(50, 50);
		continent.setLocation(continentLoc);
		state.setLocation(stateLoc);
		assertFalse(continent.isMyState(state));
	}

}
