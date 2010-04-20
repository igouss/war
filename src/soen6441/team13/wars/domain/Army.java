package soen6441.team13.wars.domain;

/**
 * Army is a movable unit that can attack other player armies.
 */
public class Army implements java.io.Serializable {

	/**
	 * Magic number for persistence.
	 */
	private static final long serialVersionUID = 2204285823348949063L;

	/**
	 * Number of infantry units.
	 */
	private int infantry = 0;

	/**
	 * Number of cavalry units.
	 */
	private int cavalry = 0;

	/**
	 * Number of artillery units.
	 */
	private int artillery = 0;

	/**
	 * Get number of artillery units that this army contains.
	 */
	public int getArtillery() {
		return artillery;
	}

	/**
	 * Update number of artillery units in this army.
	 */
	public void setArtillery(int artillery) {
		this.artillery = artillery;
	}

	/**
	 * Get number of cavalry units that this army contains.
	 */
	public int getCavalry() {
		return cavalry;
	}

	@Override
	public String toString() {
		return "Army [artillery=" + artillery + ", cavalry=" + cavalry
				+ ", infantry=" + infantry + "]";
	}

	/**
	 * Update number of cavalry units in this army.
	 */
	public void setCavalry(int cavalry) {
		this.cavalry = cavalry;
	}

	/**
	 * Get number of infantry units that this army contains.
	 */
	public int getInfantry() {
		return infantry;
	}

	/**
	 * Update number of infantry units in this army.
	 */
	public void setInfantry(int infantry) {
		this.infantry = infantry;
	}

	/**
	 * get the attack score based on a luck roll
	 * and a formula
	 */
	public double getAttackScore(double luck) {
		return infantry * luck + cavalry * 3 * luck + artillery * 5 * luck;
	}

	/**
	 * get the defense score based on a luck roll
	 * and a formula
	 */
	public double getDefenceScore(double luck) {
		return infantry * 2 * luck + cavalry * 2 * luck + artillery * 6 * luck;
	}

	/**
	 * reset the army to contain no units
	 */
	public void reset() {
		infantry = 0;
		cavalry = 0;
		artillery = 0;
	}

	/**
	 * add infantry to this army
	 */
	public void addInfantry(int infantry) {
		this.infantry += infantry;
	}

	/**
	 * add cavalry to this army
	 */
	public void addCavalery(int cavalry) {
		this.cavalry += cavalry;

	}

	/**
	 * add artillery to this army
	 */
	public void addArtillery(int artillery) {
		this.artillery += artillery;
	}

	/**
	 * total units in this army
	 */
	public int armySize() {
		return infantry + cavalry + artillery;
	}

	public int getStrenght() {
		return getArtillery() * 10 + getCavalry() * 5
				+ getInfantry();
	}
}
