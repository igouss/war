package soen6441.team13.wars.controller;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import soen6441.team13.wars.domain.GameWorld;
import soen6441.team13.wars.engine.SimEngine;
import soen6441.team13.wars.engine.battle.Luck;
import soen6441.team13.wars.presentation.gameWorld.GameWorldView;

/**
 * This class is the controller for the simulation
 * it links the sim engine to the sim viewer
 *
 */
public class SimulationController {
	private SimEngine simEngine;
	private final GameWorldView worldView;

	private boolean started;
	private final Luck luck;

	/**
	 * Create new Simulation controller with random luck.
	 */
	public SimulationController(GameWorldView worldView) {
		this(worldView, new Luck());
	}

	/**
	 * Create new Simulation controller with user provided luck generation.
	 */
	public SimulationController(GameWorldView worldView, Luck luck) {
		this.worldView = worldView;
		this.luck = luck;
	}

	/**
	 * 
	 */
	public void start(GameWorld gameWorld) {
		if (gameWorld.getPlayers().size() > 0) {
			simEngine = new SimEngine(gameWorld, luck);
			started = true;
		}
	}

	/**
	 * Start the war simulator
	 */
	public void startSimulation() {

		if (isStarted() && !simEngine.isGameOver()) {
			new SwingWorker<Boolean, Object>() {
				@Override
				protected Boolean doInBackground() throws Exception {
					simEngine.execute();

					return simEngine.isGameOver();
				}

				@Override
				protected void done() {
					try {
						worldView.repaint();
						if (get().booleanValue()) {
							gameOver();
						} else {
							startSimulation();
						}
					} catch (Exception ignore) {
						ignore.printStackTrace();
					}
				}
			}.execute();
		}
	}

	/**
	 * Stop the simulation engine even is game is not over.
	 */
	public void stopSimulation() {
		started = false;
	}

	/**
	 * Callback for sim engine when game is over and some player won.
	 */
	public void gameOver() {
		JOptionPane.showMessageDialog(null,
				"Tada!",
				"The Game is over! Player " + simEngine.getWinner().getName() + " won!",
				JOptionPane.INFORMATION_MESSAGE);
		stopSimulation();
	}

	/**
	 * Perform one step of the simulation.
	 */
	public void nextTurn() {
		if (isStarted() && !simEngine.isGameOver()) {
			new SwingWorker<Boolean, Object>() {
				@Override
				protected Boolean doInBackground() throws Exception {
					simEngine.execute();
					return simEngine.isGameOver();
				}

				@Override
				protected void done() {
					try {
						worldView.repaint();
						if (get().booleanValue()) {
							gameOver();
						} else {
							// nextTurn();
						}
					} catch (Exception ignore) {
						ignore.printStackTrace();
					}
				}
			}.execute();
		}
	}

	/**
	 * Return the state {started,stopped} of the simulator.
	 */
	public boolean isStarted() {
		return started;
	}

	/**
	 * Pause the simulation.
	 */
	public void pause() {
		started = false;
		simEngine.setPaused(true);
	}

	/**
	 * resume the simulation
	 */
	public void resume() {
		started = true;
		simEngine.setPaused(false);
	}

}
