package soen6441.team13.wars.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
	Player player;

	@Before
	public void before() {
		player = new Player("Test");
	}

	@Test
	public void canAccessName() {
		assertEquals("Test", player.getName());
	}

	@Test
	public void hasColor() {
		assertNotNull(player.getColor());
	}
}
