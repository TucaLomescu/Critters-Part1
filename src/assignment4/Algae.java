package assignment4;

/*
 * Do not change or submit this file.
 */
import assignment4.Critter.TestCritter;

public class Algae extends TestCritter {


	/**
	 * Returns toString representation of Critter
	 * @return character rep of Critter
	 */
	public String toString() { return "@"; }

	/**
	 * Algae never fight, so returns false
	 * @return false bc no fighting
	 */
	public boolean fight(String not_used) { return false; }

	/**
	 * Set of instructions to do every time the world takes a timeStep
	 * For Algae, energy is added from photosynthesis
	 */
	public void doTimeStep() {
		setEnergy(getEnergy() + Params.photosynthesis_energy_amount - Params.rest_energy_cost);
	}
}
