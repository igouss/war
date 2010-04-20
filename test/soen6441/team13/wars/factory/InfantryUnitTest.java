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
public class InfantryUnitTest {

	/**
	 * Test method for {@link soen6441.team13.wars.factory.InfantryUnit#InfantryUnit()}.
	 */
	@Test
	public final void testInfantryUnit() {
		Unit U = new InfantryUnit();
		assertEquals(U.strength, 1);
	}

}
