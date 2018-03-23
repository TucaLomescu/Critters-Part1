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

            else if(input.contains("show")) {
                if(input.split(" ").length > 1) System.out.println("error processing: " + input);
                else Critter.displayWorld();
            }

            else if(input.contains("step")) {
                int steps = 0;
                try {
                    if (input.equals("step")) steps = 1;
                    else {
                        steps = Integer.parseInt(input.split(" ")[1]);
                    }
                    for (int i = 0; i < steps; i++) Critter.worldTimeStep();
                } catch(Exception c) {
                    System.out.println("error processing: " + input);
                }
            }

            else if(input.contains("seed")) {
                try {
                    int seed = Integer.parseInt(input.split(" ")[1]);
                    if(input.split(" ").length > 2) throw new Exception();
                    Critter.setSeed(seed);
                } catch(Exception e) {
                    System.out.println("error processing: " + input);
                }
            }

            else if(input.contains("make")) {
                int count = 1;
                String[] makeInput = input.split(" ");
                try {

                    if (makeInput.length == 3) {
                        count = Integer.parseInt(makeInput[2]);
                    }
                    if(makeInput.length > 3) throw new Exception();

                } catch(Exception e) {
                    System.out.println("error processing: " + input);
                }

                try {
                    for(int i = 0; i < count; i++)
                        Critter.makeCritter(makeInput[1]);
                }
                catch (Exception c) {
                    System.out.println("error processing: " + input);
                }

            }

            else if(input.contains("stats")) {
                String critType = input.split(" ")[1];
                try {
                    java.util.List<Critter> list = Critter.getInstances(critType);

                    Class tempTrump = Class.forName("assignment4.Critter1");
                    Class tempWall = Class.forName("assignment4.Critter2");
                    Class tempPepe = Class.forName("assignment4.Critter3");
                    Class tempDoge = Class.forName("assignment4.Critter4");
                    Class tempAlgae = Class.forName("assignment4.Algae");
                    Class tempCraig = Class.forName("assignment4.Craig");


                    java.util.HashSet<Class> crittersSeen = new HashSet<Class>();

                    for(Critter c: list) {
                        crittersSeen.add(c.getClass());
                    }

                    if(crittersSeen.size() > 1 || critType.equals("Critter")) {
                        //call universal case
                        Critter.runStats(list);
                    }

                    else {
                        Class tempC = Class.forName("assignment4." + critType);
                        if(tempC == tempTrump) Critter1.runStats(list);
                        else if(tempC == tempDoge) Critter4.runStats(list);
                        else if(tempC == tempPepe) Critter3.runStats(list);
                        else if(tempC == tempWall) Critter2.runStats(list);
                        else if(tempC == tempAlgae) Algae.runStats(list);
                        else if(tempC == tempCraig) Craig.runStats(list);
                    }
                }
                catch (Exception c) {
                    System.out.println("invalid class: " + critType);
                }
            }

            else {
                System.out.println("invalid command: " + input);
            }

        }
        /* Write your code above */
        //System.out.flush();

    }


}

