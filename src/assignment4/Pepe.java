package assignment4;

/*
 * Do not change or submit this file.
 */
import assignment4.Critter.TestCritter;

public class Pepe extends TestCritter {

    private int hops = 0;


    /**
     * Returns toString representation of Critter
     * @return character rep of Critter
     */
    public String toString() { return "P"; }

    /**
     * Randomly decides whether Pepe fights or not
     * @return true or false, based on random int
     */
    public boolean fight(String not_used) {
        int rand = getRandomInt(2);
        return (rand == 0)? true : false;
    }

    /**
     * Set of instructions to do every time the world takes a timeStep
     * For Pepe, the critter runs twice resulting in a "hop" and gains back 4 energy
     */
    public void doTimeStep() {
        setEnergy(getEnergy() + 4 - Params.rest_energy_cost);
        run(getRandomInt(8));
        run(getRandomInt(8));
        hops += 2;
    }

    public static void runStats(java.util.List<Critter> pepes) {
        int totalHops = 0;
        System.out.print("" + pepes.size() + " total Pepes    ");
        for(Critter c: pepes){
            Pepe p = (Pepe) c;
            totalHops += p.hops;
        }
        System.out.println(" Their total hops are: " + totalHops);
    }
}
