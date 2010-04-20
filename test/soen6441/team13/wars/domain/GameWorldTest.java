package soen6441.team13.wars.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import soen6441.team13.wars.factory.UnitFactory;
import edu.uci.ics.jung.graph.Graph;

public class GameWorldTest {
	private GameWorld gameWorld;
	private UnitFactory unitFactory;
	private Graph<State, Edge> graph;
	
	@Before
	public void before() {
		gameWorld = new GameWorld();
		unitFactory = new UnitFactory();
		graph = gameWorld.getGraph();
	}

	@Test
	public void testGetGraph() {
		assertNotNull(gameWorld.getGraph());
	}

	@Test
	public void testGetPlayers() {
		assertNotNull(gameWorld.getPlayers());
	}

	@Test
	public void testAddPlayer() {
		gameWorld.addPlayer(new Player("Test Player"));
		assertEquals(gameWorld.getPlayers().size(), 1);
	}

	@Test
	public void testAddContinent() {
		gameWorld.addContinent(new Continent("Test Continent"));
		assertEquals(gameWorld.getContinents().size(), 1);
	}

	@Test
	public void testGetContinents() {
		assertNotNull(gameWorld.getContinents());
	}

	@Test
	public void testHasPlayer() {
		Player TestPlayer =  new Player("Test Player");
		gameWorld.addPlayer(TestPlayer);
		assertTrue(gameWorld.hasPlayer(TestPlayer));
	}

	@Test
	public void testGetStates() {
		final Player TestPlayer =  new Player("Test Player");
		gameWorld.addPlayer(TestPlayer);		
		final State state1 = new State(1, TestPlayer, unitFactory);
		final State state2 = new State(2, TestPlayer, unitFactory);

		gameWorld.getGraph().addVertex(state1);
		gameWorld.getGraph().addVertex(state2);
		assertEquals(gameWorld.getStates().size(), 2);
	}

	@Test
	public void testGetStatesPlayer() {
		final Player TestPlayer =  new Player("Test Player");
		final Player AnotherTestPlayer =  new Player("Another Test Player");
		gameWorld.addPlayer(TestPlayer);
		gameWorld.addPlayer(AnotherTestPlayer);
		final State state1 = new State(1, TestPlayer, unitFactory);
		final State state2 = new State(2, AnotherTestPlayer, unitFactory);

		gameWorld.getGraph().addVertex(state1);
		gameWorld.getGraph().addVertex(state2);
		assertEquals(gameWorld.getStates(TestPlayer).size(), 1);
	}

	@Test
	public void testIsLoser() {
		final Player TestPlayer =  new Player("Test Player");
		gameWorld.addPlayer(TestPlayer);
		assertTrue(gameWorld.isLoser(TestPlayer));
	}

	@Test
	public void testIsOnFrontLine() {
		Player TestPlayer =  new Player("Test Player");
		Player AnotherTestPlayer =  new Player("Another Test Player");
		gameWorld.addPlayer(TestPlayer);
		gameWorld.addPlayer(AnotherTestPlayer);
		State state1 = new State(0, TestPlayer, unitFactory);
		State state2 = new State(1, AnotherTestPlayer, unitFactory);
		graph.addVertex(state1);
		graph.addVertex(state2);
		graph.addEdge(new Edge(), state1, state2);
		
		assertTrue(gameWorld.isOnFrontLine(state1)
					&& gameWorld.isOnFrontLine(state2));
	}

	@Test
	public void testGetNeighborStates() {
		final Player TestPlayer =  new Player("Test Player");
		final Player AnotherTestPlayer =  new Player("Another Test Player");
		gameWorld.addPlayer(TestPlayer);
		gameWorld.addPlayer(AnotherTestPlayer);
		final State state1 = new State(1, TestPlayer, unitFactory);
		final State state2 = new State(2, TestPlayer, unitFactory);
		final State state3 = new State(3, AnotherTestPlayer, unitFactory);
		graph.addVertex(state1);
		graph.addVertex(state2);
		graph.addVertex(state3);
		
		gameWorld.getGraph().addEdge(new Edge(), state1, state2);
		gameWorld.getGraph().addEdge(new Edge(), state1, state3);
		assertEquals(gameWorld.getNeighborStates(state1).size(), 2);
	}

	@Test
	public void testGetEnemyNeighborStates() {
		final Player TestPlayer =  new Player("Test Player");
		final Player AnotherTestPlayer =  new Player("Another Test Player");
		gameWorld.addPlayer(TestPlayer);
		gameWorld.addPlayer(AnotherTestPlayer);
		final State state1 = new State(1, TestPlayer, unitFactory);
		final State state2 = new State(2, AnotherTestPlayer, unitFactory);
		graph.addVertex(state1);
		graph.addVertex(state2);
		
		gameWorld.getGraph().addEdge(new Edge(), state1, state2);
		assertEquals(gameWorld.getEnemyNeighborStates(state1).size(), 1);
	}

}
