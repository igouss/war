package soen6441.team13.wars.engine.movement;

import java.util.Set;

import soen6441.team13.wars.domain.State;

/**
 * This interface represents a heuristic function
 * to determine which of a set of possible moves is the
 * best one
 */
public interface MoveStrategy {
	/**
	 * pick a move out of a set of moves that is best
	 * according to a heuristic
	 */
	public State pick(Set<StateMove> moves);
}
