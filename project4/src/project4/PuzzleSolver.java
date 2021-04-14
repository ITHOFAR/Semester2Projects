package project4;

import java.util.ArrayList;
import java.util.List;

/**
 * Designed to solve a puzzle on integers recursively
 * Keeps track of visitedIndices and allSolutions.
 * Only input is a puzzle to be solved
 *
 * @author Isaac Harris
 * @version April 13th, 2021
 */
public class PuzzleSolver {

    private final int[] puzzle;
    private List<Integer> visitedIndices;
    private List<List<Integer>> allSolutions;

    /**
     * Takes in a puzzle of integers
     * Assumes puzzle is a valid puzzle
     *
     * @param puzzle the puzzle of integers to be solved
     */
    public PuzzleSolver(int[] puzzle) {
        this.puzzle = puzzle;
        this.visitedIndices = new ArrayList<>();
        this.allSolutions = new ArrayList<>();
    }

    /**
     * Checks if safe to travel left in the puzzle
     *
     * @param index current position in puzzle
     * @return returns false if not safe to travel left, returns true if safe travel left
     */
    private boolean isValidLeft(int index) {
        return (puzzle[index] <= index);
    }

    /**
     * Checks if safe to travel right in the puzzle
     *
     * @param index current position in puzzle
     * @return returns false if not safe to travel right, returns true if safe travel right
     */
    private boolean isValidRight(int index) {
        return (puzzle[index] <= puzzle.length - index -1);
    }

    /**
     * The main workhorse of the class
     * Checks base case when index is at the end
     * Checks if safe to move, then sees if a solution exist after moving, if not then backtracks
     * Adds solution to an list that will contain all solutions
     *
     * @param index current position in the puzzle
     * @return returns true after finding all solutions, returns false if no solution exist
     */
    private boolean findSolutions(int index) {

        int lastIndex = puzzle.length - 1;

        //base case where index is at the last position
        if (index == lastIndex) {
            //Instead of printing here, added solution to list of solutions for more flexibility in the future
            allSolutions.add(new ArrayList<>(visitedIndices));
            return true;
        }

        int valueAtIndex = puzzle[index];
        int nextIndexLeft = index - valueAtIndex;
        int nextIndexRight = valueAtIndex + index;
        boolean result = false;

        //checking if index is in bounds and index has not been visited
        if (index >= 0 && index <= lastIndex && !visitedIndices.contains(index)) {

            //Checking if safe to travel left
            if (isValidLeft(index)) {

                visitedIndices.add(index); //adding to path traveled
                result = findSolutions(nextIndexLeft) || result;

                visitedIndices.remove((Integer) index); //backtracking
            }
            //checking if safe to travel right
            if (isValidRight(index)) {

                visitedIndices.add(index); //adding to path traveled
                result = findSolutions(nextIndexRight) || result;

                visitedIndices.remove((Integer) index); //backtracking
            }
        }
        
        return result;
    }

    /**
     * wrapper function for findSolutions
     * checks if there is a solution, if not writes a message saying so
     * prints all solutions
     * tells how many solutions there are
     *
     * @param index starting position to the puzzle
     */
    public void solvePuzzle(int index) {

        if (!findSolutions(index)) {
            System.out.println("No way through this puzzle.");
            return;
        }

        //handles printing all solutions
        for (List<Integer> sol: allSolutions) {
            printSolution(sol);
            System.out.println();
        }
        int waysOut = allSolutions.size();
        System.out.println("There are " + waysOut + " ways through this puzzle.");

        return;
    }

    /**
     * resets all fields so solvePuzzle can be used again
     */
    public void resetFields() {
        visitedIndices.clear();
        allSolutions.clear();
    }

    /**
     * Uses indices and converts it to a path through the puzzle
     * Uses algorithm to determine if moved left or right
     *
     * @param sols indices that have been traveled to
     * @return returns String containing indices and direction
     */
    private String convertToPath(List<Integer> sols) {

        //using StringBuilder for efficiency
        StringBuilder solWDir = new StringBuilder();

        for (int i = 0; i < sols.size(); i++) {

            if (i == sols.size() - 1) { //handles when last index
                solWDir.append(sols.get(i) + ".R");
            }
            else if (sols.get(i + 1) - sols.get(i) > 0) { //handles when moving right
                solWDir.append(sols.get(i) + ".R, ");
            }
            else { //handles when moving left
                solWDir.append(sols.get(i) + ".L, ");
            }
        }
        String output = solWDir.toString();

        return output;
    }

    /**
     * Handles printing out all of the states of the puzzle
     * Uses convert to path to add direction to indices
     * Uses printSolutions to handle formatting and logic
     *
     * @param path List of integers containing all the indices but no direction
     */
    private void printSolution(List<Integer> path) {

        String solWPath = convertToPath(path);

        //splits string by comma to have array of index and directions
        String[] solsSplit = solWPath.strip().split("\\,");

        for (int i = 0; i < solsSplit.length; i++) {

            //splits string by period to only have a pair of index and direction
            String[] solsPair = solsSplit[i].strip().split("\\.");

            printSolutionLine(Integer.parseInt(solsPair[0]), solsPair[1].charAt(0));
        }
    }

    /**
     * prints out the current line of the puzzle in a specific format
     * the current index has a direction attached to it, telling what the next move will be
     *
     * @param index     current position in the index
     * @param direction the direction of the next move
     */
    private void printSolutionLine(int index, char direction) {

        //using StringBuilder for efficiency
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

    /**
     * returns list of all solutions
     * @return returns list of list solutions
     */
    public List<List<Integer>> getAllSolutions() {
        return allSolutions;
    }
}