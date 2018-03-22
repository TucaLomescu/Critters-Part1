package assignment4;

/*
 * Do not change or submit this file.
 */
import assignment4.Critter.TestCritter;

public class Wall extends TestCritter {

    private boolean didTrumpPayForIt = false;

    public String toString() { return "W"; }

    public boolean fight(String not_used) { return false; }

    public void doTimeStep() {
        setEnergy(getEnergy()-10);
    }

    public static void runStats(java.util.List<Critter> walls) {
        int totalHops = 0;
        System.out.print("" + walls.size() + " total Walls    ");
        System.out.print(" Did Trump pay for it? Answer: " + Boolean.toString(((Wall) walls.get(0)).didTrumpPayForIt) +  "       ");

        System.out.println(" This wall costs $" + walls.size()*2 + " brazillion!");
    }

}
