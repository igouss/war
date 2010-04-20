package soen6441.team13.wars.presentation;

import java.io.File;

import soen6441.team13.wars.controller.EditorViewController;
import soen6441.team13.wars.controller.SimulationController;
import soen6441.team13.wars.domain.Continent;
import soen6441.team13.wars.domain.Player;
import soen6441.team13.wars.domain.State;
import soen6441.team13.wars.presentation.gameWorld.GameWorldView;

/**
 * This is the interface to the main view frame for the game editor
 */
public interface EditorView {

	/**
	 * sets the controller that will handle events
	 */
	void setEditorController(EditorViewController gameEditorViewController);

	/**
	 * Initialise view
	 */
	void init();

	/**
	 * Show view
	 */
	void show();

	/**
	 * Get selected player name
	 * 
	 * @return
	 */
	String getNewPlayerName();

	/**
	 * Add player to the view
	 */
	void addPlayer(Player newPlayer);

	/**
	 * Check is user marked state as a city
	 */
	boolean isCitySelected();

	/**
	 * Check is user marked that the current territory has barracks.
	 */
	boolean hasBracksSelected();

	/**
	 * Check is user marked that the current territory has iron.
	 */
	boolean hasIronSelected();

	/**
	 * Check is user marked that the current territory has stables.
	 */
	boolean hasStablesSelected();

	/**
	 * Check is user marked that the current territory has artillery factory.
	 */
	boolean hasArtilleryFactorySelected();

	/**
	 * get currently selected player.
	 */

	Player getSelectedPlayer();

	/**
	 * Get currently selected state name.
	 */
	String getSelectedStateName();

	/**
	 * Return number of cavalry units in currently selected territory
	 */
	int getNumberOfCavalry();

	/**
	 * Return number of artillery units in currently selected territory
	 */
	int getNumberOfArtillery();

	/**
	 * Return number of infantry units in currently selected territory
	 */
	int getNumberOfInfantry();

	/**
	 * Set the name of the state.
	 */
	void setStateName(String name);

	/**
	 * Set number of infantry in currently selected territory
	 */
	void setNumberOfInfantry(int infantry);

	/**
	 * Set number of cavalry units in currently selected territory
	 */
	void setNumberOfCavalry(int cavalery);

	/**
	 * Return number of artillery units in currently selected territory
	 */
	void setNumberOfArtillery(int artillery);

	/**
	 * Update territory state
	 * 
	 * @param city
	 *            if true make is a city else a territory
	 */
	void isCity(boolean city);

	/**
	 * Set number of barracks in currently selected territory
	 */
	void hasBarracks(boolean hasBarracks);

	/*
	 * Configure resources
	 * 
	 * @param hasOilDerrick
	 *            true is territory has this resource
	 */
	//void hasOil(boolean hasOilDerrick);

	/**
	 * Configure resources
	 * 
	 * @param hasIron
	 *            true is territory has this resource
	 */
	void hasIron(boolean hasIronMine);

	/**
	 * this represents the editor method to add
	 * and remove a stable from a city state
	 * and grant the capability to create cavalry
	 * @param hasStables
	 */
	void hasStables(boolean hasStables);

	/**
	 * Configure resources
	 * 
	 * @param ArtilleryFactory
	 *            true is territory has this resource
	 */
	void hasArtilleryFactory(boolean hasArtilleryFactory);

	/**
	 * Select current player
	 */
	void selectPlayer(Player player);

	/**
	 * Get a file that contains saved game.
	 * 
	 * @return null if no file selected
	 */
	File openFile();

	/**
	 * Ask user to select a file where to save the game.
	 * 
	 * @return null if no file selected
	 */
	File getSaveFile();

	/**
	 * Clear player list
	 */
	void clearPlayerList();

	/**
	 * Sends a summary description of the state to the view.
	 */
	void setDescription(State state);

	/**
	 * Add continent to the view
	 */
	void addContinent(Continent continent);

	/**
	 * Select current continent.
	 */
	void selectContinent(Continent continent);

	/**
	 * Return currently selected continent.
	 */
	Continent getSelectedContinent();

	/**
	 * Clear continents list
	 */
	void clearContinents();

	/**
	 * Ask user for continent name
	 * @return continent name or null of user cancels request.
	 */
	String getContinentName();

	/**
	 * set the simulation controller
	 */
	void setSimulationController(SimulationController simulationController);

	GameWorldView getGameWorldView();
}
