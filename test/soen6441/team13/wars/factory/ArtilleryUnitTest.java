package soen6441.team13.wars.factory;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ArtilleryUnitTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public final void testArtilleryUnit() {
		Unit U = new ArtilleryUnit();
		assertEquals(U.strength,3);
	}


}
