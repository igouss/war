package soen6441.team13.wars.engine;

import soen6441.team13.wars.domain.GameWorld;
import soen6441.team13.wars.domain.Player;

/**
 * This is the final stage of the simulation engine
 * state machine it is when one of the players
 * has defeating all the other players no further
 * action is possible
 */
public class WinnerStage extends Stage {

	public WinnerStage(GameWorld gameWorld) {
		super(gameWorld);
	}

	@Override
	public void execute(Player player) {
	}

	/**
	 * check if a player has won
	 */
	public boolean isWinner(Player player) {
		boolean isWiner = true;
		for (Player otherPlayer : gameWorld.getPlayers()) {
			if (!otherPlayer.equals(player) && gameWorld.getStates(otherPlayer).size() != 0) {
				isWiner = false;
				break;
			}
		}
		return isWiner;
	}
}
