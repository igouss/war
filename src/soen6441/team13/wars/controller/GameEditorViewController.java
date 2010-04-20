package soen6441.team13.wars.controller;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.Collection;
import java.util.LinkedList;

import soen6441.team13.wars.domain.Continent;
import soen6441.team13.wars.domain.GameWorld;
import soen6441.team13.wars.domain.Player;
import soen6441.team13.wars.domain.State;
import soen6441.team13.wars.logger.ActionLogger;
import soen6441.team13.wars.logger.ActionLoggerFactory;
import soen6441.team13.wars.persistence.SimReader;
import soen6441.team13.wars.persistence.SimWriter;
import soen6441.team13.wars.presentation.EditorView;
import soen6441.team13.wars.presentation.gameWorld.GameWorldView;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

/**
 * The controller receives input and initiates a
 * response by making calls on model objects.
 */
public class GameEditorViewController implements EditorViewController {

	private static final String DEFAULT_PLAYER_NAME = "Player 1";

	/**
	 * View that this controller controls.
	 */
	private final EditorView view;

	/**
	 * Graph that holds the game state.
	 */
	private GameWorld world;
	private final GameWorldView worldView;
	private final ActionLogger actionLogger;

	//	private SimEngine simEngine;

	private final SimulationController simulationController;

	/**
	 * Constructor responsible for creating all objects used by this class.
	 */
	public GameEditorViewController(EditorView view) {
		this.view = view;
		world = new GameWorld();
		worldView = view.getGameWorldView();
		simulationController = new SimulationController(worldView);
		actionLogger = ActionLoggerFactory.getInstance();
	}

	/**
	 * Initialises view, sets some listeners, and displays the GUI
	 */
	public void initialize() {
		view.setEditorController(this);
		view.setSimulationController(simulationController);
		view.init();

		LinkedList<Continent> continents = world.getContinents();
		for (Continent continent : continents) {
			view.addContinent(continent);
		}
		view.selectContinent(null);
		view.show();
	}

	/**
	 * Callback from view when user changes current state parameters. Updates
	 * current state parameters.
	 */
	@Override
	public void currentStateChanged() {
		State currentState = null;
		State[] pickedStates = worldView.getPickedStates();

		if (pickedStates.length == 1) {
			currentState = pickedStates[0];
		}

		if (currentState != null) {

			currentState.setHasBarracks(view.hasBracksSelected());
			currentState.setHasIronMine(view.hasIronSelected());
			currentState.setHasStables(view.hasStablesSelected());
			currentState.setHasFoundry(view.hasArtilleryFactorySelected());
			currentState.setCity(view.isCitySelected());
			currentState.setPlayer(view.getSelectedPlayer());

			currentState.setName(view.getSelectedStateName());
			worldView.updateLabel(currentState, view.getSelectedStateName());
			worldView.updateStateLocations();

			currentState.getGarrison().setCavalry(view.getNumberOfCavalry());
			currentState.getGarrison().setArtillery(view.getNumberOfArtillery());
			currentState.getGarrison().setInfantry(view.getNumberOfInfantry());
			Continent newContinent = null;
			//check if there is a drawn continent 
			for (Continent continent : getGameWorld().getContinents()) {
				if (continent.isMyState(currentState)) {
					newContinent = continent;
					break;
				}
			}
			// if continent was not found, take one from the list
			if (newContinent == null) {
				newContinent = view.getSelectedContinent();
			}
			if (currentState != null && newContinent != null) {
				// if this state now belongs to a different continent
				// remove state from the old continent and add to the new one
				Continent oldContinent = currentState.getContinent();
				if (oldContinent != null) {
					oldContinent.removeState(currentState);
				}
				newContinent.addState(currentState);
				currentState.setContinent(newContinent);
			}

			worldView.updateVertex(currentState);
			updateView();
		}
	}

	/**
	 * Callback from view when user selects different mode of map editing. Set
	 * new editing mode.
	 */
	public void graphSelectionChanged(Mode mode) {
		worldView.getGraphMouse().setMode(mode);
	}

	/**
	 * Callback from view when user selects a state. Updates view with state
	 * data.
	 */
	public void stateSelected(State state) {
		refreshStateValues(state);
	}

	/**
	 * Update the view with the state data.
	 */
	private void refreshStateValues(State state) {
		view.setStateName(state.getName());
		view.setNumberOfInfantry(state.getGarrison().getInfantry());
		view.setNumberOfCavalry(state.getGarrison().getCavalry());
		view.setNumberOfArtillery(state.getGarrison().getArtillery());
		view.isCity(state.isCity());
		view.hasBarracks(state.hasBarracks());
		view.hasIron(state.hasIronMine());
		view.hasStables(state.hasStables());
		view.hasArtilleryFactory(state.hasFoundry());
		view.selectPlayer(state.getPlayer());
		view.selectContinent(state.getContinent());
		view.setDescription(state);
	}

	public State[] getPickedStates() {
		return worldView.getPickedStates();
	}

	/**
	 * Return the View that this controller controls.
	 */
	public EditorView getView() {
		return view;
	}

	/**
	 * Get set of players.
	 * @return
	 */
	public LinkedList<Player> getPlayers() {
		return world.getPlayers();
	}

	/**
	 * Callback from view when users requests application to load saved game.
	 * Load the game and updates view with new game state.
	 **/
	public void loadGame() {
		File file = view.openFile();
		if (file != null) {
			String path = file.getPath();

			// world.clear();
			world = (GameWorld) SimReader.fromXML(path);
			if (world != null) {
				if (worldView != null) worldView.reset();
				simulationController.stopSimulation();
				
				view.clearPlayerList();
				view.clearContinents();

				Collection<State> states = world.getStates();

				for (Player player : world.getPlayers()) {
					view.addPlayer(player);
				}

				for (Continent continent : world.getContinents()) {
					view.addContinent(continent);
					if (worldView != null) worldView.DrawContinents(continent);
				}

				for (State s : states) {
					if (worldView != null) worldView.setStateLocation(s, s.getLocation());
					refreshStateValues(s);
				}
				// restore the action log from game world
				actionLogger.setLogState(world.getActionLog());
				updateView();
			} else {
				System.err.println("File" + file.getName() + "does not exist");
			}
		}
	}

	/**
	 * Callback from view when user request to save current game. Saves game in
	 * user selected file.
	 */
	public void saveGame() {
		File file = view.getSaveFile();
		if (file != null) {
			String path = file.getAbsolutePath();
			world.setActionLog(actionLogger.getEventList());
			if (worldView != null) worldView.updateStateLocations(); //refresh state locations, (graphical coordinates) 
			SimWriter.toXML(world, path);
		}
	}

	/**
	 * Callback from view when user add new player. Adds new player to the game.
	 */
	public void addPlayer(Player player) {
		if (player != null && !world.hasPlayer(player)) {
			world.addPlayer(player);
			view.addPlayer(player);
		}
	}

	/**
	 * Callback for view when new user adds new player.
	 */
	public void createNewPlayer() {
		String playerName = view.getNewPlayerName();
		Player newPlayer;
		if (playerName != null) {
			newPlayer = new Player(playerName);
		} else {
			newPlayer = new Player(DEFAULT_PLAYER_NAME);
		}
		addPlayer(newPlayer);
	}

	/**
	 * Return GameWorld model.
	 */
	@Override
	public GameWorld getGameWorld() {
		return world;
	}

	/**
	 * Return currently selected player.
	 */
	@Override
	public Player getCurrentPlayer() {
		return view.getSelectedPlayer();
	}

	/**
	 * Create a new continent.
	 */
	@Override
	public void createNewContinent() {
		String name = view.getContinentName();
		if (name != null) {
			addContinent(new Continent(name));
		}

	}

	/**
	 * Add new continent to world and view.
	 */
	private void addContinent(Continent continent) {
		world.addContinent(continent);
		view.addContinent(continent);
	}

	/**
	 * create a rectangular 
	 * new continent at a position with certain
	 * dimensions
	 */
	@Override
	public void createNewContinent(Point2D location, Dimension2D size) {
		String name = view.getContinentName();
		if (name != null) {
			worldView.updateStateLocations();

			Continent continent = new Continent(name);
			continent.setLocation(location);
			continent.setSize(size);

			addContinent(continent);

			Collection<State> states = world.getStates();
			for (State state : states) {
				if (continent.isMyState(state)) {
					Continent oldContinent = state.getContinent();
					if (oldContinent != null) {
						oldContinent.removeState(state);
					}
					state.setContinent(continent);
					continent.addState(state);
				}
			}
		}
	}

	/**
	 * repaint the GUI
	 */
	@Override
	public void updateView() {
		if (worldView != null) worldView.repaint();
	}
}
