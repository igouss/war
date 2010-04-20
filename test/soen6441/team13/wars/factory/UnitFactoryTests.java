/**
 * 
 */
package soen6441.team13.wars.factory;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import soen6441.team13.wars.domain.Player;
import soen6441.team13.wars.domain.State;
import soen6441.team13.wars.logger.ActionLogger;

/**
 * @author Pavel
 *
 */
public class UnitFactoryTests {

	UnitFactory unitFactory;
	State montreal;
	ActionLogger actionLogger;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		actionLogger = new ActionLogger();
		unitFactory = new UnitFactory();
		final Player p1 = new Player("player 1");
		montreal = new State(0, p1, new UnitFactory());
	}

	/**
	 * Test method for {@link soen6441.team13.wars.factory.UnitFactory#createUnit
	 * (soen6441.team13.wars.domain.State, int, int)}.
	 */
	@Test
	public final void testCreateUnit() {
		montreal.setHasBarracks(true);
		Collection<Unit> units = unitFactory.createUnit(montreal, 3, 100, actionLogger);
		assertEquals(units.size(), 3);

		montreal.setHasStables(true);
		units = unitFactory.createUnit(montreal, 3, 100, actionLogger);
		assertEquals(units.size(), 2);

		montreal.setHasFoundry(true);
		units = unitFactory.createUnit(montreal, 3, 100, actionLogger);
		assertEquals(units.size(), 1);

	}

}
