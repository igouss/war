package soen6441.team13.wars.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import soen6441.team13.wars.factory.UnitFactory;
import soen6441.team13.wars.logger.ActionLogger;

public class StateTest {
	State state;
	Player player;

	@Before
	public void before() {
		player = new Player("P1");
		state = new State(0, player, new UnitFactory());
	}

	@Test
	public void canAccessPlayer() {
		assertEquals(player, state.getPlayer());
	}

	@Test
	public void canChangePlayer() {
		Player newPlayer = new Player("p2");
		state.setPlayer(newPlayer);
		assertEquals(newPlayer, state.getPlayer());
	}
	@Test
	public void testProduce(){
		state.setHasBarracks(true);
		state.produce(2, 100, new ActionLogger());
		assertEquals(2, state.getArmySize());
		state.setHasStables(true);		
		state.produce(2, 100, new ActionLogger());
		assertEquals(3, state.getArmySize());
	}
	@Test
	public void testMoveArmy(){
		state.setHasBarracks(true);
		state.produce(2, 100, new ActionLogger());
		state.setHasStables(true);		
		state.produce(2, 100, new ActionLogger());
	    State moveTo = new State(0, player, new UnitFactory());
		state.moveArmy(moveTo, state.getArmy());
		//System.out.print(moveTo.getArmySize());
		assertEquals(2, moveTo.getArmySize());
		assertEquals(1, state.getArmySize());		
	}	
	
	@Test
	public void shouldReturnCorrectStrength() throws Exception {
		Army garrison = new Army();

		state.setArmy(garrison);
		assertEquals(0, state.getStrength());
		garrison.setInfantry(1);
		assertEquals(1, state.getStrength());
		garrison.setCavalry(1);
		assertEquals(6, state.getStrength());
		garrison.setArtillery(1);
		assertEquals(16, state.getStrength());

	}

}
