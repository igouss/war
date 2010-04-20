package soen6441.team13.wars.engine.movement;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import soen6441.team13.wars.domain.Edge;
import soen6441.team13.wars.domain.GameWorld;
import soen6441.team13.wars.domain.Player;
import soen6441.team13.wars.domain.State;
import soen6441.team13.wars.factory.UnitFactory;
import edu.uci.ics.jung.graph.Graph;

public class MovementStageTests {
	GameWorld gameWorld;
	Graph<State, Edge> graph;
	MovementStage movementStage;
	Player troy, pavel, justin;
	State montreal;
	State moscow;
	State toronto;
	State ottawa;
	State winipeg;
	State edmonton;

	@Before
	public void setUp() {
		gameWorld = new GameWorld();
		graph = gameWorld.getGraph();
		troy = new Player("Troy");
		pavel = new Player("Pavel");
		justin = new Player("Justin");
		gameWorld.addPlayer(troy);
		gameWorld.addPlayer(pavel);
		gameWorld.addPlayer(justin);
		montreal = new State(0, troy, new UnitFactory());
		montreal.setName("Montreal");

		moscow = new State(1, pavel, new UnitFactory());
		moscow.setName("Moscow");
		moscow.getGarrison().addInfantry(2);
		montreal.getGarrison().addInfantry(2);

		graph.addVertex(montreal);
		graph.addVertex(moscow);

		graph.addEdge(new Edge(), montreal, moscow);

		movementStage = new MovementStage(gameWorld);
	}

	@Test
	public void showsNoMoveIfOnFrontLine() throws Exception {
		movementStage.execute(troy);
		movementStage.execute(pavel);
		assertEquals(2, montreal.getArmySize());
		assertEquals(2, moscow.getArmySize());
	}

	@Test
	public void showsMovesToFrontLineByOne() throws Exception {
		addOttawa();
		movementStage.execute(troy);
		movementStage.execute(pavel);
		assertEquals(3, montreal.getArmySize());
		assertEquals(2, moscow.getArmySize());
		assertEquals(1, ottawa.getArmySize());
	}

	@Test
	public void showsMovesTowardsFrontLineByTwo() throws Exception {
		addOttawa();
		addToronto();
		movementStage.execute(troy);
		movementStage.execute(pavel);
		assertEquals(4, montreal.getArmySize());
		assertEquals(2, moscow.getArmySize());
		assertEquals(1, ottawa.getArmySize());
		assertEquals(1, toronto.getArmySize());
	}

	@Test
	public void showsChoosesTheShortestPath() throws Exception {
		addOttawa();
		addToronto();
		addWinipeg();
		addEdmonton();
		movementStage.execute(troy);
		movementStage.execute(pavel);
		movementStage.execute(justin);
		assertEquals(3, montreal.getArmySize());
		assertEquals(2, moscow.getArmySize());
		assertEquals(1, ottawa.getArmySize());
		assertEquals(1, toronto.getArmySize());
		assertEquals(3, winipeg.getArmySize());
		assertEquals(2, edmonton.getArmySize());
	}

	private void addToronto() {
		toronto = new State(3, troy,
				new UnitFactory());
		toronto.getGarrison().addInfantry(2);
		toronto.setName("Toronto");
		graph.addVertex(toronto);
		graph.addEdge(new Edge(), ottawa, toronto);
	}

	private void addOttawa() {
		ottawa = new State(2, troy,
				new UnitFactory());
		ottawa.getGarrison().addInfantry(2);
		ottawa.setName("Ottawa");
		graph.addVertex(ottawa);
		graph.addEdge(new Edge(), montreal, ottawa);
	}

	private void addWinipeg() {
		winipeg = new State(4, troy,
				new UnitFactory());
		winipeg.getGarrison().addInfantry(2);
		winipeg.setName("Winipeg");
		graph.addVertex(winipeg);
		graph.addEdge(new Edge(), toronto, winipeg);
	}

	private void addEdmonton() {
		edmonton = new State(5, justin,
				new UnitFactory());
		edmonton.getGarrison().addInfantry(2);
		edmonton.setName("Edmonton");
		graph.addVertex(edmonton);
		graph.addEdge(new Edge(), edmonton, winipeg);
	}

}
