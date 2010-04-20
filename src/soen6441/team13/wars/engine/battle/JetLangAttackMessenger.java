package soen6441.team13.wars.engine.battle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jetlang.channels.MemoryChannel;
import org.jetlang.core.Callback;
import org.jetlang.fibers.Fiber;
import org.jetlang.fibers.PoolFiberFactory;

/**
 * Implementation of AttackMessanger using JetLang library.
 */
public class JetLangAttackMessenger implements AttackMessenger {

	private final Fiber fiber;
	private final MemoryChannel<AttackMessage> channel;
	private final MemoryChannel<Boolean> channel2;

	public JetLangAttackMessenger() {
		ExecutorService service = Executors.newCachedThreadPool();
		PoolFiberFactory fact = new PoolFiberFactory(service);
		fiber = fact.create();
		fiber.start();
		channel = new MemoryChannel<AttackMessage>();
		channel2 = new MemoryChannel<Boolean>();

	}

	/* (non-Javadoc)
	 * @see soen6441.team13.wars.engine.battle.AttackMessanger#subscribe(org.jetlang.core.Callback)
	 */
	public void subscribe(Callback<AttackMessage> battleCallback) {
		channel.subscribe(fiber, battleCallback);
	}

	/* (non-Javadoc)
	 * @see soen6441.team13.wars.engine.battle.AttackMessanger#subscribe2(org.jetlang.core.Callback)
	 */
	public void subscribe2(Callback<Boolean> onMsg) {
		channel2.subscribe(fiber, onMsg);
	}

	/* (non-Javadoc)
	 * @see soen6441.team13.wars.engine.battle.AttackMessanger#publish(soen6441.team13.wars.engine.battle.AttackMessage)
	 */
	public void publish(AttackMessage msg) {
		channel.publish(msg);
	}

	/* (non-Javadoc)
	 * @see soen6441.team13.wars.engine.battle.AttackMessanger#publish(java.lang.Boolean)
	 */
	public void publish(Boolean msg) {
		channel2.publish(msg);
	}

}
