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

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {

	public enum CritterShape{
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}

	public javafx.scene.paint.Color viewColor() { return Color.BLACK;}

	public javafx.scene.paint.Color viewOutlineColor() { return Color.BLACK;}
	public javafx.scene.paint.Color viewFillColor() { return Color.WHITE;}

	public abstract CritterShape viewShape();



	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	private static java.util.Random rand = new java.util.Random();

	/**
	 * Creates random integer between 0 and max noninclusive
	 * @param max number up to which integer can be made
	 * @return the random integer
	 */
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}

	/**
	 * sets seed so that random integers are the same for runs with that seed
	 * @param new_seed seed integer value
	 */
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}


	/* a one-character long string that visually depicts your critter in the ASCII interface */

	/**
	 * super class for displaying string version of the critter
	 * @return critter character
	 */
	public String toString() { return ""; }

	private int energy = 0;
	protected int getEnergy() { return energy; }

	private int x_coord;
	private int y_coord;

	/**
	 * Moves critter one unit in any direction
	 * @param direction determines which way the critter moves
	 */
	protected final void walk(int direction) {
		energy -= Params.walk_energy_cost;
		changeLoc(direction, true);
	}

	/**
	 * Moves critter two units in any direction
	 * @param direction determines which way the critter moves
	 */
	protected final void run(int direction) {
		energy -= Params.run_energy_cost;
		energy -= Params.rest_energy_cost;
		changeLoc(direction, false);
	}

	/**
	 * Allows critters to produce offspring
	 * @param offspring is the new baby critter created by the parent
	 * @param direction where the new critter is placed in relation to the parent
	 */
	protected final void reproduce(Critter offspring, int direction) {
		if(energy >= Params.min_reproduce_energy){
			offspring.energy = energy/2;
			double fEnergy = ((double) energy)/2.0;
			energy /= 2;
			if(fEnergy%1 > 0){
				energy ++;
			}
			offspring.x_coord = x_coord;
			offspring.y_coord = y_coord;
			offspring.changeLoc(direction, true);
			babies.add(offspring);
		}
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String opponent);

	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try {
			Class c = Class.forName("assignment4." + critter_class_name);
			Critter critter = (Critter) c.newInstance();
			critter.energy = Params.start_energy;
			critter.x_coord = getRandomInt(Params.world_width);
			critter.y_coord = getRandomInt(Params.world_height);
			population.add(critter);

		}catch (Exception c){
			throw new InvalidCritterException(critter_class_name);
		}
	}

	/*
	public static void makeWallCritter(int x, int y){
		Critter2 wall = new Critter2();
		wall.setEnergy(1000);
		wall.setX_coord(x);
		wall.setY_coord(y);
		babies.add(wall);
	}
	*/

	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		try {
			Class tempC = Class.forName("assignment4." + critter_class_name);
			//Critter tempCritter = (Critter) tempC.newInstance();

			for(Critter c: population) {
				if (c.getClass() == tempC || critter_class_name.equals("Critter")){
					result.add(c);
				}
			}

		}
		catch (Exception c) {
			throw new InvalidCritterException(critter_class_name);
		}

		return result;
	}

	/**
	 * Creates a new critter instance for testing purposes
	 * @param critter_class_name the type of critter to be created
	 * @return the critter that was made
	 * @throws InvalidCritterException
	 */
	public static Critter makeTestCritter(String critter_class_name) throws InvalidCritterException{
		try {
			Class c = Class.forName(critter_class_name);
			Critter critter = (Critter) c.newInstance();
			return critter;
		}catch (Exception c){
			throw new InvalidCritterException(critter_class_name);
		}
	}

	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static String runStats(List<Critter> critters) {
		StringBuilder ret = new StringBuilder();
		ret.append("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			ret.append(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		ret.append("\n");
		return ret.toString();
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		for(int i = population.size() - 1; i > 0; i--){
			population.remove(i);
		}
	}

	/**
	 * Simulates one time step for every Critter in the population
	 */
	public static void worldTimeStep() {
		List<Critter> death = new java.util.ArrayList<>();
		for(Critter c: babies){
			population.add(c);
		}
		babies.clear();
		for(Critter c: population){
			c.doTimeStep();
			c.energy -= Params.rest_energy_cost;
			if(c.getEnergy() <= 0){
				death.add(c);
			}
		}
		for(Critter c: death){
			population.remove(c);
		}
		death.clear();

		checkConflicts();
		Algae a;
		for(int i = 0; i < Params.refresh_algae_count; i++) {
			a = new Algae();
			a.setEnergy(Params.start_energy);
			a.setX_coord(getRandomInt(Params.world_width));
			a.setY_coord(getRandomInt(Params.world_height ));
			population.add(a);
		}

	}

	/**
	 * Prints the current state of the grid to console
	 */
	public static void displayWorld() {

		/*// Prints top border
		System.out.print("+");
		for(int i = 0; i < Params.world_width; i++) System.out.print("-");
		System.out.println("+");

		// Prints body with critters
		for(int i = 0; i < Params.world_height; i++) {
			System.out.print("|");
			boolean spotTaken = false;
			for(int j = 0; j < Params.world_width; j++){
				boolean drawn = false;
				for(Critter c: population){
					if(c.x_coord == j && c.y_coord == i && !drawn) {
						System.out.print(c.toString());
						spotTaken = true;
						drawn = true;
					}
				}
				if(!spotTaken){
					System.out.print(" ");
				}
				spotTaken = false;
			}
			System.out.println("|");
		}

		// Prints bottom border
		System.out.print("+");
		for(int i = 0; i < Params.world_width; i++) System.out.print("-");
		System.out.println("+");*/

	}

	/**
	 * changes the x and y coordinates to update critter location
	 * @param direction specifies movement direction
	 * @param isWalk true if walk, false if run
	 */
	public void changeLoc(int direction, boolean isWalk){
		int changeX, changeY;

		switch(direction){
			case 0:
				changeX = 0;
				changeY = -1;
				break;
			case 1:
				changeX = 1;
				changeY = -1;
				break;
			case 2:
				changeX = 1;
				changeY = 0;
				break;
			case 3:
				changeX = 1;
				changeY = 1;
				break;
			case 4:
				changeX = 0;
				changeY = 1;
				break;
			case 5:
				changeX = -1;
				changeY = 1;
				break;
			case 6:
				changeX = -1;
				changeY = 0;
				break;
			default:
				changeX = -1;
				changeY = -1;
				break;
		}

		if(!isWalk){
			changeX *= 2;
			changeY *= 2;
		}

		if(x_coord + changeX < 0){
			x_coord = Params.world_width + (x_coord + changeX);
		} else{
			x_coord = (x_coord + changeX) % (Params.world_width);
		}

		if(y_coord + changeY < 0){
			y_coord = Params.world_height + (y_coord + changeY);
		} else{
			y_coord = (y_coord + changeY) % (Params.world_height);
		}

	}

	/**
	 * sees if any Critters on the board are overlapping, if they are then resolve their conflict
	 */
	public static void checkConflicts() {
		int[] checked = new int[population.size()];

		List<List<Critter>> allConflicts = new java.util.ArrayList<List<Critter>>();

		for(int i = 0; i < population.size()-1; i++) {
			List<Critter> curConflict = new java.util.ArrayList<Critter>();
			curConflict.add(population.get(i));
			for(int j = i + 1; j < population.size(); j++) {
				if(population.get(j).x_coord == curConflict.get(0).x_coord && population.get(j).y_coord == curConflict.get(0).y_coord) {
					if(checked[j]==0) {
						curConflict.add(population.get(j));
						checked[j] = 1;
					}
				}
			}
			if(curConflict.size() > 1) allConflicts.add(curConflict);
		}

		// allConflicts contains all conflicting cells on the grid

		for(List<Critter> curList: allConflicts) {
			// loop for more than two critters
			int numCritters = curList.size();
			while(numCritters > 1) {
				if (numCritters > 1) {
					Critter A = curList.get(0);
					Critter B = curList.get(1);
					Boolean aFight = A.fight(B.toString());
					Boolean bFight = B.fight(A.toString());
					int aRoll = 0, bRoll = 0;



					if (aFight) aRoll = getRandomInt(A.getEnergy());
					if (bFight) bRoll = getRandomInt(B.getEnergy());

					if (aRoll >= bRoll) {
						A.energy += (B.energy / 2);
						population.remove(B);
						curList.remove(B);
					} else {
						B.energy += (A.energy / 2);
						population.remove(A);
						curList.remove(A);
					}
					//check for alive and dead
					numCritters --;
				}
			}
		}
	}

	/**
	 * This function allows critters to look and see if their potential spot has another thing
	 * @param direction numbered as before starting with 0 = north
	 * @param steps true if run and false if a walk
	 * @return toString of the critter at that spot or null if no critter
	 */
	public String look(int direction, boolean steps){
		energy -= Params.look_energy_cost;
		int changeX, changeY;
		int tempX, tempY;
		switch(direction){
			case 0:
				changeX = 0;
				changeY = -1;
				break;
			case 1:
				changeX = 1;
				changeY = -1;
				break;
			case 2:
				changeX = 1;
				changeY = 0;
				break;
			case 3:
				changeX = 1;
				changeY = 1;
				break;
			case 4:
				changeX = 0;
				changeY = 1;
				break;
			case 5:
				changeX = -1;
				changeY = 1;
				break;
			case 6:
				changeX = -1;
				changeY = 0;
				break;
			default:
				changeX = -1;
				changeY = -1;
				break;
		}
		if(steps){
			changeX *= 2;
			changeY *= 2;
		}
		if(x_coord + changeX < 0){
			tempX = Params.world_width + (x_coord + changeX);
		} else{
			tempX = (x_coord + changeX) % (Params.world_width);
		}

		if(y_coord + changeY < 0){
			tempY = Params.world_height + (y_coord + changeY);
		} else{
			tempY = (y_coord + changeY) % (Params.world_height);
		}

		for(Critter c: population){
			if(c.x_coord == tempX && c.y_coord == tempY)
				return c.toString();
		}
		return null;
	}

	static void drawUpdatedCritters(GraphicsContext gc) {
	    for(Critter c : population) {
	        CritterShape shape = c.viewShape();
	        /*
            javafx.scene.paint.Color outline = c.viewOutlineColor();
            javafx.scene.paint.Color fill = c.viewFillColor();

            gc.setFill(fill);
            gc.setStroke(outline);
            gc.setLineWidth(5.0);
            */

            double gridWidth = 550.0/Params.world_width;
            double gridHeight = 550.0/Params.world_height;
            double gridHeightBorder = gridHeight*0.1;
            double gridWidthBorder = gridWidth*0.1;
            double critHeight = gridHeight*0.8;
            double critWidth = gridWidth*0.8;

	        switch(shape){
                case CIRCLE:
                    gc.setFill(c.viewFillColor());
                    gc.fill();

                    gc.setStroke(c.viewOutlineColor());
                    gc.setLineWidth(2);
                    gc.stroke();
                    gc.fillOval(gridWidthBorder + 25 + gridWidth*c.x_coord, gridHeightBorder + 25 + gridHeight*c.y_coord,
							critWidth, critHeight);
                    gc.strokeOval(gridWidthBorder + 25 + gridWidth*c.x_coord, gridHeightBorder + 25 + gridHeight*c.y_coord,
							critWidth, critHeight);
                    break;
                case STAR:
                    double[] xCoordsStar = {0,0,0,0,0,0,0,0,0,0};
                    xCoordsStar[0] = gridWidthBorder + 25 + gridWidth*c.x_coord + critWidth/2;
                    xCoordsStar[1] = gridWidthBorder + 25 + gridWidth*c.x_coord + critWidth*0.4;
                    xCoordsStar[2] = gridWidthBorder + 25 + gridWidth*c.x_coord;
                    xCoordsStar[3] = gridWidthBorder + 25 + gridWidth*c.x_coord + critWidth*0.3;
                    xCoordsStar[4] = gridWidthBorder + 25 + gridWidth*c.x_coord + critWidth*0.2;
                    xCoordsStar[5] = gridWidthBorder + 25 + gridWidth*c.x_coord + critWidth/2;
                    xCoordsStar[6] = gridWidthBorder + 25 + gridWidth*c.x_coord + critWidth*0.8;
                    xCoordsStar[7] = gridWidthBorder + 25 + gridWidth*c.x_coord + critWidth*0.7;
                    xCoordsStar[8] = gridWidthBorder + 25 + gridWidth*c.x_coord + critWidth;
                    xCoordsStar[9] = gridWidthBorder + 25 + gridWidth*c.x_coord + critWidth*0.6;
                    double[] yCoordsStar = {0,0,0,0,0,0,0,0,0,0};
                    yCoordsStar[0] = gridHeightBorder + 25 + gridHeight*c.y_coord;
                    yCoordsStar[1] = gridHeightBorder + 25 + gridHeight*c.y_coord + critHeight*0.4;
                    yCoordsStar[2] = gridHeightBorder + 25 + gridHeight*c.y_coord + critHeight*0.4;
                    yCoordsStar[3] = gridHeightBorder + 25 + gridHeight*c.y_coord + critHeight*0.6;
                    yCoordsStar[4] = gridHeightBorder + 25 + gridHeight*c.y_coord + critHeight;
                    yCoordsStar[5] = gridHeightBorder + 25 + gridHeight*c.y_coord + critHeight*0.7;
                    yCoordsStar[6] = gridHeightBorder + 25 + gridHeight*c.y_coord + critHeight;
                    yCoordsStar[7] = gridHeightBorder + 25 + gridHeight*c.y_coord + critHeight*0.6;
                    yCoordsStar[8] = gridHeightBorder + 25 + gridHeight*c.y_coord + critHeight*0.4;
                    yCoordsStar[9] = gridHeightBorder + 25 + gridHeight*c.y_coord + critHeight*0.4;
                    int nStar = 10;


                    gc.setFill(c.viewFillColor());
                    gc.fill();

                    gc.setStroke(c.viewOutlineColor());
                    gc.setLineWidth(2);
                    gc.stroke();

                    gc.fillPolygon(xCoordsStar, yCoordsStar, nStar);
					gc.strokePolygon(xCoordsStar, yCoordsStar, nStar);
                    break;
                case SQUARE:
                    gc.setFill(c.viewFillColor());
                    gc.fill();

                    gc.setStroke(c.viewOutlineColor());
                    gc.setLineWidth(2);
                    gc.stroke();
                    gc.fillRect(gridWidthBorder + 25 + gridWidth*c.x_coord, gridHeightBorder + 25 + gridHeight*c.y_coord, critWidth, critHeight);
					gc.strokeRect(gridWidthBorder + 25 + gridWidth*c.x_coord, gridHeightBorder + 25 + gridHeight*c.y_coord, critWidth, critHeight);
                    break;
                case DIAMOND:
                    double[] xCoordsDi = {0,0,0,0};
                    xCoordsDi[0] = gridWidthBorder + 25 + gridWidth*c.x_coord;
                    xCoordsDi[1] = gridWidthBorder + 25 + gridWidth*c.x_coord + critWidth/2;
                    xCoordsDi[2] = gridWidthBorder + 25 + gridWidth*c.x_coord + critWidth;
                    xCoordsDi[3] = gridWidthBorder + 25 + gridWidth*c.x_coord + critWidth/2;
                    double[] yCoordsDi = {0,0,0,0};
                    yCoordsDi[0] = gridHeightBorder + 25 + gridHeight*c.y_coord + critHeight/2;
                    yCoordsDi[1] = gridHeightBorder + 25 + gridHeight*c.y_coord + critHeight;
                    yCoordsDi[2] = gridHeightBorder + 25 + gridHeight*c.y_coord + critHeight/2;
                    yCoordsDi[3] = gridHeightBorder + 25 + gridHeight*c.y_coord;
                    int nDi = 4;


                    gc.setFill(c.viewFillColor());
                    gc.fill();

                    gc.setStroke(c.viewOutlineColor());
                    gc.setLineWidth(2);
                    gc.stroke();

                    gc.fillPolygon(xCoordsDi, yCoordsDi, nDi);
					gc.strokePolygon(xCoordsDi, yCoordsDi, nDi);
                    break;
                case TRIANGLE:
                    double[] xCoordsTri = {0,0,0};
                    xCoordsTri[0] = gridWidthBorder + 25 + gridWidth*c.x_coord;
                    xCoordsTri[1] = gridWidthBorder + 25 + gridWidth*c.x_coord + critWidth;
                    xCoordsTri[2] = gridWidthBorder + 25 + gridWidth*c.x_coord + critWidth/2;
                    double[] yCoordsTri = {0,0,0};
                    yCoordsTri[0] = gridHeightBorder + 25 + gridHeight*c.y_coord + critHeight;
                    yCoordsTri[1] = gridHeightBorder + 25 + gridHeight*c.y_coord + critHeight;
                    yCoordsTri[2] = gridHeightBorder + 25 + gridHeight*c.y_coord ;
                    int nTri = 3;


                    gc.setFill(c.viewFillColor());
                    gc.fill();

                    gc.setStroke(c.viewOutlineColor());
                    gc.setLineWidth(2);
                    gc.stroke();

                    gc.fillPolygon(xCoordsTri, yCoordsTri, nTri);
					gc.strokePolygon(xCoordsTri, yCoordsTri, nTri);
                    break;
            }
        }
    }


	/* the TestCritter class allows some critters to "cheat". If you want to
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here.
	 *
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}

		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}

		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}

		protected int getX_coord() {
			return super.x_coord;
		}

		protected int getY_coord() {
			return super.y_coord;
		}



		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}

		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}
}
