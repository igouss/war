package soen6441.team13.wars.engine.battle;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jetlang.core.Callback;

import soen6441.team13.wars.domain.Army;
import soen6441.team13.wars.domain.State;
import soen6441.team13.wars.logger.ActionLogger;

/**
 * Callback for the state then state thread receives an attack message
 */
public class BattleCallback implements Callback<AttackMessage> {

	private final State state;
	private final AttackMessenger messanger;
	private final Luck luck;

	private final List<AttackMessage> attacks;
	private final ActionLogger actionLogger;

	public BattleCallback(State state, AttackMessenger messanger,
			ActionLogger actionLogger, Luck luck) {
		this.actionLogger = actionLogger;
		this.state = state;
		this.messanger = messanger;
		this.luck = luck;
		// added by troy to synchronize access to the attacks list
		// from multiple threads
		attacks = Collections.synchronizedList(
				new LinkedList<AttackMessage>());
	}

	@Override
	/**
	 * This method is called by jetlang when message is delivered to a state.
	 */
	public void onMessage(AttackMessage message) {
		final State targetState = message.getTargetState();
		final AttackMessageType messageType = message.getAttackMessageType();
		if (targetState.equals(state)) {
			if (messageType == AttackMessageType.ATTACK) {
				attacks.add(message);
			} else if (messageType == AttackMessageType.WIN) {
				actVictory(message);
			} else if (messageType == AttackMessageType.LOSE) {
				actDefeat(message);
			} else if (messageType == AttackMessageType.BATTLE) {
				if (attacks.size() > 0) {
					actBattle(attacks, message.getPlayer());
					attacks.clear();
				}
				messanger.publish(true);
			} else {
				throw new RuntimeException("Unknown message type:" + messageType.toString());
			}
		}
	}

	/**
	 * Handle defeat situation.
	 */
	private void actDefeat(AttackMessage message) {
		String result = message.getSourceState().getName()
				+ " attacked "
				+ message.getTargetState().getName()
				+ " and lost "
				+ message.getArmy();
		actionLogger.logBattle(message.getPlayer(), result);
		state.handleDefeat(message.getSourceState(), message.getArmy());
	}

	/**
	 * Handle victory.
	 */
	private void actVictory(AttackMessage message) {
		String result = message.getSourceState().getName()
				+ " attacked "
				+ message.getTargetState().getName()
				+ " and won ";
		actionLogger.logBattle(message.getPlayer(), result);
		state.handleVictory(message.getSourceState(), message.getArmy());
	}

	/**
	 * Perform battle on a separate thread and send result of the battle
	 * to an attacker.
	 */
	private void actBattle(List<AttackMessage> attacks, String player) {
		for (AttackMessage attackMessage : attacks) {
			try {
				attackMessage.getSourceState().lock();
				attackMessage.getTargetState().lock();

				final Army attackArmy = attackMessage.getArmy();
				final Army defenceArmy = getDefenceArmy(attackArmy);

				final State attackerState = attackMessage.getSourceState();

				double attackScore = attackArmy.getAttackScore(luck.getLuck());
				double defenceScore = defenceArmy.getDefenceScore(luck.getLuck());

				AttackMessageType result = (defenceScore < attackScore) ? AttackMessageType.WIN
						: AttackMessageType.LOSE;
				messanger.publish(new AttackMessage(result, attackerState, state, defenceArmy, player));
			} finally {
				attackMessage.getTargetState().unlock();
				attackMessage.getSourceState().unlock();
			}
		}

	}

	/**
	 * Create a defence army that will fight an attacker.
	 */
	private Army getDefenceArmy(Army attackArmy) {
		Army army = new Army();
		try {
			state.lock();
			Army stateArmy = state.getArmy();
			while (army.getStrenght() < attackArmy.getStrenght() && state.getArmySize() > 0) {
				final int infantrySize = stateArmy.getInfantry();
				final int cavalrySize = stateArmy.getCavalry();
				final int artillerySize = stateArmy.getArtillery();
				if (infantrySize > 0) {
					army.addInfantry(1);
					stateArmy.setInfantry(infantrySize - 1);
				} else if (cavalrySize > 0) {
					army.addCavalery(1);
					stateArmy.setCavalry(cavalrySize - 1);
				} else if (artillerySize > 0) {
					army.addArtillery(1);
					stateArmy.setArtillery(artillerySize - 1);
				}
			}
		} finally {
			state.unlock();
		}
		return army;
	}
}
