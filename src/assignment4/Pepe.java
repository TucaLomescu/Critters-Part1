package assignment4;

/*
 * Do not change or submit this file.
 */
import assignment4.Critter.TestCritter;

public class Pepe extends TestCritter {

    public String toString() { return "P"; }

    public boolean fight(String not_used) {
        int rand = getRandomInt(2);
        return (rand == 0)? true : false;
    }

    public void doTimeStep() {
        setEnergy(getEnergy()+4);
        run(getRandomInt(8));
        run(getRandomInt(8));
    }
}
