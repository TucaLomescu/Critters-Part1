package assignment4;

/*
 * Do not change or submit this file.
 */
import assignment4.Critter.TestCritter;

public class Doge extends TestCritter {

    private int dogeReproCount = 0;

    /**
     * Returns toString representation of Critter
     * @return character rep of Critter
     */
    public String toString() { return "D"; }

    /**
     * Doges never fight, so returns false
     * @return false bc no fighting
     */
    public boolean fight(String not_used) { return false; }

    /**
     * Set of instructions to do every time the world takes a timeStep
     * For Doges, run in a random direction and reproduce if alive still
     */
    public void doTimeStep() {
        run(getRandomInt(7));
        setEnergy(getEnergy()+5-Params.rest_energy_cost);
        if(getEnergy() > 0){
            Doge child = new Doge();
            reproduce(child, Critter.getRandomInt(8));
            dogeReproCount ++;
        }
    }

    /**
     * shows the statistics of Doges, including total Doges and reproduction count
     * @param doges list of all Doge instances
     */
    public static void runStats(java.util.List<Critter> doges) {
        int totalRepro = 0;
        System.out.print("" + doges.size() + " total Doges    ");
        for(Critter c: doges){
            Doge d = (Doge) c;
            totalRepro += d.dogeReproCount;
        }
        System.out.println(" Doge reproductive history: alive Doges have reproduced " + totalRepro + " times!");
    }
}
