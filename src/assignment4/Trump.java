package assignment4;

/*
 * Do not change or submit this file.
 */
import assignment4.Critter.TestCritter;

public class Trump extends TestCritter {

    private int dir;
    private int tanLevel = 0

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
        tanLevel = (Params.start_energy - getEnergy());
        Critter.makeWallCritter(prevX, prevY);
    }

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
