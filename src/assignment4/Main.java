package assignment4;
/* CRITTERS Main.java
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


import java.util.HashSet;
import java.util.Scanner;
import java.io.*;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) {

        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        Boolean isRunning = true;
        while(isRunning) {
            System.out.print("critters>");
            String input = kb.nextLine();
            input = input.trim();

            if(input.equals("quit")) isRunning = false;

            else if(input.equals("show")) Critter.displayWorld();

            else if(input.contains("step")) {
                int steps = 0;
                if(input.equals("step")) steps = 1;
                else steps = Integer.parseInt(input.split(" ")[1]);

                for(int i = 0; i < steps; i++) Critter.worldTimeStep();
                //add all new critters to collection

            }

            else if(input.contains("seed")) {
                int seed = Integer.parseInt(input.split(" ")[1]);
                Critter.setSeed(seed);
            }

            else if(input.contains("make")) {
                String[] makeInput = input.split(" ");
                int count = 1;

                if(makeInput.length == 3) count = Integer.parseInt(makeInput[2]);

                for(int i = 0; i < count; i++) {
                    try {
                        Critter.makeCritter(makeInput[1]);
                    }
                    catch (Exception c) {
                        System.out.println("error processing: " + input);
                    }
                }
                //add all new critters to collection

            }

            else if(input.contains("stats")) {
                String critType = input.split(" ")[1];
                try {
                    java.util.List<Critter> list = Critter.getInstances(critType);

                    Class tempTrump = Class.forName("assignment4.Trump");
                    Class tempWall = Class.forName("assignment4.Wall");
                    Class tempPepe = Class.forName("assignment4.Pepe");
                    Class tempDoge = Class.forName("assignment4.Doge");
                    Class tempAlgae = Class.forName("assignment4.Algae");
                    Class tempCraig = Class.forName("assignment4.Craig");


                    java.util.HashSet<Class> crittersSeen = new HashSet<Class>();

                    for(Critter c: list) {
                        crittersSeen.add(c.getClass());
                    }

                    if(crittersSeen.size() > 1) {
                        //call universal case
                        Critter.runStats(list);
                    }

                    else {
                        Class tempC = Class.forName("assignment4.Critter");
                        for(Class c: crittersSeen) {
                            tempC = c;
                        }

                        if(tempC == tempTrump) Trump.runStats(list);
                        else if(tempC == tempDoge) Doge.runStats(list);
                        else if(tempC == tempPepe) Pepe.runStats(list);
                        else if(tempC == tempWall) Wall.runStats(list);
                        else if(tempC == tempAlgae) Algae.runStats(list);
                        else if(tempC == tempCraig) Craig.runStats(list);
                    }
                }
                catch (Exception c) {

                }
            }

            else {
                System.out.println("invalid command: " + input);
            }

        }
        System.out.println("DONE");
        /* Write your code above */
        //System.out.flush();

    }


}

