package soen6441.team13.wars.engine;

import soen6441.team13.wars.domain.GameWorld;
import soen6441.team13.wars.domain.Player;
import soen6441.team13.wars.logger.ActionLogger;
import soen6441.team13.wars.logger.ActionLoggerFactory;

/**
 * This is an abstract representing one of the stages or
 * states of the simulation engine state machine.
 */
public abstract class Stage {
	final protected GameWorld gameWorld;
	final protected ActionLogger actionLogger;

	public Stage(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
		this.actionLogger = ActionLoggerFactory.getInstance();
	}

	/**
	 * Execute game strategy.
	 */
	public abstract void execute(Player player);
}
