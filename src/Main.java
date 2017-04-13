/**
 * Software Programming Challenge
 *
 * @author Eric Thomas Parsons
 * @version 1.0
 * Date: May 13, 2016
 *
 * Plan to create program:
 *
 * Make a Main class to constantly be able to test new code,
 * additions, and other methods properly.
 *
 * Create a Cell class
 *      Add: properties such as:
 *          Integers X and Y location
 *          Boolean is alive state
 *          Color default color (green)
 *      Make accessors and mutator methods
 *      Make default, standard, and copy constructors
 *      Override equals() and toString() methods
 *
 * Create a Board class
 *      Add: instance variables-
 *          2 dimensional array
 *          X size and Y size for the 2D array
 *      Make accessors and mutator methods
 *      Make default, standard, and copy constructors
 *      Override equals() and toString() methods
 *      -Later add necessary methods for pulling specific types of data
 *
 * Create UserInterface class to handle all visual aspects-
 * Create SimulationLogistics to handle all data aspects-
 *
 *      -The UserInterface class has:
 *          Instance variables such as:
 *              Window properties, default Color's of the windows,
 *              JPanel/JLabel/Container/Component reference variables,
 *              default line and cell sizes, and total count of cells
 *              in board array
 *          An implementation to JFrame, and constructor to make it
 *          A method to create a start screen, has a button that:
 *              Notifies listener when fade animation starts
 *          Innerclass listener to handel when program kicks-off
 *          A fade screen animation method (runs separate thread)
 *              ^new thread activates another listener to start simulation
 *          Innerclass that extends JPanel to draw background and lines
 *          Methods that update the visual for newly born and killed cells
 *          Only appropriate accessors and mutator methods
 *
 *      -The SimulationLogistics class has:
 *          Instance variables that hold basic properties of delays and algorithm multipliers
 *          Method that creates a starter cell for the program to work off of
 *          Method to run simulation, holds algorithm that works as:
 *            Birth:
 *              1. Determines if Cell at (x, y) has enough cells around to reproduce
 *              2. Randomly generates index between 0 and number of cells surrounding Cell
 *              3. Gets x and y point at randomized index and creates new cell
 *              4. Board updates changes with new cell
 *              5. Updates visual
 *            Death:
 *              1. Gets number of living cells around current cell positing
 *              2. If greater/equal to overcrowding number, cell 'dies'
 *              3. Wipes Cell memory from board
 *              4. Updates visual of new change
 *          Death and life cycle that sweets board array looking for changes
 *          Respectable accessor and mutator methods
 *
 * Create a listener that starts the game
 *      Called after fade animation is completed
 *      Starts the algorithm thread
 *
 * For appealing title screen, create a Background image class
 *      Use world-class Microsoft Paint skill to develop background picture
 *      Make instance variables to hold base X and Y position, and image itself
 *      Make a draw method for when called repeatedly, creates a moving affect by
 *          changing x values to -1 pixel per call, move image back when out of frame
 *      Override toString() method
 *      Make accessors and mutator methods
 *
 * Create a scrolling background class to move image/portray on JFrame
 *      Creates 2 copies of Background object, sets one to (0,0) and other
 *          X position to out of frame to therefore move image to -1 each iteration
 *      Override run() method to wait a specified time, then repaint image
 *      Therefore required override of update() method to create Graphics for image
 *      Make paint method to draw the buffered image
 *
 * Recheck class/method/variable scope identifiers to reduce risk of security flaw
 *
 * Bugs: None known
 */

public final class Main {
    public static void main(String[] args){

        //creating UserInterface object to start program
        UserInterface simulation = new UserInterface();

    }
}//Main
