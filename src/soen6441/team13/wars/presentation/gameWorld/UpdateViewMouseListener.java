package soen6441.team13.wars.presentation.gameWorld;

import java.awt.event.MouseEvent;

import soen6441.team13.wars.controller.EditorViewController;
import soen6441.team13.wars.domain.State;
import edu.uci.ics.jung.visualization.control.GraphMouseListener;

/**
 * Capture mouse click on a state vertex and notify the controller about this
 * event.
 */
class UpdateViewMouseListener implements GraphMouseListener<State> {
	private final EditorViewController controller;

	/**
	 * create an update mouse listener
	 * @param controller
	 */
	public UpdateViewMouseListener(EditorViewController controller) {
		this.controller = controller;

	}

	/**
	 * when the mouse is released call this method
	 */
	@Override
	public void graphReleased(State arg0, MouseEvent arg1) {
		// do nothing
	}

	/**
	 * when the mouse is pressed this method will be called
	 */
	@Override
	public void graphPressed(State arg0, MouseEvent arg1) {
		// do nothing
	}

	/**
	 * when the mouse clicked on a state this method
	 * will be called and set the selected state
	 */
	@Override
	public void graphClicked(State state, MouseEvent arg1) {
		controller.stateSelected(state);
	}
}