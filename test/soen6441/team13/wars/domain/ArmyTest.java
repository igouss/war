package soen6441.team13.wars.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ArmyTest {
	private Army army;

	@Before
	public void before() {
		army = new Army();
	}

	@Test
	public void initialValuesShouldBeZero() {
		assertEquals(0, army.getArtillery());
		assertEquals(0, army.getInfantry());
		assertEquals(0, army.getCavalry());
	}

	@Test
	public void canChangeNumberOfArtillery() {
		army.setArtillery(10);
		assertEquals(10, army.getArtillery());
	}

	@Test
	public void canChangeNumberOfInfantry() {
		army.setInfantry(10);
		assertEquals(10, army.getInfantry());
	}

	@Test
	public void canChangeNumberOfCavalery() {
		army.setCavalry(10);
		assertEquals(10, army.getCavalry());
	}

}
