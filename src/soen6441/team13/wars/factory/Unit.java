package soen6441.team13.wars.factory;

import soen6441.team13.wars.domain.Army;

/**
 * This class represents an abstract military unit
 * and all its common attributes
 */
public abstract class Unit {
	final int strength;
	final int requiredPoints;
	final int requiredIron;

	Unit(int strength, int requiredPoints, int requiredIron) {
		super();
		this.strength = strength;
		this.requiredPoints = requiredPoints;
		this.requiredIron = requiredIron;
	}

	/**
	 * get the strenght of this unit which is the effectiveness
	 * in combat
	 */
	public int getStrength() {
		return strength;
	}

	/**
	 * add a unit of this type to an army garrison 
	 */
	public abstract void add(Army garrison);

}
