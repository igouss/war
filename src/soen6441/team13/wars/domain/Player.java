package soen6441.team13.wars.domain;

import java.awt.Color;
import java.util.Random;

/**
 * This class represents a Player/Country in the simulation it is a collection
 * of controlled States.
 */
public class Player implements java.io.Serializable {
	/**
	 * Magic number for persistence.
	 */
	private static final long serialVersionUID = -8150633953008315746L;

	/**
	 * Color assigned to the player.
	 */
	private Color color = null;

	/**
	 * Player name
	 */
	private final String name;

	/**
	 * construct a new player with a name
	 */
	public Player(String name) {
		this.name = name;

		Random randomGenerator = new Random();
		color = new Color(randomGenerator.nextInt(255), randomGenerator
				.nextInt(255), randomGenerator.nextInt(255));
	}

	/**
	 * Return color of player.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Return name of player
	 */
	public String getName() {
		return name;
	}

	/**
	 * string representation of this player is
	 * its name
	 */
	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		Player otherPlayer = (Player) obj;
		if (otherPlayer != null) {
			return name.equals(otherPlayer.getName());
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		if (name != null) {
			return name.hashCode();
		} else {
			return 0;
		}
	}
}
