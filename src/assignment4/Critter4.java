package assignment4;

/* CRITTERS Critter4.java
 * EE422C Project 4 submission by
 * Luca Tomescu
 * LT22732
 * Section 15455
 * Amogh Agnihotri
 * AA73264
 * Section 15455
 * Slip days used: 0
 * Spring 2017
 */
import assignment4.Critter;
import javafx.scene.paint.Color;

public class Critter4 extends Critter {

    private int dogeReproCount = 0;

    /**
     * Returns toString representation of Critter
     * @return character rep of Critter
     */
    public String toString() { return "4"; }

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
        //setEnergy(getEnergy()+5-Params.rest_energy_cost);
        if(getEnergy() > 0){
            Critter4 child = new Critter4();
            reproduce(child, Critter.getRandomInt(8));
            dogeReproCount ++;
        }
    }

    /**
     * shows the statistics of Doges, including total Doges and reproduction count
     * @param doges list of all Critter4 instances
     */
    public static String runStats(java.util.List<Critter> doges) {
        StringBuilder ret = new StringBuilder();
        int totalRepro = 0;
        ret.append("" + doges.size() + " total Doges    ");
        for(Critter c: doges){
            Critter4 d = (Critter4) c;
            totalRepro += d.dogeReproCount;
        }
        ret.append(" Critter4 reproductive history: alive Doges have reproduced " + totalRepro + " times!\n");
        return ret.toString();
    }

    @Override
    public CritterShape viewShape(){ return CritterShape.DIAMOND;}

    @Override
    public javafx.scene.paint.Color viewFillColor(){ return Color.BEIGE;}
}
