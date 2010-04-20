package soen6441.team13.wars.engine.battle;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

import org.jetlang.core.Callback;

import soen6441.team13.wars.domain.Army;
import soen6441.team13.wars.domain.GameWorld;
import soen6441.team13.wars.domain.Player;
import soen6441.team13.wars.domain.State;
import soen6441.team13.wars.engine.Stage;

/**
 * Performs an attack on neighbors states that are controlled by another player
 */
public class BattleStage extends Stage {
	private final AttackMessenger messanger;
	private CountDownLatch reset;

	/**
	 * Create battle stage that uses jetlang messenger
	 */
	public BattleStage(GameWorld gameWorld, Luck luck) {
		this(gameWorld, luck, new JetLangAttackMessenger());
	}

	/**
	 * Create battle stage that uses user provided messenger.
	 */
	public BattleStage(GameWorld gameWorld, Luck luck, AttackMessenger messanger) {
		super(gameWorld);
		this.messanger = messanger;

		Callback<Boolean> runnable = new Callback<Boolean>() {
            @Override
			public void onMessage(Boolean msg) {
				reset.countDown();
			}
		};
		messanger.subscribe2(runnable);

		final Collection<State> states = gameWorld.getStates();
		for (State state : states) {
			messanger.subscribe(new BattleCallback(state, messanger, actionLogger, luck));
		}
	}

	/**
	 * Executes an attack logic
	 */
	@Override
	public void execute(Player player) {
		final Collection<State> states = gameWorld.getStates(player);

		reset = new CountDownLatch(states.size());

		for (State state : states) {
			final boolean isThreatened = state.isThreatened();
			boolean forceAttack = false;
			final int attackSkipCount = state.getAttackSkipCount();
			final boolean hasEnoughArmyToAttack = state.getArmySize() > 1;

			final Collection<State> enemyNeighbors = gameWorld.getEnemyNeighborStates(state);

			if (isThreatened && hasEnoughArmyToAttack && attackSkipCount > 5) {
				forceAttack = true;
			} else {
				state.setAttackSkipCount(attackSkipCount + 1);
			}
			if (forceAttack || (hasEnoughArmyToAttack && !isThreatened)) {
				state.setAttackSkipCount(0);

				if (enemyNeighbors.size() > 0) {
					Collection<State> targets = getTargetStates(state, enemyNeighbors);
					if (targets.size() > 0) {
						attack(state, targets, player.getName());
					}
				}
			}
			messanger.publish(new AttackMessage(AttackMessageType.BATTLE, state, player.getName()));
		}

		try {
			reset.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO: Iouri this is not a bug
		// The following code allows troops
		// that have moved to other states
		// because they have conquered a state to move
		// again in the movement stage
		// if you think they should not be able to move
		// again in the movement stage then delete it
		for (State state : states) {
			state.resetMove();
		}
	}

	/**
	 * Selects a target among enemy controlled neighbor states.
	 * If target not found returns null.
	 */
	private Collection<State> getTargetStates(State state, final Collection<State> enemyNeighbors) {

		LinkedList<State> targets = new LinkedList<State>();

		for (State enemyState : enemyNeighbors) {
			if (enemyState.getStrength() <= state.getStrength()) {
				targets.add(enemyState);
			}
		}
		return targets;
	}

	/**
	 * Perform an attack on a target state 
	 */
	public void attack(State state, Collection<State> targets, String attacker) {
		final Army army = state.getArmy();

		for (State enemyState : targets) {
			Army attackArmy = new Army();
			try {
				state.lock();
				final int enemyStrength = enemyState.getStrength();
				while (attackArmy.getStrenght() <= enemyStrength && state.getArmySize() > 0) {
					final int infantrySize = army.getInfantry();
					final int cavalrySize = army.getCavalry();
					final int artillerySize = army.getArtillery();
					if (infantrySize > 0) {
						attackArmy.addInfantry(1);
						army.setInfantry(infantrySize - 1);
					} else if (cavalrySize > 0) {
						attackArmy.addCavalery(1);
						army.setCavalry(cavalrySize - 1);
					} else if (artillerySize > 0) {
						attackArmy.addArtillery(1);
						army.setArtillery(artillerySize - 1);
					}
				}
			} finally {
				state.unlock();
			}
			final AttackMessage msg = new AttackMessage(AttackMessageType.ATTACK, enemyState, state, attackArmy,
					attacker);
			messanger.publish(msg);
		}
	}
}
