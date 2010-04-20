/**
 * 
 */
package soen6441.team13.wars.factory;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Pavel
 *
 */
public class CavalryUnitTest {

	/**
	 * Test method for {@link soen6441.team13.wars.factory.CavalryUnit#CavalryUnit()}.
	 */
	@Test
	public final void testCavalryUnit() {
		Unit U = new CavalryUnit();
		assertEquals(U.strength, 2);
	}

}
