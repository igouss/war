package soen6441.team13.wars.domain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.HashSet;

import edu.uci.ics.jung.visualization.VisualizationServer;

/**
 * A continent is a collection of States. implements
 * VisualizationServer.Paintable
 */
public class Continent implements VisualizationServer.Paintable {

	/**
	 * render this continent in a GUI canvas as a rectangular object with a
	 * label
	 */
	@Override
	public void paint(Graphics g) {
		// this method draws the continent
		g.setColor(Color.CYAN);
		g.drawRect((int) location.getX(), (int) location.getY(), (int) size
				.getWidth(), (int) size.getHeight());
		g.drawString(name, (int) location.getX(), (int) location.getY());
	}

	/**
	 * inform GUI renderer to use a transform or not
	 */
	@Override
	public boolean useTransform() {
		return true;
	}

	private final String name;
	private Point2D location; // top-left position
	private Dimension2D size;

	/**
	 * returns true, if state has coordinates inside the continent.
	 */
	public boolean isMyState(State state) {
		Point2D p = state.getLocation();
		return (p.getX() >= location.getX() && p.getY() >= location.getY()
				&& p.getX() <= location.getX() + size.getWidth() && p.getY() <= location
				.getY()
				+ size.getHeight());
	}

	/**
	 * set the location of the continent on the GUI
	 * 
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Point2D location) {
		this.location = location;
	}

	/**
	 * @return the location
	 */
	public Point2D getLocation() {
		return location;
	}

	/**
	 * set the size of the rectangle representing this continent on the map
	 * 
	 * @param size
	 *            the size to set
	 */
	public void setSize(Dimension2D size) {
		this.size = size;
	}

	/**
	 * get the size of the rectangle representing this continent
	 * 
	 * @return the size
	 */
	public Dimension2D getSize() {
		return size;
	}

	/**
	 * Set of states that belong to this continent.
	 */
	HashSet<State> states = new HashSet<State>();

	/**
	 * construct a new continent with a name
	 */
	public Continent(String name) {
		this.name = name;
	}

	/**
	 * Add a state to the continent.
	 */
	public void addState(State state) {
		states.add(state);
	}

	/**
	 * Remove the state from the continent.
	 */
	public void removeState(State state) {
		states.remove(state);
	}

	/**
	 * get the states that make up this continent
	 * @return
	 */
	public HashSet<State> getStates() {
		return this.states;
	}

	/**
	 * the string representation of this continent is just
	 * its name
	 */
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Continent other = (Continent) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

}
