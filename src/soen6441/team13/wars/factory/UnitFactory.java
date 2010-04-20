package soen6441.team13.wars.factory;

import java.util.Collection;
import java.util.HashSet;

import soen6441.team13.wars.domain.State;
import soen6441.team13.wars.logger.ActionLogger;

/**
 * Factory to build (abstract implementations of) Unit.
 */
public class UnitFactory {
	/**
	 * Creates units based on the score of production units, and the amount of iron available.
	 * We produce as many of the "stronger" units as possible -> Artiller, Cavalry, then Infantry.
	 */
	public Collection<Unit> createUnit(State state,
			int productionUnits,
			int ironAmount, ActionLogger actionLogger) {
		Collection<Unit> units = new HashSet<Unit>();
		int art = 0;
		int inf = 0;
		int cav = 0;
		while (productionUnits > 0) {

			if (state.hasFoundry() && ironAmount >= 100 && productionUnits >= 3) {
				units.add(new ArtilleryUnit());
				ironAmount -= 100;
				productionUnits -= 3;
				art++;
			} else if (state.hasStables() && productionUnits >= 2) {
				units.add(new CavalryUnit());
				productionUnits -= 2;
				cav++;
			} else if (state.hasBarracks() && productionUnits >= 1) {
				units.add(new InfantryUnit());
				--productionUnits;
				inf++;
			}

		}
		String result = state.getName()
				+ " produced "
				+ inf
				+ " infantry, "
				+ cav
				+ " cavalry, "
				+ art
				+ " artillery.";
		actionLogger.logProduction(state.getPlayer().getName(),
				result);
		return units;
	}
}
