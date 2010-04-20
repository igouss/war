package soen6441.team13.wars.engine;

import java.util.Collection;

import soen6441.team13.wars.domain.GameWorld;
import soen6441.team13.wars.domain.Player;
import soen6441.team13.wars.domain.State;

/**
 * This class represents the scouting stage.
 * It fives attack recommendations that the
 * battle stage should use. 
 */
public class ReconStage extends Stage {

	public ReconStage(GameWorld gameWorld) {
		super(gameWorld);
	}

	/**
	 * Execute recon strategy
	 */
	@Override
	public void execute(Player player) {
		// Identify threatened states
		final Collection<State> playerStates = gameWorld.getStates(player);
		for (State state : playerStates) {
			final Collection<State> enemyNeighborStates = gameWorld.getEnemyNeighborStates(state);
			state.isThreatened(false);
			for (State enemyState : enemyNeighborStates) {

				if (enemyState.getStrength() * 2 > state.getStrength()) {
					state.isThreatened(true);
					// log decision to attack
					String result = state.getName()
							+ " scouted "
							+ enemyState.getName()
							+ " will attack";
					actionLogger.logRecon(player.getName(),
							result);
					break;
				}
			}
		}
	}
}
