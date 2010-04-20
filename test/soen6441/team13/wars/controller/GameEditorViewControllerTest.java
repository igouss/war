package soen6441.team13.wars.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import soen6441.team13.wars.domain.Player;

public class GameEditorViewControllerTest {
	GameEditorViewController controller;
	DummyEditorView view;


	@Before
	public void before() {
		view = new DummyEditorView();
		controller = new GameEditorViewController(view);
	}

	@Test
	public void test_on_init_initializes_view() {
		controller.initialize();
		assertTrue(view.initCalled());
	}
	
	@Test
	public void test_on_init_show_view() {
		controller.initialize();
		assertTrue(view.showCalled());
	}
	
	@Test
	public void test_can_add_players() {
		controller.addPlayer(new Player("test1"));
		assertEquals(1, controller.getPlayers().size());
	}
}
