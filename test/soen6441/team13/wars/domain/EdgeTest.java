package soen6441.team13.wars.domain;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

public class EdgeTest {

	@Test
	public void canCreateEdge() {
		try {
			Edge edge = new Edge();
			assertNotNull(edge);
		} catch (Exception e) {
			fail("Failed to create an edge");
		}
	}
}
