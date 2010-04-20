package soen6441.team13.wars.engine.battle;

import soen6441.team13.wars.domain.Army;
import soen6441.team13.wars.domain.State;

/**
 * Attack message hold message type, army size and target state.
 * It is used as a thread communication mechanism.
 */
public class AttackMessage {
	private final AttackMessageType attackMessageType;

	private final State targetState;
	private final State sourceState;
	private final Army army;
	private final String player;

	public AttackMessage(AttackMessageType attackMessageType, State targetState, String player) {
		this.attackMessageType = attackMessageType;
		this.targetState = targetState;
		sourceState = null;
		army = null;
		this.player = player;
	}

	public AttackMessage(AttackMessageType attackMessageType, State targetState, State sourceState,
			Army army, String player) {
		this.attackMessageType = attackMessageType;
		this.targetState = targetState;
		this.sourceState = sourceState;
		this.army = army;
		this.player = player;
	}

	public AttackMessageType getAttackMessageType() {
		return attackMessageType;
	}

	public State getTargetState() {
		return targetState;
	}

	public State getSourceState() {
		return sourceState;
	}

	public Army getArmy() {
		return army;
	}

	public String getPlayer() {
		return player;
	}
}
