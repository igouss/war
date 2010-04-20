package soen6441.team13.wars.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import ca.odell.glazedlists.EventList;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

/**
 * 
 * The entire simulation world it is a collection of players, continents, 
 * states and edges.
 *
 */
public class GameWorld implements Serializable {
	/**
	 * magic serialization number
	 */
	private static final long serialVersionUID = -6359502992802181027L;

	/**
	 * Set of players that control states on the map.
	 */
	private final LinkedList<Player> players;

	/**
	 * Set of players that control states on the map.
	 */
	private final LinkedList<Continent> continents;

	/**
	 * Holds graph representation of the world.
	 */
	private Graph<State, Edge> graph;

	/**
	 * For build 2 only - allow to do not run the battle stage
	 */
	private boolean doBattle;

	/**
	 * This is the action log which needs to be persisted
	 */

	private final LinkedList<Action> actionLog = new LinkedList<Action>();

	public LinkedList<Action> getActionLog() {
		return actionLog;
	}

	public void setActionLog(EventList<Action> actionLog) {
		this.actionLog.clear();
		for (Action action : actionLog) {
			this.actionLog.add(action);
		}
	}

	/**
	* Create new empty world.
	*/
	public GameWorld() {
		players = new LinkedList<Player>();
		continents = new LinkedList<Continent>();
		graph = new UndirectedSparseGraph<State, Edge>();
		doBattle = false;

	}

	/**
	 * Return graph model of the world.
	 */
	public Graph<State, Edge> getGraph() {
		return graph;
	}

	/**
	 * Return all players in the game world
	 */
	public LinkedList<Player> getPlayers() {
		return players;
	}

	/**
	 * Add new player to the game
	 */
	public void addPlayer(Player player) {
		if (!players.contains(player)) {
			players.add(player);
		}
	}

	/**
	 * Make new world graph
	 */
	public void clear() {
		graph = new UndirectedSparseGraph<State, Edge>();
	}

	/**
	 * Add continent to current game world.
	 */
	public void addContinent(Continent continent) {
		continents.add(continent);
	}

	/**
	 * Return all continents in this game world.
	 */
	public LinkedList<Continent> getContinents() {
		return continents;
	}

	/**
	 * Checks if the world contains this player.
	 */
	public boolean hasPlayer(Player player) {
		return players.contains(player);
	}

	/**
	 * Returns all the states in the graph.
	 */
	public Collection<State> getStates() {
		return getGraph().getVertices();
	}

	/**
	 * Return states that belong to the player
	 */
	public Collection<State> getStates(Player player) {
		Collection<State> allStates = getStates();
		HashSet<State> playerStates = new HashSet<State>();

		for (State state : allStates) {
			if (state.getPlayer().equals(player)) {
				playerStates.add(state);
			}
		}
		return playerStates;
	}

	/**
	 * Determine if a player has lost meaning
	 * he controls no states
	 */
	public boolean isLoser(Player player) {
		return getStates(player).isEmpty();
	}

	/**
	 * determine if a state is on the front line of the battle
	 * meaning it is under threat of attack
	 * @return true if there is a neighbor state controlled by
	 * by an enemy player
	 */
	public boolean isOnFrontLine(State state) {
		boolean result = false;
		Player player = state.getPlayer();
		Collection<State> states = getNeighborStates(state);
		for (State neighbor : states) {
			if (!neighbor.getPlayer().equals(player)) {
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * Return a collection of neighbor states
	 */
	public Collection<State> getNeighborStates(State state) {
		return graph.getNeighbors(state);
	}

	/**
	 * Return a collection of neighbor states controlled by another player
	 */
	public Collection<State> getEnemyNeighborStates(State playerState) {
		final Player player = playerState.getPlayer();
		final Collection<State> neighbors = getNeighborStates(playerState);

		LinkedList<State> enemyStates = new LinkedList<State>();

		for (State state : neighbors) {
			if (!state.getPlayer().equals(player)) {
				enemyStates.add(state);
			}
		}

		return enemyStates;
	}

	/**
	 * @param doBattle the doBattle to set
	 */
	public void setDoBattle(boolean doBattle) {
		this.doBattle = doBattle;
	}

	/**
	 * @return the doBattle
	 */
	public boolean getDoBattle() {
		return doBattle;
	}

}
