package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Luca Tomescu
 * LT22732
 * Section 15455
 * Amogh Agnihotri
 * AA73264
 * Section 15455
 * Slip days used: 0
 * Spring 2017
 */

import assignment4.Critter.TestCritter;

public class MyCritter6 extends TestCritter {
	
	@Override
	public void doTimeStep() {
	}

	@Override
	public boolean fight(String opponent) {
		run(getRandomInt(8));
		return false;
	}

	@Override
	public String toString () {
		return "5";
	}
}
