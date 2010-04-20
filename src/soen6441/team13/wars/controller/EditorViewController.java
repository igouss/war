package soen6441.team13.wars.controller;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

import soen6441.team13.wars.domain.GameWorld;
import soen6441.team13.wars.domain.Player;
import soen6441.team13.wars.domain.State;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

/**
 * The interface to our Controller which is a mediator between the model and view 
 * it pulls and pushes state between the model and the view.
 */
public interface EditorViewController {
	/**
	 * Saves the current game
	 */
	void saveGame();

	/**
	 * Loads new game
	 */
	void loadGame();

	/**
	 * Created new player
	 */
	void createNewPlayer();

	/**
	 * Callback for view. Notify class that current state changed.
	 */
	void currentStateChanged();

	/**
	 * Callback for view. Notify class that map editor selection mechanism
	 * changed.
	 */
	void graphSelectionChanged(Mode mode);

	/**
	 * Return currently active player
	 */
	Player getCurrentPlayer();

	/**
	 * Set currently selected state
	 */
	void stateSelected(State state);

	/**
	 * Return game world.
	 */
	GameWorld getGameWorld();

	/**
	 * Callback for the view. Create new continent.
	 */
	void createNewContinent();

	/**
	 * a method to create a new continent
	 */
	void createNewContinent(Point2D location, Dimension2D size);

	/**
	 * update the view of the simulation and editor
	 */
	void updateView();
}
