package assignment4;

/*
 * Do not change or submit this file.
 */
import assignment4.Critter.TestCritter;

public class Doge extends TestCritter {

    public String toString() { return "D"; }

    public boolean fight(String not_used) { return false; }

    public void doTimeStep() {
        run(getRandomInt(7));
        setEnergy(getEnergy()+4);
        if(getEnergy() > 0){
            Doge child = new Doge();
            reproduce(child, Critter.getRandomInt(8));
        }
    }
}
