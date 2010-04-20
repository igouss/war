package soen6441.team13.wars.engine.movement;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import soen6441.team13.wars.domain.State;

/**
 * This is random movement strategy which is probably
 * as good as any other naive heuristic we could come up with 
 */
public class RandomMoveStrategy implements MoveStrategy {
	private final static Random rnd = new Random(System.currentTimeMillis());

	/**
	 * Pick randomly out of the set of shortest
	 * paths to the front line
	 */
	@Override
	public State pick(Set<StateMove> moves) {
		if (0 == moves.size()) {
			return null;
		}
		ArrayList<StateMove> raMoves = new ArrayList<StateMove>(moves);
		int pick = rnd.nextInt(raMoves.size());
		return raMoves.get(pick).getMoveTo();
	}

}
