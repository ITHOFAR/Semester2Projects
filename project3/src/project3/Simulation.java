package project3;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This application finds a way out of the maze specified in the input
 * file (if such a way out exists). The visual feedback is printed to the
 * screen as a console-based animation. The algorithm used is either
 * stack-based or queue-based depending on the value of the second argument
 * to the program.
 *
 * @author Joanna Klukowska
 *
 */
public class Simulation {
    /** Number of lines to be cleared in order to
     * clear the screen in the terminal window. */
    public static final int SCREEN_HEIGHT = 80;

    /**
     * This is the function that starts the program.
     * @param args
     *   specifies two arguments to the program :
     *      1 - input file with the maze in it
     *      2 - a keyword equal to either "stack" or "queue" to indicate
     *      which structure should be used in solving the maze
     */
    public static void main (String [] args) {
        //make sure that the number of arguments to the program is correct
        if (args.length < 2) {
            System.err.printf("Error: Incorrect usage.%n");
            System.err.printf("\tUSAGE: java MazeRun inputFile method%n");
            System.err.printf("\t   inputFile should contain the maze specification%n");
            System.err.printf("\t   method should be either `queue` or `stack`%n");
            System.exit (1);
        }

        //verify that the input file exists and can be read
        //then open it for reading
        File inputFile = new File (args[0]);
        if ( !(inputFile.exists() && inputFile.canRead() ) )
        {
            System.err.printf("Error: Cannot read file %s.%n", args[0]);
            System.exit (1);
        }
        Scanner in = null;

        try {
            in = new Scanner (inputFile);
        } catch (FileNotFoundException e) {
            System.err.printf("Error: Cannot read file %s.%n", args[0]);
            System.exit (1);
        }


        //verify that the method is specified correctly
        if (!( args[1].startsWith("queue") || args[1].startsWith("stack") ) ) {
            System.err.printf("Error: Incorrect usage.%n");
            System.err.printf("\tUSAGE: java MazeRun inputFile method%n");
            System.err.printf("\n\n   Valid methods are 'queue' and 'stack'.\n");
            System.exit (1);
        }

        //read the maze representation from the file
        char [][] charMazeFromFile = getCharMaze( in );
        in.close();

        //create maze object
        Labyrinth maze = new Labyrinth(charMazeFromFile);

        //start the animation by clearing the screen and printing the maze
        clearScreen();
        System.out.println(maze);

        //use the algorithm indicated by the second command line argument
        if (args[1].startsWith("stack")) {
            //run the search for way out algorithm using stack
            searchForWayOut(maze, new StackOfSpaces()  );
        }
        else {
            //run the search for way out algorithm using queue
            searchForWayOut(maze, new QueueOfSpaces() );
        }
    }

    /**
     * Reads in the maze data from the file and saves it as a 2D character array.
     * @param in input file stream that contains maze data
     * @return  2D character array representation of the maze
     */
    public static char [][]  getCharMaze ( Scanner in ) {
        StringBuffer mazeFromFile = new StringBuffer();
        //read the file content
        int rowsCount = 0;
        while ( in.hasNext() ) {
            mazeFromFile.append( in.nextLine() + "\n" );
            rowsCount++;
        }
        //split into an array of rows
        String [] mazeFromFileMatrix = mazeFromFile.toString().split("\n");
        //convert into a 2D array of characters
        char [][] charMazeFromFile = new char[rowsCount] [] ;
        for (int i = 0; i < rowsCount; i++) {
            charMazeFromFile[i] = mazeFromFileMatrix[i].toCharArray();
        }

        return charMazeFromFile;
    }

    /**
     * This method implements the algorithm that finds a way out of the maze.
     * @param maze maze object to navigate
     * @param listOfSquares object to be used as the list of spaces (depending on
     *    the structure used, the algorithm perform different search)
     */
    public static void searchForWayOut (Labyrinth maze,
                                        PossibleLocations listOfSquares )  {
        if (listOfSquares == null || maze == null )
            throw new NullPointerException();

        //set a random start position
        Location start = maze.generateStartPosition();

        //variables used in running of the algorithm
        Location current = null;
        ArrayList<Location> neighbors = null;
        boolean foundWayOut = false;

        //add the start position to the list of squares
        listOfSquares.add( start );

        // if the list is empty, the finish is unreachable;
        // terminate the algorithm.
        while (!listOfSquares.isEmpty()) {
            // grab a location from the list.
            current = listOfSquares.remove();
            if ( current == null ) throw new LabyrinthSearchException ("removed element null");

            //if location has been visited then there is no need
            //to explore it again; this step is done.
            if (maze.getSquareType(current).isVisited() )
                continue;

            //if location is a finish square, the finish
            //was reachable; terminate the algorithm.
            if (maze.getSquareType(current).isWayOut() ) {
                foundWayOut = true;
                break;
            }

            //Otherwise, it is a reachable non-finish location that we haven’t seen
            //yet. So, explore it as follows:
            //   - compute all the adjacent locations that are inside the maze and
            //     aren’t walls, and
            //   - add them to the list for later exploration.
            //Also, record the fact that you’ve explored this location so you won’t
            //ever have to explore it again.
            neighbors = maze.getNeighbors(current);
            for (int i = 0; i < neighbors.size(); i++) {
                listOfSquares.add(neighbors.get(i));
            }
            maze.setSquareToVisited(current);

            clearScreen();
            System.out.println(maze);
            //after every step wait 300 milliseconds
            try{Thread.sleep(200);}
            catch(InterruptedException e){}
        }

        //display the final "screen"
        if (foundWayOut) {
            maze.setSquareToExit(current);
            clearScreen();
            System.out.println(maze);
            System.out.println("You found the way out!");
        }
        else {
            System.out.println("There is no way out of here!");
        }
    }

    /**
     * Clears the "screen" of the terminal by printing SCREEN_HEIGHT number
     * of blank lines.
     */
    public static void clearScreen( ) {
        for (int i = 0; i < SCREEN_HEIGHT; i++ )		{
            System.out.println(" ");
        }
    }

}