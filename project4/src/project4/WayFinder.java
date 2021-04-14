package project4;

/**
 * Main class where user input is taken in and solved
 * Uses helper class to do all the work
 * Checks user input and returns a solved puzzle
 *
 * @author Isaac Harris
 * @version April 10th, 2021
 */
public class WayFinder {

    /**
     * Takes in user input and checks if it is valid
     * Uses helper method from helper object to solve puzzle
     *
     * @param args command line input that contains the puzzle,
     *             should be positive integers from 0 to 99 with the last int being 0.
     */
    public static void main(String[] args) {

        //verify that the command line argument exists
        if (args.length == 0) {
            errExitWMess("ERROR: no puzzle was inputted.");
        }
        if (args.length < 2) {
            errExitWMess("Error: two or more numbers required to be a puzzle");
        }

        int[] puzzle = new int[args.length];

        //Try block in the event that a non integer is in the input which would cause parseInt to blow up
        try {
            //Checking if the last integer is zero
            if (Integer.parseInt(args[args.length - 1]) != 0) {
                errExitWMess("ERROR: the last value in the puzzle has to be zero.");
            }

            for (int i = 0; i < args.length - 1; i++) {

                int value = Integer.parseInt(args[i]);
                //Checking if any negative integers were inputted
                if (value < 0) {
                    errExitWMess("ERROR: the puzzle values have to be positive integers.");
                }
                //checking if any integers larger than 99 were inputted
                if (value > 99) {
                    errExitWMess("ERROR: Values out of range.");
                }
                puzzle[i] = value;
            }
        } catch (NumberFormatException e) { //In the event that an input is not a integer and parseInt fails
            errExitWMess("ERROR: the puzzle values must be integers.");
        }

        //Creates object to solve puzzle, all the work is handled by the object
        PuzzleSolver currentPuzzle = new PuzzleSolver(puzzle);
        currentPuzzle.solvePuzzle(0);
        currentPuzzle.resetFields();
    }

    /**
     * Prints out error message and exits program with error
     * @param errMess message to be printed to the error log
     */
    private static void errExitWMess(String errMess) {
        System.err.println(errMess);
        System.exit(1);
    }
}