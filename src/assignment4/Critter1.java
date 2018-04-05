package assignment4;

/* CRITTERS Critter1.java
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

import java.awt.*;

public class Critter1 extends Critter {

    private int dir;
    private int tanLevel = 0;

    public Critter1(){
        dir = getRandomInt(3)*2;
    }

    /**
     * Returns toString representation of Critter
     * @return character rep of Critter
     */
    public String toString() { return "1"; }

    /**
     * Trumps always fight
     * @return true bc always fights
     */
    public boolean fight(String not_used) {
        look(0, false);
        return true;
    }

    /**
     * Set of instructions to do every time the world takes a timeStep
     * For Trumps, energy is added from photosynthesis
     */
    public void doTimeStep(){
        //setEnergy(getEnergy() - Params.rest_energy_cost);
        //int prevX = getX_coord();
        //int prevY = getY_coord();
        walk(dir);
        tanLevel = (Params.start_energy - getEnergy());
        //Critter.makeWallCritter(prevX, prevY);
    }

    /**
     * Analyzes the statistics for Critter1 critters and outputs them to the console
     * @param trumps is a list of Trumps for gathering the statistics of
     */
    public static String runStats(java.util.List<Critter> trumps) {
        StringBuilder ret = new StringBuilder();
        int totalTanLevel = 0;
        ret.append("" + trumps.size() + " total Trumps    ");
        for(Critter c: trumps){
            Critter1 t = (Critter1) c;
            totalTanLevel += t.tanLevel;
        }
        ret.append(" Their total Tan Level is: " + totalTanLevel);
        return ret.toString();
    }

    @Override
    public CritterShape viewShape(){ return CritterShape.STAR;}

    @Override
    public javafx.scene.paint.Color viewFillColor(){return javafx.scene.paint.Color.ORANGE;}
}
