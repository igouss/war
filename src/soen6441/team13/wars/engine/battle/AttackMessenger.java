package soen6441.team13.wars.engine.battle;

import org.jetlang.core.Callback;

public interface AttackMessenger {

	/**
	 * Subscribe to messages of type AttackMessage.
	 */
	public void subscribe(Callback<AttackMessage> battleCallback);

	/**
	 * Subscribe to message of type, battle of over. 
	 */
	public void subscribe2(Callback<Boolean> onMsg);

	/**
	 * Send message of type attack message
	 */
	public void publish(AttackMessage msg);

	/**
	 * Sent notification that battle is over.
	 */
	public void publish(Boolean msg);

}