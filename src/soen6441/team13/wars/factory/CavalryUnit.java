package soen6441.team13.wars.factory;

import soen6441.team13.wars.domain.Army;

/**
 * This class represents a unit of soldiers
 * mounted on horseback 
 */
public class CavalryUnit extends Unit {

	CavalryUnit() {
		super(2, 2, 0);
	}

	@Override
	public void add(Army garrison) {
		garrison.addCavalery(1);
	}

}
