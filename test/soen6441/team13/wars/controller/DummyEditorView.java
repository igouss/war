package soen6441.team13.wars.controller;

import java.io.File;

import soen6441.team13.wars.domain.Continent;
import soen6441.team13.wars.domain.Player;
import soen6441.team13.wars.domain.State;
import soen6441.team13.wars.presentation.EditorView;
import soen6441.team13.wars.presentation.gameWorld.GameWorldView;

public class DummyEditorView implements EditorView {
	boolean initCalled = true;
	boolean showCalled = true;
	File saveFile = new File("test");

	@Override
	public boolean isCitySelected() {
		return false;
	}

	@Override
	public void addPlayer(Player newPlayer) {
	}

	@Override
	public String getNewPlayerName() {
		return null;
	}

	@Override
	public int getNumberOfCavalry() {
		return 0;
	}

	@Override
	public int getNumberOfInfantry() {
		return 0;
	}

	@Override
	public int getNumberOfArtillery() {
		return 0;
	}

	@Override
	public File getSaveFile() {
		return saveFile;
	}

	@Override
	public Player getSelectedPlayer() {
		return null;
	}

	@Override
	public String getSelectedStateName() {
		return null;
	}

	@Override
	public void hasBarracks(boolean hasBarracks) {
	}

	@Override
	public boolean hasBracksSelected() {
		return false;
	}

	@Override
	public void hasIron(boolean hasIronMine) {
	}

	@Override
	public boolean hasIronSelected() {
		return false;
	}



	@Override
	public void hasStables(boolean hasStables) {
	}

	@Override
	public boolean hasStablesSelected() {
		return false;
	}

	@Override
	public void hasArtilleryFactory(boolean hasArtilleryFactory) {
	}

	@Override
	public boolean hasArtilleryFactorySelected() {
		return false;
	}

	@Override
	public void init() {
		initCalled = true;
	}

	@Override
	public void isCity(boolean city) {
	}

	@Override
	public File openFile() {
		return saveFile;
	}

	@Override
	public void selectPlayer(Player player) {
	}

	@Override
	public void setEditorController(EditorViewController gameEditorViewController) {
	}

	@Override
	public void setNumberOfCavalry(int cavalery) {
	}

	@Override
	public void setNumberOfInfantry(int infantry) {
	}

	@Override
	public void setNumberOfArtillery(int artillery) {
	}

	@Override
	public void setStateName(String name) {
	}

	@Override
	public void show() {
		showCalled = true;
	}

	public boolean initCalled() {
		return initCalled;
	}

	public boolean showCalled() {
		return showCalled;
	}

	@Override
	public void clearPlayerList() {
	}

	@Override
	public void setDescription(State state) {
	}

	@Override
	public void addContinent(Continent continent) {
	}

	@Override
	public void selectContinent(Continent continent) {
	}

	@Override
	public Continent getSelectedContinent() {
		return null;
	}

	@Override
	public void clearContinents() {
	}

	@Override
	public String getContinentName() {
		return null;
	}

	@Override
	public void setSimulationController(SimulationController simulationController) {
		// TODO Auto-generated method stub

	}

        @Override
    public GameWorldView getGameWorldView() {
        	return null;
    }
}
