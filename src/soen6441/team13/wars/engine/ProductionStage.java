package soen6441.team13.wars.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import soen6441.team13.wars.domain.Continent;
import soen6441.team13.wars.domain.GameWorld;
import soen6441.team13.wars.domain.Player;
import soen6441.team13.wars.domain.State;

/**
 * Handles the production stage of the simulation. 
 * Responsible for calculating calling that which build factories/units.
 */
class ProductionStage extends Stage {

	/**
	 * Constructor
	 */
	public ProductionStage(GameWorld gameWorld) {
		super(gameWorld);
	}

	/**
	 * Implementation: Gathers stats, determines how many factories/units to build, calls build().
	 */
	@Override
	public void execute(Player player) {
		Collection<State> states = gameWorld.getStates(player);

		//phase one (pre-construction): calculate total iron supply and retrieve cities for building factories after
		int ironSupply = 0;
		Collection<State> cities = new ArrayList<State>();
		Set<Continent> continents = new HashSet<Continent>();

		for (State state : states) {
			if (state.hasIronMine()) {
				ironSupply += 100;
			}
			if (state.isCity()) {
				cities.add(state);
			}
			if (state.getContinent() != null) {
				continents.add(state.getContinent()); //get collection of unique continents that a player has states in for use in prod score calc
			}
		}

		//if no cities then player has lost their cities in battle, so no production can take place
		if (cities.size() == 0) {
			return;
		}

		//phase two: factory construction   
		for (State city : cities) {
			if (!city.hasStables() && ironSupply >= 200) {
				city.setHasStables(true);
				ironSupply -= 200;
				String result = city.getName() + " produced a stable";
				actionLogger.logProduction(player.getName()
						, result);
			} else if (!city.hasFoundry() && ironSupply >= 500) {
				city.setHasFoundry(true);
				ironSupply -= 500;
				String result = city.getName() + " produced a foundry";
				actionLogger.logProduction(player.getName()
						, result);
			}
		}

		//phase three: unit production
		int score = calcProductionScore(states.size(), cities.size(), continents, player);
		int unitsPerCity = score / cities.size();
		int ironPerCity = ironSupply / cities.size();
		for (State city : cities) {
			city.produce(unitsPerCity, ironPerCity, actionLogger);
		}

	}

	/**
	 * Calculates production score used in determining how many units to build per city.
	 */
	protected int calcProductionScore(int numStates, int numCities, Set<Continent> continents, Player player) {

		//get number of continents owned
		int continentsOwned = 0;
		for (Continent continent : continents) {
			HashSet<State> states = continent.getStates();
			boolean ownsAll = true;
			for (State state : states) {
				if (state.getPlayer() != player) {
					ownsAll = false;
					break;
				}
			}
			if (ownsAll) {
				++continentsOwned;
			}
		}

		return 15 + numStates + numCities * 2 + continentsOwned * 5;
	}
}
