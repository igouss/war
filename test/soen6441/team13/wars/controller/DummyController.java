package soen6441.team13.wars.controller;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.HashSet;

import soen6441.team13.wars.domain.GameWorld;
import soen6441.team13.wars.domain.Player;
import soen6441.team13.wars.domain.State;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

class DummyController implements EditorViewController {

	private boolean loadGameCalled = false;
	private boolean saveGameCalled = false;

	public boolean loadGameCalled() {
		return loadGameCalled;
	}

	public boolean saveGameCalled() {
		return saveGameCalled;
	}

	@Override
	public void loadGame() {
		loadGameCalled = true;
	}

	@Override
	public void saveGame() {
		saveGameCalled = true;
	}

	@Override
	public void createNewPlayer() {
	}

	@Override
	public void currentStateChanged() {
	}

	@Override
	public void graphSelectionChanged(Mode mode) {
	}

	public HashSet<Player> getPlayers() {
		return null;
	}

	@Override
	public Player getCurrentPlayer() {
		return null;
	}

	@Override
	public void stateSelected(State state) {
	}

	@Override
	public GameWorld getGameWorld() {
		return null;
	}

	@Override
	public void createNewContinent() {
	}

	@Override
	public void createNewContinent(Point2D location, Dimension2D size) {
	}

	@Override
	public void updateView() {
		// TODO Auto-generated method stub		
	}
}