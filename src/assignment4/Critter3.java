package assignment4;

/*
 * Do not change or submit this file.
 */
import assignment4.Critter.TestCritter;

public class Critter3 extends TestCritter {

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
        setEnergy(getEnergy() + 4 - Params.rest_energy_cost);
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
