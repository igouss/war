
package soen6441.team13.wars.logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import soen6441.team13.wars.domain.Action;
import soen6441.team13.wars.domain.ActionType;

/**
 *
 * @author Justin
 */
public class ActionLoggerTest {
        

	@Test
	public void testEventList() throws Exception {
            ActionLogger log = new ActionLogger();
            log.logBattle("justin", "state 3 produces soemthing");
            assertEquals(1, log.getEventList().size());

            log.logBattle("bob", "state 1 produces soemthing");
            assertEquals(2, log.getEventList().size());
            
            log.logBattle("justin", "state 6 produces soemthing");
            assertEquals(3, log.getEventList().size());
	}

	@Test
	public void testLogBattle() throws Exception {
            ActionLogger log = new ActionLogger();
            String result = "state 2 produces 5 cavalry";
            log.logBattle("justin", result);
            assertNotNull(log.getEventList().get(0));

            Action action = log.getEventList().get(0);
            assertEquals(action.getAction(), ActionType.ATTACK);
            assertEquals(action.getOrder().intValue(), 0);
            assertEquals(action.getPlayer(), "justin");
            assertEquals(action.getResult(), result);
            
	}

	@Test
	public void testLogMove() throws Exception {
            ActionLogger log = new ActionLogger();
            String result = "move test";
            log.logMove("justin", result);
            assertNotNull(log.getEventList().get(0));

            Action action = log.getEventList().get(0);
            assertEquals(action.getAction(), ActionType.MOVE);
            assertEquals(action.getOrder().intValue(), 0);
            assertEquals(action.getPlayer(), "justin");
            assertEquals(action.getResult(), result);

            //add another log
            log.logMove("justin", result);
            assertNotNull(log.getEventList().get(1));
            action = log.getEventList().get(1);
            assertEquals(action.getAction(), ActionType.MOVE);
            assertEquals(action.getOrder().intValue(), 1);
            assertEquals(action.getPlayer(), "justin");
            assertEquals(action.getResult(), result);
	}


	@Test
	public void testLogProduction() throws Exception {
            ActionLogger log = new ActionLogger();
            String result = "production testing";
            log.logProduction("justin", result);
            assertNotNull(log.getEventList().get(0));

            Action action = log.getEventList().get(0);
            assertEquals(action.getAction(), ActionType.PRODUCE);
            assertEquals(action.getOrder().intValue(), 0);
            assertEquals(action.getPlayer(), "justin");
            assertEquals(action.getResult(), result);

            //add another log
            log.logProduction("justin", result);
            assertNotNull(log.getEventList().get(1));
            action = log.getEventList().get(1);
            assertEquals(action.getAction(), ActionType.PRODUCE);
            assertEquals(action.getOrder().intValue(), 1);
            assertEquals(action.getPlayer(), "justin");
            assertEquals(action.getResult(), result);
	}


	@Test
	public void testLogRecon() throws Exception {
            ActionLogger log = new ActionLogger();
            String result = "recon testing";
            log.logRecon("justin", result);
            assertNotNull(log.getEventList().get(0));

            Action action = log.getEventList().get(0);
            assertEquals(action.getAction(), ActionType.SCOUT);
            assertEquals(action.getOrder().intValue(), 0);
            assertEquals(action.getPlayer(), "justin");
            assertEquals(action.getResult(), result);

            //add another log
            log.logRecon("justin", result);
            assertNotNull(log.getEventList().get(1));
            action = log.getEventList().get(1);
            assertEquals(action.getAction(), ActionType.SCOUT);
            assertEquals(action.getOrder().intValue(), 1);
            assertEquals(action.getPlayer(), "justin");
            assertEquals(action.getResult(), result);
	}

}
