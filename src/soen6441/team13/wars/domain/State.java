package soen6441.team13.wars.domain;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import soen6441.team13.wars.factory.Unit;
import soen6441.team13.wars.factory.UnitFactory;
import soen6441.team13.wars.logger.ActionLogger;

/**
 * 
 * This class represents a State which is a the smallest territorial unit in the
 * simulation.
 * 
 */
public class State implements java.io.Serializable {

	/**
	 * Magic number for persistence.
	 */
	private static final long serialVersionUID = 280065315641092237L;

	private final Lock lock;

	/**
	 * State id
	 */
	private final int id;

	/**
	 * Player who controls this state
	 */
	private Player player;

	private boolean hasBarracks = false;
	private boolean hasFoundry = false;
	private boolean hasStables = false;
	private boolean isCity = false;
	private Army army = null;
	Army movedThisTurn = null;

	private String name = null;
	private boolean hasIronMine;

	private final UnitFactory unitFactory;

	/**
	 * Location on the UI graph
	 */
	private Point2D location;

	private Continent continent;

	private boolean isThreatened;

	private int attackSkipCount;

	/**
	 * Create new state.
	 * 
	 * @param n
	 *            state id
	 * @param player
	 *            player who controls this state.
	 * @param gameWorld
	 */
	public State(int n, Player player, UnitFactory unitFactory) {
		id = n;
		this.unitFactory = unitFactory;
		name = "State " + id;
		army = new Army();
		movedThisTurn = new Army();
		this.player = player;
		lock = new ReentrantLock();
	}

	/**
	 * Deal with attack failure. Kill all but one unit.
	 */
	public void handleDefeat(State winerState, Army army) {

		// Do nothing since we already subtracted army size when we created
		// attack army in BattleStage.attack
	}

	public void lock() {
		lock.lock();
	}

	public void unlock() {
		lock.unlock();
	}

	/**
	 * returns number represented the simple state Strength
	 * 
	 * @return int
	 */
	public int getStrength() {
		return army.getStrenght();
	}

	/**
	 * Return owner of this state.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Set the owner/player of this state.
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * @return true if this state has an iron mine
	 */
	public boolean hasIronMine() {
		return hasIronMine;
	}

	/**
	 * remove or add an iron mine to this state
	 */
	public void setHasIronMine(boolean hasIronMine) {
		this.hasIronMine = hasIronMine;
	}

	/**
	 * @return true if this state has barracks
	 */
	public boolean hasBarracks() {
		return hasBarracks;
	}

	/**
	 * add or remove barracks from this state
	 */
	public void setHasBarracks(boolean hasBarracks) {
		this.hasBarracks = hasBarracks;
	}

	/**
	 * @return true if this state has a foundry and can build artillery
	 */
	public boolean hasFoundry() {
		return hasFoundry;
	}

	/**
	 * add or remove a foundry and the capability to make artillery
	 */
	public void setHasFoundry(boolean hasFoundry) {
		this.hasFoundry = hasFoundry;
	}

	public int getAttackSkipCount() {
		return attackSkipCount;
	}

	public void setAttackSkipCount(int attackSkipCount) {
		this.attackSkipCount = attackSkipCount;
	}

	/**
	 * 
	 * @return if this state has a stable and can make cavalry
	 */
	public boolean hasStables() {
		return hasStables;
	}

	/**
	 * add or remove a stable and the capability to create cavalry
	 */
	public void setHasStables(boolean hasStables) {
		this.hasStables = hasStables;
	}

	/**
	 * @return true if this state is a city and can produce military units
	 */
	public boolean isCity() {
		return isCity;
	}

	/**
	 * make this state a city and allow it to produce military units
	 */
	public void setCity(boolean isCity) {
		this.isCity = isCity;

		// default: every city will have barracks
		if (isCity) {
			this.hasBarracks = true;
		} else {
			this.hasBarracks = false;
		}

	}

	/**
	 * set the troops that are located on this state
	 */
	public void setArmy(Army garrison) {
		this.army = garrison;
	}

	/**
	 * @return the name of this state
	 */
	public String getName() {
		return name;
	}

	/**
	 * change the name of this state
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * get the troops that are located on this state
	 */
	public Army getGarrison() {
		return army;
	}

	/**
	 * the unique id of this state
	 */
	public int getID() {
		return id;
	}

	/**
	 * the string representation of this state is its name and id
	 */
	@Override
	public String toString() {
		return getName() + " " + id;
	}

	/**
	 * set the location of this state on the map GUI
	 */
	public void setLocation(Point2D location) {
		this.location = location;
	}

	/**
	 * return the location of this state on the map GUI
	 */
	public Point2D getLocation() {
		return location;
	}

	/**
	 * set the continent to which this state belongs
	 */
	public void setContinent(Continent continent) {
		this.continent = continent;
	}

	/**
	 * get the continent to which this state belongs
	 * 
	 * @return
	 */
	public Continent getContinent() {
		return continent;
	}

	/**
	 * Calls the createUnit method of our unit factory instance. Will produce
	 * implementations of the abstract Unit class.
	 */
	public void produce(int productionUnits, int ironAmount,
			ActionLogger actionLogger) {
		final Collection<? extends Unit> units = unitFactory.createUnit(this,
				productionUnits, ironAmount, actionLogger);
		for (Unit unit : units) {
			unit.add(army);
		}
	}

	/**
	 * get the attack score of this states
	 * army on this state based
	 * on a luck roll and a formula
	 */
	public double getAttackScore(double luck) {
		return army.getAttackScore(luck);
	}

	/**
	 * get the defense score of this states army
	 * based on a luck roll and a formula
	 */
	public double getDefenceScore(double luck) {
		return army.getDefenceScore(luck);
	}

	/**
	 * Deal with successful attack
	 */
	public void handleVictory(State loserState, Army army) {
		try {
			loserState.lock();
			loserState.killArmy();
			moveArmy(loserState, army);
		} finally {
			loserState.unlock();
		}
	}

	/**
	 * eliminate this states army it has been
	 * defeated in battle ha!
	 */
	public void killArmy() {
		army.reset();
	}

	/**
	 * move this states army to another state
	 */
	public void moveArmy(State destinationState, Army armyToMove) {
		if (null == destinationState) {
			return;
		}

		try {
			destinationState.lock();

			final Army otherStateArmy = destinationState.getGarrison();

			destinationState.setPlayer(getPlayer());

			if (armyToMove.getInfantry() > 1) {
				int keptInfantry = 0 < movedThisTurn.getInfantry() ? movedThisTurn
						.getInfantry() : 1;

				int movedInfantry = army.getInfantry() - keptInfantry;
				movedInfantry = 0 < movedInfantry ? movedInfantry : 0;
				otherStateArmy.addInfantry(movedInfantry);
				destinationState.movedThisTurn.addInfantry(movedInfantry);

				moveCavalry(destinationState, otherStateArmy, 0);

				moveArtillery(destinationState, otherStateArmy, 0);
				army.setInfantry(keptInfantry);
			} else if (army.getCavalry() > 1) {
				moveCavalry(destinationState, otherStateArmy, 1);
				moveArtillery(destinationState, otherStateArmy, 0);
			} else if (army.getArtillery() > 1) {
				moveArtillery(destinationState, otherStateArmy, 1);
			}
		} finally {
			destinationState.unlock();
		}

	}

	/**
	 * move artileery helper function
	 */
	private void moveArtillery(State otherState, final Army otherStateArmy,
			int keep) {

		int keptArtillery = 0 < movedThisTurn.getCavalry() ? movedThisTurn
				.getCavalry() : keep;

		int movedArtillery = army.getArtillery()
				- movedThisTurn.getArtillery() - keep;
		movedArtillery = 0 < movedArtillery ? movedArtillery : 0;
		otherStateArmy.addArtillery(movedArtillery);
		otherState.movedThisTurn.addArtillery(movedArtillery);
		army.setArtillery(keptArtillery);
	}

	/**
	 * reset the armies that have been moved this turn 
	 * to none
	 */
	public void resetMove() {
		movedThisTurn.reset();
	}

	/**
	 * move cavalry helper function
	 */
	private void moveCavalry(State otherState, final Army otherStateArmy,
			int keep) {

		int keptCavalry = 0 < movedThisTurn.getCavalry() ? movedThisTurn
				.getCavalry() : keep;

		int movedCavalry = army.getCavalry() - movedThisTurn.getCavalry()
				- keep;
		movedCavalry = 0 < movedCavalry ? movedCavalry : 0;
		otherStateArmy.addCavalery(movedCavalry);
		otherState.movedThisTurn.addCavalery(movedCavalry);
		army.setCavalry(keptCavalry);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		State other = (State) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	/**
	 * get this states army size in total units
	 */
	public int getArmySize() {
		return army.armySize();
	}

	public void isThreatened(boolean isThreatened) {
		this.isThreatened = isThreatened;
	}

	public boolean isThreatened() {
		return isThreatened;
	}

	public Army getArmy() {
		return army;
	}
}
