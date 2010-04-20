package soen6441.team13.wars.engine.battle;

import java.util.Random;

/**
 * Class that computes luck factor.
 */
public class Luck {

	/**
	 * Luck generator
	 */
	private final static Random rnd = new Random(System.currentTimeMillis());

	/**
	 * Return luck factor [0.1..1]
	 */
	public double getLuck() {
		return rnd.nextDouble() + 0.1;
	}
}
