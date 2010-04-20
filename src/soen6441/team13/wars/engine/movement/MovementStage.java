package soen6441.team13.wars.engine.movement;

import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import soen6441.team13.wars.domain.GameWorld;
import soen6441.team13.wars.domain.Player;
import soen6441.team13.wars.domain.State;
import soen6441.team13.wars.engine.Stage;

/**
 * This class controls the movement stage in the simulation
 * engine it decides how the troops will move in the simulation
 * the strategy is move troops through the shortest
 * path to the front lines where they can be of use
 * this is done by searching the graph breadth first
 */
public class MovementStage extends Stage {

	private MoveStrategy moveStrategy = new RandomMoveStrategy();

	public MovementStage(GameWorld gameWorld) {
		super(gameWorld);
	}

	/**
	 * allow an alternate strategy for the movement
	 * default is to pick randomly between shortest
	 * paths to the front line 
	 */
	public void setMoveStrategy(MoveStrategy moveStrategy) {
		this.moveStrategy = moveStrategy;
	}

	/**
	 * execute movement stage for a player given in
	 * the arguments
	 */
	@Override
	public void execute(Player player) {
		Collection<State> states = gameWorld.getStates(player);

		for (State state : states) {

			// if state is on front 
			// line don't bother moving its troops
			if (gameWorld.isOnFrontLine(state)) {
				continue;
			}

			// if we are we are in the rear echelon 
			// move our troops towards the front line
			// through the shortest path

			Set<State> seenStates = new HashSet<State>();
			seenStates.add(state);
			Collection<State> toAdd = gameWorld.getNeighborStates(state);
			seenStates.addAll(toAdd);

			Deque<StateMove> movesToLookAt = new LinkedList<StateMove>();
			for (State s : toAdd) {
				movesToLookAt.addLast(new StateMove(s, s, 1));
			}

			Set<StateMove> moves = bfsFindMove(seenStates, movesToLookAt);

			State moveTo = moveStrategy.pick(moves);
			// TODO : add more details
			// TODO : make two logging modes turn summary and verbose
			if (null == moveTo) {
				continue;
			}
			String result = "moving troops from "
					+ state.getName()
					+ " to " + moveTo.getName();
			actionLogger.logMove(player.getName(), result);
			state.moveArmy(moveTo, state.getArmy());
		}

		for (State state : states) {
			state.resetMove();
		}
	}

	/**
	 * helper method that does breadth first search
	 * of the graph to find the shortest moves to the
	 * frontline
	 */
	private Set<StateMove> bfsFindMove(Set<State> seenStates,
			Deque<StateMove> movesToLookAt) {

		Set<StateMove> moves = new HashSet<StateMove>();
		int smallestDistance = Integer.MAX_VALUE;

		while (!movesToLookAt.isEmpty()) {
			StateMove move = movesToLookAt.removeFirst();

			// we found something on the front line
			if (gameWorld.isOnFrontLine(move.getFinalDestination())) {
				smallestDistance =
						move.getDistance() < smallestDistance
						? move.getDistance()
						: smallestDistance;
				if (move.getDistance() <= smallestDistance) {
					moves.add(move);
					// else look deeper in the graph
				}
			} else {
				Collection<State> neighbors =
						gameWorld.
						getNeighborStates(move.getFinalDestination());
				for (State neighbor : neighbors) {
					// if we have seen this state skip it
					if (seenStates.contains(neighbor)) {
						continue;
					}

					seenStates.add(neighbor);
					movesToLookAt.addLast(new StateMove(move.getMoveTo(),
							neighbor,
							move.getDistance() + 1));
				}
			}
		}
		return moves;
	}
}
