package assignment4;

/* CRITTERS Critter2.java
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

public class Critter2 extends TestCritter {

    private boolean didTrumpPayForIt = false;

    /**
     * Returns toString representation of Critter
     * @return character rep of Critter
     */
    public String toString() { return "2"; }

    /**
     * Walls always fight (and win) because they are indomitable
     * @return true bc always fights
     */
    public boolean fight(String not_used) { return true; }

    /**
     * Set of instructions to do every time the world takes a timeStep
     * For Walls, energy is deducted for every step after it is built
     */
    public void doTimeStep() {
        setEnergy(getEnergy() - 9 - Params.rest_energy_cost);
    }

    /**
     * Analyzes the statistics for Critter2 critters and outputs them to the console
     * @param walls is the array of walls for which we are gathering data
     */
    public static void runStats(java.util.List<Critter> walls) {
        int totalHops = 0;
        System.out.print("" + walls.size() + " total Walls    ");
        System.out.print(" Did Critter1 pay for it? Answer: " + Boolean.toString(((Critter2) walls.get(0)).didTrumpPayForIt) +  "       ");

        System.out.println(" This wall costs $" + walls.size()*2 + " brazillion!");
    }

}
