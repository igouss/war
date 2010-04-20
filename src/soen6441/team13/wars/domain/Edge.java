package soen6441.team13.wars.domain;

/**
 * This class represents an edge in the simulation it represents the possibility
 * of movement of troops or launching attacks between states that are 
 * connected by an edge.
 * 
 */
public class Edge implements java.io.Serializable {
	/**
	 * Magic number for persistence.
	 */
	private static final long serialVersionUID = -3541560442511500318L;

	/**
	 * Create an numbered edge.
	 * @param n is an edge number
	 */
	public Edge() {
	}

	@Override
	public String toString() {
		return "";
	}
}
