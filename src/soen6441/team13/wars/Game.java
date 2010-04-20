package soen6441.team13.wars;

import soen6441.team13.wars.controller.GameEditorViewController;
import soen6441.team13.wars.presentation.GameFrame;

/**
 * Contains applications main(). Starts new game and shows map editor user interface.
 */
public class Game {
	/**
	 * main function for simulation editor and simulation viewer GUI
	 */
	public static void main(String[] args) {
		new GameEditorViewController(new GameFrame()).initialize();
		//new GameEditorViewController(new GameEditorView()).initialize();
	}
}
