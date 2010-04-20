package soen6441.team13.wars.engine;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import soen6441.team13.wars.domain.Army;
import soen6441.team13.wars.domain.Edge;
import soen6441.team13.wars.domain.GameWorld;
import soen6441.team13.wars.domain.Player;
import soen6441.team13.wars.domain.State;
import soen6441.team13.wars.engine.battle.BattleStage;
import soen6441.team13.wars.engine.battle.Luck;
import soen6441.team13.wars.factory.UnitFactory;

public class BattleStageTest {

	private GameWorld gameWorld;
	private UnitFactory unitFactory;
	private Luck luck;

	@Before
	public void before() {
		gameWorld = new GameWorld();
		unitFactory = new UnitFactory();
		luck = new ConstantLuck();
	}

	@Test
	public void test_Attack() {
		final Player p1 = new Player("player 1");
		final Player p2 = new Player("player 2");
		gameWorld.addPlayer(p1);
		gameWorld.addPlayer(p2);

		final State state1 = new State(1, p1, unitFactory);
		final State state2 = new State(2, p2, unitFactory);

		gameWorld.getGraph().addVertex(state1);
		gameWorld.getGraph().addVertex(state2);
		gameWorld.getGraph().addEdge(new Edge(), state1, state2);

		final Army army1 = new Army();
		army1.addArtillery(10);
		army1.addCavalery(10);
		state1.setArmy(army1);

		final Army army2 = new Army();
		army2.addCavalery(1);
		state2.setArmy(army2);

		BattleStage battleStage = new BattleStage(gameWorld, luck);
		battleStage.execute(p1);
		System.out.print(state1.getArmySize());
		System.out.print(state2.getArmySize());
		assertEquals(18, state1.getArmySize());
		assertEquals(1, state2.getArmySize());
	}
@Test
	public void test_MultiAttack() {
		final Player p1 = new Player("player 1");
		final Player p2 = new Player("player 2");
		gameWorld.addPlayer(p1);
		gameWorld.addPlayer(p2);

		final State state1 = new State(1, p1, unitFactory);
		final State state2 = new State(2, p2, unitFactory);
        final State state3 = new State(3, p2, unitFactory);
        final State state4 = new State(4, p2, unitFactory);

		gameWorld.getGraph().addVertex(state1);
		gameWorld.getGraph().addVertex(state2);
        gameWorld.getGraph().addVertex(state3);
        gameWorld.getGraph().addVertex(state4);
		gameWorld.getGraph().addEdge(new Edge(), state1, state2);
        gameWorld.getGraph().addEdge(new Edge(), state1, state3);
        gameWorld.getGraph().addEdge(new Edge(), state1, state4);


		final Army army1 = new Army();
		army1.addArtillery(10);
		//army1.addCavalery(10);
		state1.setArmy(army1);

		final Army army2 = new Army();
		army2.addInfantry(1);
		state2.setArmy(army2);

        final Army army3 = new Army();
		army3.addInfantry(1);
		state3.setArmy(army3);

        final Army army4 = new Army();
		army4.addInfantry(1);
		state4.setArmy(army4);

		BattleStage battleStage = new BattleStage(gameWorld, luck);
		battleStage.execute(p1);
		
		final int ArmySize = state1.getArmySize()+		state2.getArmySize()+		state3.getArmySize()+		state4.getArmySize();
		assertEquals(10, ArmySize);
		/*assertTrue(state1.getPlayer().equals(state2.getPlayer()) &&
                state1.getPlayer().equals(state3.getPlayer()) &&
                state1.getPlayer().equals(state4.getPlayer()));*/
		
	}
	@Test
	public void test_isThreatenedStateShouldNotAttack() {
		final Player p1 = new Player("player 1");
		final Player p2 = new Player("player 2");
		gameWorld.addPlayer(p1);
		gameWorld.addPlayer(p2);

		final State state1 = new State(1, p1, unitFactory);
		final State state2 = new State(2, p2, unitFactory);

		state1.isThreatened(true);

		gameWorld.getGraph().addVertex(state1);
		gameWorld.getGraph().addVertex(state2);
		gameWorld.getGraph().addEdge(new Edge(), state1, state2);

		final Army garrison1 = new Army();
		garrison1.addArtillery(10);
		garrison1.addCavalery(10);
		state1.setArmy(garrison1);

		BattleStage battleStage = new BattleStage(gameWorld, luck);
		battleStage.execute(p1);

		assertEquals(20, state1.getArmySize());
		assertEquals(0, state2.getArmySize());
	}

	/** Avoid random behavoiur by providing constant luck **/
	class ConstantLuck extends Luck {

		@Override
		public double getLuck() {
			return 0.5;
		}
	}
}
