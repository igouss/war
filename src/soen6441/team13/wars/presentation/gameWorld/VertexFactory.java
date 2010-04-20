package soen6441.team13.wars.presentation.gameWorld;

import org.apache.commons.collections15.Factory;

import soen6441.team13.wars.controller.EditorViewController;
import soen6441.team13.wars.domain.GameWorld;
import soen6441.team13.wars.domain.Player;
import soen6441.team13.wars.domain.State;
import soen6441.team13.wars.factory.UnitFactory;

/**
 * Create new vertices that represent game states. Used by JUNG.
 */
class VertexFactory implements Factory<State> {

	private final EditorViewController controller;
	private final UnitFactory unitFactory;

	public VertexFactory(EditorViewController controller) {
		this.controller = controller;
		unitFactory = new UnitFactory();
	}

	/**
	 * Create a new State object to be added to game map
	 */
	@Override
	public State create() {
		Player currentPlayer = controller.getCurrentPlayer();
		if (currentPlayer == null) {
			controller.createNewPlayer();
			currentPlayer = controller.getCurrentPlayer();
		}
		final GameWorld gameWorld = controller.getGameWorld();
		int numOfStates = gameWorld.getGraph().getVertexCount() + 1;

		final State state = new State(numOfStates, currentPlayer, unitFactory);

		return state;
	}
}