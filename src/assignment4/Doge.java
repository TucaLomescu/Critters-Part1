package assignment4;

/*
 * Do not change or submit this file.
 */
import assignment4.Critter.TestCritter;

public class Doge extends TestCritter {

    private int dogeReproCount = 0;

    public String toString() { return "D"; }

    public boolean fight(String not_used) { return false; }

    public void doTimeStep() {
        run(getRandomInt(7));
        setEnergy(getEnergy()+4);
        if(getEnergy() > 0){
            Doge child = new Doge();
            reproduce(child, Critter.getRandomInt(8));
            dogeReproCount ++;
        }
    }

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
