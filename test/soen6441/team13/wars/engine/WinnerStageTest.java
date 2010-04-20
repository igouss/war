package soen6441.team13.wars.engine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import soen6441.team13.wars.domain.Edge;
import soen6441.team13.wars.domain.GameWorld;
import soen6441.team13.wars.domain.Player;
import soen6441.team13.wars.domain.State;
import soen6441.team13.wars.factory.UnitFactory;
import edu.uci.ics.jung.graph.Graph;

public class WinnerStageTest {
	GameWorld gameWorld;
	Graph<State, Edge> graph;
	WinnerStage winStage;
	Player justin, pavel;
	State montreal;
	State moscow;

	@Before
	public void setUp() {
		gameWorld = new GameWorld();
		graph = gameWorld.getGraph();
	}
	@Test
	public void testWinnerStage() {
		
	}

	@Test
	public void testIsWinner() {

		pavel = new Player("Pavel");
		justin = new Player("Justin");
		
		gameWorld.addPlayer(justin);
		gameWorld.addPlayer(pavel);
		winStage = new WinnerStage(gameWorld);
		moscow = new State(0, pavel, new UnitFactory());

		graph.addVertex(moscow);
		assertTrue(winStage.isWinner(pavel));

		montreal = new State(1, justin, new UnitFactory());		
		graph.addEdge(new Edge(), montreal, moscow);
		graph.addVertex(montreal);
		assertFalse(winStage.isWinner(pavel));
			
	}

}
