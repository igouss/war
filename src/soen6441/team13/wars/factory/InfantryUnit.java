package soen6441.team13.wars.factory;

import soen6441.team13.wars.domain.Army;

/**
 * This class represents a unit of foot soldiers
 * a.k.a riffle men
 */
public class InfantryUnit extends Unit {

	public InfantryUnit() {
		super(1, 1, 0);
	}

	/**
	 * add infantry to the army garrison
	 * in the arguments
	 */
	@Override
	public void add(Army garrison) {
		garrison.addInfantry(1);
	}
}
