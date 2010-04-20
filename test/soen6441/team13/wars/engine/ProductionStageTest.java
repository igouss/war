package soen6441.team13.wars.engine;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import soen6441.team13.wars.domain.Continent;
import soen6441.team13.wars.domain.Edge;
import soen6441.team13.wars.domain.GameWorld;
import soen6441.team13.wars.domain.Player;
import soen6441.team13.wars.domain.State;
import soen6441.team13.wars.factory.UnitFactory;
import edu.uci.ics.jung.graph.Graph;

public class ProductionStageTest {

	GameWorld gameWorld;
	Graph<State, Edge> graph;
	ProductionStage prodStage;
	Player justin, pavel;
	State montreal;
	State moscow;

	@Before
	public void setUp() {

		gameWorld = new GameWorld();
		graph = gameWorld.getGraph();
		justin = new Player("Justin");
		pavel = new Player("Pavel");
		gameWorld.addPlayer(justin);
		gameWorld.addPlayer(pavel);
		montreal = new State(0, justin, new UnitFactory());
		moscow = new State(1, pavel, new UnitFactory());

		graph.addVertex(montreal);
		graph.addVertex(moscow);

		graph.addEdge(new Edge(), montreal, moscow);

		prodStage = new ProductionStage(gameWorld);
	}

	@Test
	public void noCityZeroArmy() throws Exception {
		montreal.setCity(false);
		prodStage.execute(justin);
		Collection<State> states = gameWorld.getStates(justin);
		for (State st : states) {
			assertEquals(0, st.getArmySize());
		}

		moscow.setCity(false);
		prodStage.execute(pavel);
		states = gameWorld.getStates(pavel);
		for (State st : states) {
			assertEquals(0, st.getArmySize());
		}
	}

	@Test
	public void oneCityNoIronMine() throws Exception {
		montreal.setCity(true);

		Continent cont = new Continent("asia");
		cont.addState(montreal);
		montreal.setContinent(cont);

		prodStage.execute(justin);
		Collection<State> states = gameWorld.getStates(justin);
		for (State st : states) {
			assertEquals(23, st.getArmySize());
		}

		moscow.setCity(true);
		Continent cont2 = new Continent("oceania");
		cont2.addState(moscow);
		moscow.setContinent(cont2);

		prodStage.execute(pavel);
		states = gameWorld.getStates(pavel);
		for (State st : states) {
			assertEquals(23, st.getArmySize());
		}
	}

	/**
	 * One city one iron mine will still produce 23 armies... need at least 200 iron to produce anything more
	 * @throws Exception
	 */
	@Test
	public void oneCityOneIronMine() throws Exception {
		montreal.setCity(true);
		montreal.setHasIronMine(true);
		Continent cont = new Continent("asia");
		cont.addState(montreal);
		montreal.setContinent(cont);

		prodStage.execute(justin);
		Collection<State> states = gameWorld.getStates(justin);
		for (State st : states) {
			assertEquals(23, st.getArmySize());
		}

		moscow.setCity(true);
		moscow.setHasIronMine(true);
		Continent cont2 = new Continent("oceania");
		cont2.addState(moscow);
		moscow.setContinent(cont2);

		prodStage.execute(pavel);
		states = gameWorld.getStates(pavel);
		for (State st : states) {
			assertEquals(23, st.getArmySize());
		}
	}

	@Test
	public void testCalculationScore1() throws Exception {
		Set<Continent> c = new HashSet<Continent>();
		montreal.setCity(true);
		Continent cont = new Continent("asia");
		cont.addState(montreal);
		montreal.setContinent(cont);
		c.add(cont);
		assertEquals(23, prodStage.calcProductionScore(1, 1, c, justin));
	}

	@Test
	public void testCalculationScore2() throws Exception {
		Set<Continent> c = new HashSet<Continent>();
		montreal.setCity(true);
		Continent cont = new Continent("asia");
		cont.addState(montreal);
		montreal.setContinent(cont);
		montreal.setPlayer(justin);

		moscow.setCity(true);
		cont.addState(moscow);
		moscow.setContinent(cont);
		moscow.setPlayer(pavel);

		c.add(cont);

		assertEquals(18, prodStage.calcProductionScore(1, 1, c, justin));
	}
}
