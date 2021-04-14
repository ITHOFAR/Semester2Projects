package project4;

import java.util.ArrayList;

/**
 * Designed to solve a puzzle on integers recursively
 * Keeps track of waysOut and visitedIndices.
 * Only input is a puzzle to be solved
 *
 * @author Isaac Harris
 * @version April 7th, 2021
 */
public class OldSolver {

    private int waysOut;
    private final int[] puzzle;
    private ArrayList<Integer> visitedIndices;
//TODO
    //Make method that returns list of list of solutions/ints from index then adds index to the list
    //helper inRange
    //helper moveLeft and moveRight
    //redo isValidLeft and isValidRight
    /**
     * Takes in a puzzle of integers
     *
     * @param puzzle the puzzle of integers to be solved
     */
    public OldSolver(int[] puzzle) {
        this.puzzle = puzzle;
        this.visitedIndices = new ArrayList<>();
        this.waysOut = 0;
    }

    /**
     * Checks if safe to travel left in the puzzle
     *
     * @param index current position in puzzle
     * @return returns false if not safe to travel left, returns true if safe travel left
     */
    private boolean isValidLeft(int index) {

        //shorten down to puzzle[index] >= index TODO
        int valueAtIndex = puzzle[index];

        if (valueAtIndex >= index) return false;

        return true;
    }

    /**
     * Checks if safe to travel right in the puzzle
     *
     * @param index current position in puzzle
     * @return returns false if not safe to travel right, returns true if safe travel right
     */
    private boolean isValidRight(int index) {

        int valueAtIndex = puzzle[index];

        if (valueAtIndex > puzzle.length - index - 1) return false;

        return true;
    }

    /**
     * Checks if safe to travel left and is safe to travel right in the puzzle
     *
     * @param index current position in puzzle
     * @return returns false if not safe to travel right and left, returns true is safe
     * to travel both directions
     */
    private boolean isBothValid(int index) {

        if (isValidRight(index) && isValidLeft(index)) return true;

        return false;
    }

    /**
     * Checks if a solution exist from current position in puzzle
     *
     * @param index          current position in puzzle
     * @param visitedIndices ArrayList of integers of current traveled path, makes a copy to not mutate it
     * @return returns true if a solution exist moving right or left, false if no solution exist
     */
    private boolean isSolvable(int index, ArrayList<Integer> visitedIndices) {

        int lastIndex = puzzle.length - 1;
        //base case where index is at the last position
        if (index == lastIndex) {
            return true;
        }
        if (index > lastIndex || index < 0 || visitedIndices.contains(index)) {
            // Outside list or previously visited, failed to solve
            return false;
        }

        ArrayList<Integer> copyOfVisitedIndices = new ArrayList<>(visitedIndices);
        copyOfVisitedIndices.add(index);

        int valueAtIndex = puzzle[index];
        int nextIndexLeft = index - valueAtIndex;
        int nextIndexRight = valueAtIndex + index;

        return isSolvable(nextIndexLeft, copyOfVisitedIndices)
                || isSolvable(nextIndexRight, copyOfVisitedIndices);
    }

    /**
     * The main workhorse of the class
     * Checks base case when index is at the end
     * Checks if safe to move, then checks if a solution exist after moving
     *
     * @param index current position in the puzzle
     * @return returns true after reaching the end of the puzzle, returns false if no solution exist
     */
    private boolean solvePuzzleUtil(int index) {

        int lastIndex = puzzle.length - 1;

        //base case where index is at the last position
        if (index == lastIndex) {
            waysOut++;
            printSolution(visitedIndices);
            System.out.println();
            return true;
        }

        int valueAtIndex = puzzle[index];
        int nextIndexLeft = index - valueAtIndex; //make method
        int nextIndexRight = valueAtIndex + index;
        boolean result = false;

        //Checking if safe to travel in both directions
        if (isBothValid(index)) {

            visitedIndices.add(index); //adding to path traveled

            if (isSolvable(nextIndexLeft, visitedIndices)) {
                result = solvePuzzleUtil(nextIndexLeft);
            }
            if (isSolvable(nextIndexRight, visitedIndices)) {
                result = solvePuzzleUtil(nextIndexRight);
            }
        }

        //Checking if safe to travel left
        else if (isValidLeft(index)) {

            visitedIndices.add(index); //adding to path traveled

            if (isSolvable(nextIndexLeft, visitedIndices)) {
                result = solvePuzzleUtil(nextIndexLeft);
            }
        }
        //checking if safe to travel right
        else if (isValidRight(index)) {

            visitedIndices.add(index); //adding to path traveled

            if (isSolvable(nextIndexRight, visitedIndices)) {
                result = solvePuzzleUtil(nextIndexRight);
            }

        }
        //backtracking
        visitedIndices.remove((Integer) index);

        return result;
    }

    /**
     * wrapper function for solvePuzzleUtil
     * checks if there is a solution, if not writes a message saying so
     * tells how many solutions there are
     *
     * @param index starting position to the puzzle
     */
    public void solvePuzzle(int index) {

        if (!solvePuzzleUtil(index)) {
            System.out.println("No way through this puzzle.");
            return;
        }
        System.out.println("There are " + waysOut + " ways through this puzzle.");

        return;
    }

    /**
     * Removes duplicate indices from an ArrayList
     *
     * @param sols an ArrayList of integers that contain the indices traveled to
     * @return returns an ArrayList of integers with no duplicates
     */
    private ArrayList<Integer> removeDuplicates(ArrayList<Integer> sols) {

        ArrayList<Integer> noDupes = new ArrayList<>();

        for (int i : sols) {
            if (!noDupes.contains(i)) noDupes.add(i);
        }
        return noDupes;
    }

    /**
     * Uses indices and converts it to a path through the puzzle
     * Uses algorithm to determine if moved left or right
     *
     * @param sols indices that have been traveled to
     * @return returns String containing indices and direction
     */
    private String convertToPath(ArrayList<Integer> sols) {
        String solutionWithDir = ""; //use stringBuilder

        for (int i = 0; i < sols.size(); i++) {

            if (i == sols.size() - 1) {
                solutionWithDir += sols.get(i) + ".R";
            }
            else if (sols.get(i + 1) - sols.get(i) > 0) {
                solutionWithDir += sols.get(i) + ".R, ";
            }
            else {
                solutionWithDir += sols.get(i) + ".L, ";
            }
        }

        return solutionWithDir;
    }

    /**
     * Handles printing out all of the states of the puzzle
     * Uses remove duplicates to treat the arraylist and convert to path to add direction
     * Uses printSolutions to handle formatting and logic
     *
     * @param path ArrayList of integers containing all the indices but no direction
     */
    private void printSolution(ArrayList<Integer> path) {

        ArrayList<Integer> solutionNoPath = removeDuplicates(path);
        String solWPath = convertToPath(solutionNoPath);

        String[] solsSplit = solWPath.strip().split("\\,");

        for (int i = 0; i < solsSplit.length; i++) {

            String[] solsPair = solsSplit[i].strip().split("\\.");

            printSolutionsTool(Integer.parseInt(solsPair[0]), solsPair[1].charAt(0));
        }
    }

    /**
     * prints out the current state of the puzzle in a specific format
     * the current index has a direction attached to it, telling what the next move will be
     *
     * @param index     current position in the index
     * @param direction the direction of the next move
     */
    private void printSolutionsTool(int index, char direction) {
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append("[");

        for (int i = 0; i < puzzle.length; i++) {

            int valueAtIndex = puzzle[i];

            if (i == index && i == 0) { //handling case when two digit integer at start to keep proper formatting
                outputBuilder.append(String.format("%2s%c,", valueAtIndex, direction));
            }
            else if (i == index) { //handling case when it is the index to provide direction
                outputBuilder.append(String.format("%3s%c,", valueAtIndex, direction));
            }
            else if (i == puzzle.length - 1) { //handling case when it is last element
                outputBuilder.append(String.format(" %2s ", valueAtIndex));
            }
            else if (i == 0) { //handling when it is start of puzzle
                outputBuilder.append(String.format("%2s ,", valueAtIndex));
            }
            else { //general case
                outputBuilder.append(String.format(" %2s ,", valueAtIndex));
            }
        }
        outputBuilder.append("]");
        String output = outputBuilder.toString();

        System.out.println(output);
    }
}
