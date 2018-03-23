package assignment4;

/* CRITTERS Critter3.java
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
import assignment4.Critter;

public class Critter3 extends Critter {

    private int hops = 0;


    /**
     * Returns toString representation of Critter
     * @return character rep of Critter
     */
    public String toString() { return "3"; }

    /**
     * Randomly decides whether Critter3 fights or not
     * @return true or false, based on random int
     */
    public boolean fight(String not_used) {
        int rand = getRandomInt(2);
        return (rand == 0)? true : false;
    }

    /**
     * Set of instructions to do every time the world takes a timeStep
     * For Critter3, the critter runs diagonally resulting in a "hop" and gains back 4 energy
     */
    public void doTimeStep() {
        //setEnergy(getEnergy() + 4 - Params.rest_energy_cost);
        run(1);
        hops ++;
    }

    /**
     * shows statistics of Pepes, including number of Pepes and total hops
     * @param pepes list of all Critter3 instances
     */
    public static void runStats(java.util.List<Critter> pepes) {
        int totalHops = 0;
        System.out.print("" + pepes.size() + " total Pepes    ");
        for(Critter c: pepes){
            Critter3 p = (Critter3) c;
            totalHops += p.hops;
        }
        System.out.println(" Their total hops are: " + totalHops);
    }
}
