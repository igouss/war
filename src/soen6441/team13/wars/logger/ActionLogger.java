package soen6441.team13.wars.logger;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import soen6441.team13.wars.domain.Action;
import soen6441.team13.wars.domain.ActionType;
import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.SortedList;

/**
 * Action logger is used to recored significant events (Actions) 
 * that we would display in a user interface.
 */
public class ActionLogger {
	private AtomicInteger sequenceNum;
	private final EventList<Action> eventList;

	public ActionLogger() {
		sequenceNum = new AtomicInteger(0);
		eventList =
				new SortedList<Action>(GlazedLists.threadSafeList(new BasicEventList<Action>()),
				new OrderComparator());
	}

	public void setLogState(List<Action> log) {
		int max = 0;

		try {
			eventList.getReadWriteLock().writeLock().lock();
			final int size = eventList.size();
			for (int i = 0; i < size; i++) {
				eventList.remove(0);
			}
		} finally {
			eventList.getReadWriteLock().writeLock().unlock();
		}
		for (Action action : log) {
			max = max < action.getOrder() ? action.getOrder() : max;
			addAction(action);
		}
		sequenceNum = new AtomicInteger(max + 1);
	}

	/**
	 * Holds all actions
	 */
	public EventList<Action> getEventList() {
		return eventList;
	}

	/**
	 * Inserts battle action into event list
	 */
	public void logBattle(String playerName, String result) {
		Action action = new Action(
				sequenceNum.getAndIncrement(),
				ActionType.ATTACK,
				playerName, result);
		addAction(action);
	}

	/**
	 * Inserts move action into event list
	 */
	public void logMove(String playerName, String result) {
		Action action = new Action(
				sequenceNum.getAndIncrement(),
				ActionType.MOVE,
				playerName,
				result);
		addAction(action);
	}

	/**
	 * Inserts production action into event list
	 */
	public void logProduction(String playerName, String result) {
		Action action = new Action(
				sequenceNum.getAndIncrement(),
				ActionType.PRODUCE,
				playerName, result);
		addAction(action);
	}

	/**
	 * Inserts recon action into event list
	 */
	public void logRecon(String playerName, String result) {
		Action action = new Action(
				sequenceNum.getAndIncrement(),
				ActionType.SCOUT,
				playerName,
				result);
		addAction(action);
	}

	/**
	 * add log actions to event list
	 */
	private void addAction(Action action) {
		eventList.getReadWriteLock().writeLock().lock();
		try {
			eventList.add(action);
		} finally {
			eventList.getReadWriteLock().writeLock().unlock();
		}
	}

	private static class OrderComparator implements Comparator<Action> {

		@Override
		public int compare(Action arg0, Action arg1) {
			return arg0.getOrder().compareTo(arg1.getOrder());
		}

	}
}
