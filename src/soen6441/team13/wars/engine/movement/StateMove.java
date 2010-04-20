package soen6441.team13.wars.engine.movement;

import soen6441.team13.wars.domain.State;

/**
 * This class represents a potential move to be carried
 * out in the movement stage.
 */
public class StateMove {
	private final State moveTo;
	private final State finalDestination;
	private final int distance;

	public StateMove(State moveTo, State finalDestination, int distance) {
		super();
		this.moveTo = moveTo;
		this.finalDestination = finalDestination;
		this.distance = distance;
	}

	/**
	 * get which state will this move actually go to
	 * @return
	 */
	public State getMoveTo() {
		return moveTo;
	}

	/**
	 * where is the ultimate destination on the frontline
	 * that this move represents
	 * @return
	 */
	public State getFinalDestination() {
		return finalDestination;
	}

	/**
	 * get how many states do I have to move through
	 * to get to the front lines
	 * @return
	 */
	public int getDistance() {
		return distance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + distance;
		result = prime
				* result
				+ ((finalDestination == null) ? 0 : finalDestination.hashCode());
		result = prime * result + ((moveTo == null) ? 0 : moveTo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		StateMove other = (StateMove) obj;
		if (distance != other.distance) {
			return false;
		}
		if (finalDestination == null) {
			if (other.finalDestination != null) {
				return false;
			}
		} else if (!finalDestination.equals(other.finalDestination)) {
			return false;
		}
		if (moveTo == null) {
			if (other.moveTo != null) {
				return false;
			}
		} else if (!moveTo.equals(other.moveTo)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "StateMove [distance=" + distance + ", finalDestination="
				+ finalDestination + ", moveTo=" + moveTo + "]";
	}

}
