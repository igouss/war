package soen6441.team13.wars.engine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import soen6441.team13.wars.domain.Army;
import soen6441.team13.wars.domain.Edge;
import soen6441.team13.wars.domain.GameWorld;
import soen6441.team13.wars.domain.Player;
import soen6441.team13.wars.domain.State;
import soen6441.team13.wars.factory.UnitFactory;

public class ReconStageTest {

	ReconStage stage;
	GameWorld gameWorld;
	private UnitFactory unitFactory;
	
	

	@Before
	public void before() {
		gameWorld = new GameWorld();
		stage = new ReconStage(gameWorld);
		unitFactory = new UnitFactory();
	}

	@Test
	public void test_shouldFlagStateAsTreatened() {
		final Player p1 = new Player("player 1");
		final Player p2 = new Player("player 2");
		gameWorld.addPlayer(p1);
		gameWorld.addPlayer(p2);

		final State state1 = new State(1, p1, unitFactory);
		final State state2 = new State(2, p2, unitFactory);

		gameWorld.getGraph().addVertex(state1);
		gameWorld.getGraph().addVertex(state2);
		gameWorld.getGraph().addEdge(new Edge(), state1, state2);

		final Army garrison2 = new Army();
		garrison2.addArtillery(10);
		garrison2.addCavalery(10);
		state2.setArmy(garrison2);

		stage.execute(p1);
		assertTrue(state1.isThreatened());
	}

	@Test
	public void test_shouldNOTflagAsTreatened() {
		final Player p1 = new Player("player 1");
		final Player p2 = new Player("player 2");
		gameWorld.addPlayer(p1);
		gameWorld.addPlayer(p2);

		final State state1 = new State(1, p1, unitFactory);
		final State state2 = new State(2, p2, unitFactory);

		gameWorld.getGraph().addVertex(state1);
		gameWorld.getGraph().addVertex(state2);
		gameWorld.getGraph().addEdge(new Edge(), state1, state2);

		final Army garrison1 = new Army();
		garrison1.addArtillery(10);
		garrison1.addCavalery(10);
		state1.setArmy(garrison1);

		final Army garrison2 = new Army();
		garrison2.addArtillery(5);
		garrison2.addCavalery(5);
		state2.setArmy(garrison2);

		stage.execute(p1);
		assertFalse(state1.isThreatened());
	}
}
