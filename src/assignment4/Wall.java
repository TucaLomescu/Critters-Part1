package assignment4;

/*
 * Do not change or submit this file.
 */
import assignment4.Critter.TestCritter;

public class Wall extends TestCritter {

    public String toString() { return "W"; }

    public boolean fight(String not_used) { return false; }

    public void doTimeStep() {
        setEnergy(getEnergy()-50);
    }

}
