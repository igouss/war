package soen6441.team13.wars.persistence;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import soen6441.team13.wars.controller.DummyEditorView;
import soen6441.team13.wars.controller.GameEditorViewController;
import soen6441.team13.wars.domain.GameWorld;
import soen6441.team13.wars.domain.Player;

public class GameWorldPersistenceTest {
	GameEditorViewController controller;
	DummyEditorView view;
	GameWorld gameWorld;
	
	@Before
	public void setUp() {
		view = new DummyEditorView();
		controller = new GameEditorViewController(view);
		controller.addPlayer(new Player("p1"));
		gameWorld = controller.getGameWorld();
	}
	
	
	
	@Test 
	public void canSaveAndLoadWorld() {
		controller.saveGame();		
		controller = new GameEditorViewController(view);		
		controller.loadGame();
		assertEquals(1, gameWorld.getPlayers().size());
		
	}
	
	@Test
	public void canSaveAndLoadWorldAsBinary() throws Exception {
		SimWriter.toBinary(gameWorld, "gameWorld.bin");
		GameWorld newGameWorld = SimReader.fromBinary("gameWorld.bin");
		assertEquals(1, newGameWorld.getPlayers().size());
	}
	
	@Test
	public void canSaveAndLoadWorldAsJSON() throws Exception {
		SimWriter.toJSON(gameWorld, "gameWorld.json");
		GameWorld newGameWorld = SimReader.fromJSON("gameWorld.json");
		assertEquals(1, newGameWorld.getPlayers().size());
	}
	
	
	@Test(expected=RuntimeException.class)
	public void shouldThrowExceptionWhenFileInvalidSaveBinary() throws Exception {
		SimWriter.toBinary(gameWorld, null);
	}
	
	@Test(expected=RuntimeException.class)
	public void shouldThrowExceptionWhenFileInvalidLoadBinary() throws Exception {
		SimReader.fromBinary(null);
	}
	
	
	@Test(expected=RuntimeException.class)
	public void shouldThrowExceptionWhenFileInvalidSaveXML() throws Exception {
		SimWriter.toXML(gameWorld, null);
	}
	
	@Test(expected=RuntimeException.class)
	public void shouldThrowExceptionWhenFileInvalidLoadXML() throws Exception {
		SimReader.fromXML(null);
	}
	
	@Test(expected=RuntimeException.class)
	public void shouldThrowExceptionWhenFileInvalidSaveJSON() throws Exception {
		SimWriter.toJSON(gameWorld, null);
	}
	
	@Test(expected=RuntimeException.class)
	public void shouldThrowExceptionWhenFileInvalidLoadJSON() throws Exception {
		SimReader.fromJSON(null);
	}
	
	
	
	
	
}
