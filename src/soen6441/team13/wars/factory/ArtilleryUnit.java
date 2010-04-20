package soen6441.team13.wars.factory;

import soen6441.team13.wars.domain.Army;

/**
 * This class represents an artillery unit
 * a.k.a cannons.
 */
public class ArtilleryUnit extends Unit {

	ArtilleryUnit() {
		super(3, 3, 1);
	}

	/**
	 * add an artillery unit to an army
	 */
	@Override
	public void add(Army garrison) {
		garrison.addArtillery(1);
	}

}
