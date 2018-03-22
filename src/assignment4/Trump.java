package assignment4;

/*
 * Do not change or submit this file.
 */
import assignment4.Critter.TestCritter;

public class Trump extends TestCritter {

    private int dir;
    private int tanLevel = 0;

    public Trump(){
        dir = getRandomInt(3)*2;
    }

    /**
     * Returns toString representation of Critter
     * @return character rep of Critter
     */
    public String toString() { return "T"; }

    /**
     * Trumps always fight
     * @return true bc always fights
     */
    public boolean fight(String not_used) { return true; }

    /**
     * Set of instructions to do every time the world takes a timeStep
     * For Trumps, energy is added from photosynthesis
     */
    public void doTimeStep(){
        setEnergy(getEnergy() - Params.rest_energy_cost);
        int prevX = getX_coord();
        int prevY = getY_coord();
        walk(dir);
        tanLevel = (Params.start_energy - getEnergy());
        Critter.makeWallCritter(prevX, prevY);
    }

    /**
     * Analyzes the statistics for Trump critters and outputs them to the console
     * @param trumps is a list of Trumps for gathering the statistics of
     */
    public static void runStats(java.util.List<Critter> trumps) {
        int totalTanLevel = 0;
        System.out.print("" + trumps.size() + " total Trumps    ");
        for(Critter c: trumps){
            Trump t = (Trump) c;
            totalTanLevel += t.tanLevel;
        }
        System.out.println(" Their total Tan Level is: " + totalTanLevel);
    }
}
