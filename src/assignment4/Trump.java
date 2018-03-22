package assignment4;

/*
 * Do not change or submit this file.
 */
import assignment4.Critter.TestCritter;

public class Trump extends TestCritter {

    private int dir;

    public Trump(){
        dir = getRandomInt(3)*2;
    }

    public String toString() { return "T"; }

    public boolean fight(String not_used) { return true; }

    public void doTimeStep(){
        //setEnergy(getEnergy());
        int prevX = getX_coord();
        int prevY = getY_coord();
        walk(dir);
        Critter.makeWallCritter(prevX, prevY);
    }
}
