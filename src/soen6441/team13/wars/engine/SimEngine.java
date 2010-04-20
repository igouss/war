package soen6441.team13.wars.engine;

import java.util.LinkedList;

import org.apache.commons.collections15.buffer.CircularFifoBuffer;

import soen6441.team13.wars.domain.GameWorld;
import soen6441.team13.wars.domain.Player;
import soen6441.team13.wars.engine.battle.BattleStage;
import soen6441.team13.wars.engine.battle.Luck;
import soen6441.team13.wars.engine.movement.MoveStrategy;
import soen6441.team13.wars.engine.movement.MovementStage;

/**
 * This is the simulation engine that drives the game
 * it uses the state design pattern
 */
public class SimEngine {
	private final LinkedList<Stage> stages = new LinkedList<Stage>();
	private final WinnerStage winingState;
	private final CircularFifoBuffer<Player> playerCycle;
	private final MovementStage movementStage;
	private final GameWorld gameWorld;
	private boolean paused;

	/**
	 * set an alternate movement strategy
	 */
	public void setMoveStrategy(MoveStrategy moveStrategy) {
		movementStage.setMoveStrategy(moveStrategy);
	}

	private boolean gameOver = false;
	private Player winner;

	/**
	 * construct a new simulation engine
	 */
	public SimEngine(GameWorld gameWorld, Luck luck) {
		this.gameWorld = gameWorld;
		playerCycle = new CircularFifoBuffer<Player>(gameWorld.getPlayers());

		winingState = new WinnerStage(gameWorld);
		stages.add(new ProductionStage(gameWorld));
		stages.add(new ReconStage(gameWorld));

		if (gameWorld.getDoBattle()) {
			stages.add(new BattleStage(gameWorld, luck));
		}
		movementStage = new MovementStage(gameWorld);
		stages.add(movementStage);
		stages.add(winingState);
	}

	/**
	 * begin executing the state machine of the simulation
	 * with a given game world engine.
	 */
	public void execute() {

		if (paused) {
			return;
		}

		for (Player player : playerCycle) {
			// don't bother if the player lost
			if (gameWorld.isLoser(player)) {
				continue;
			}

			for (Stage stage : stages) {
				stage.execute(player);
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException ignore) {
			}
			if (winingState.isWinner(player)) {
				gameOver = true;
				winner = player;
			}
		}
	}

	/**
	 * @return true if the game is over
	 */
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * Return the player who won the simulation.
	 */
	public Player getWinner() {
		return winner;
	}

	/**
	 * boolean settting for pausing the game
	 */
	public void setPaused(boolean paused) {
		this.paused = paused;
	}

}
