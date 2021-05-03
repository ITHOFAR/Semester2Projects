package project5;

import java.util.ArrayList;
import java.util.List;

/**
 * Class designed to take in a BSTMountain and Hiker and find valid paths down the mountain
 * Stores the mountain, hiker, root of mountain, maxDepth of mountain
 * Keeps a List of Strings that represents one valid path down the mountain
 * Keeps a List of List of String that represents all valid paths down the mountain
 * Inspired by my code from Project 4
 * Recursive algorithm inspired by Karl in his Ed post and recitation teaching
 *
 * @author Isaac Harris
 * @version 05/02/2021
 */
public class MountainSolver {

    private static BSTMountain mountain;
    private BSTMountain.BSTNode root;
    private Hiker hiker;
    private static int maxDepth;

    private List<List<String>> allSolutions;
    private List<String> solution;


    /**
     * Takes in a BSTMountain and a hiker object
     * Assumes the mountain is built with BSTNodes that store RestStops
     *
     * @param mountain the BSTMountain object that will be traversed
     * @param hiker    the Hiker object that will store supplies
     */
    public MountainSolver(BSTMountain mountain, Hiker hiker) {
        this.mountain = mountain;
        this.hiker = hiker;
        this.allSolutions = new ArrayList<>();
        this.solution = new ArrayList<>();
        this.root = mountain.getRoot();
        this.maxDepth = mountain.getMaxDepth();
    }

    /**
     * Wrapper function for traverseMountain
     * If there is no solution, then does nothing
     * If there are solutions, prints them out
     */
    public void solveMountain() {
        //handles case where there is no mountain to solve
        if (mountain == null) {
            return;
        }
        if (!traverseMountain(root, hiker)) {
            return;
        }
        else {
           printSolutions(allSolutions);
        }
    }

    /**
     *  Resets solution, allSolutions, and hiker
     *  Allows the mountain to be called again with a different hiker
     */
    public void resetFields() {
        solution.clear();
        allSolutions.clear();
        hiker = new Hiker();
    }

    /**
     * Main workhorse of the class
     * Checks base case where hiker is at bottom of mountain
     * Checks if safe to advance on current RestStop by checking supplies, if not then backtracks
     * adds label of current RestStop to solution list
     *
     * @param node Current RestStop that hiker must traverse, contains supplies and obstacles
     * @param hiker current state of the hiker, will update supplies based on reststop that was passed in
     * @return returns true if a solution exist, false if no solution exist
     */
    private boolean traverseMountain(BSTMountain.BSTNode node, Hiker hiker) {

        boolean result = false;

        //checking case where node is null, means mountain is empty
        if (node == null) {
            return false;
        }

        //base case where hiker has reached the end of the mountain
        if (isLeafNode(node) && ((maxDepth-1) - node.getDepth()) == 0) {
            solution.add(node.getRestStop().getLabel());
            allSolutions.add(new ArrayList<>(solution));

            //removes node for backtracking
            solution.remove(node.getRestStop().getLabel());
            return true;
        }
        //updating state of hiker based on current RestStop
        addSupplies(node, hiker);
        //checking if hiker will run out of supplies
        if (canAdvance(node, hiker)) {
            //using supplies based on amount of obstacles in RestStop
            advanceHiker(node, hiker);

            //adding a possible path to solution
            solution.add(node.getRestStop().getLabel());

            //checking if solution could exist to the left
            if (node.getLeftStop() != null) {
                result = traverseMountain(node.getLeftStop(), new Hiker(hiker)) || result;
            }
            //checking if solution could exist to the right
            if (node.getRightStop() != null) {
                result = traverseMountain(node.getRightStop(), new Hiker(hiker)) || result;
            }
        }
        //backtracking
        solution.remove(node.getRestStop().getLabel());

        return result;
    }

    /**
     * checking if current node in mountain is a leaf node
     * if it is a leaf node, then the left child and right child are null
     * @param node the node that will checked if it is a leaf node
     * @return returns true if node is a leaf node, false if not
     */
    private boolean isLeafNode(BSTMountain.BSTNode node) {
        if (node.getLeftStop() == null && node.getRightStop() == null) {
            return true;
        }
        return false;
    }

    /**
     * Checks if hiker can advance based on amount of supplies stored and obstacles in RestStop
     * @param node the RestStop that contains supplies and obstacles
     * @param hiker the hiker containing supplies
     * @return returns true if the hiker can advance, false if stuck (will have to wait for rescue)
     */
    private boolean canAdvance(BSTMountain.BSTNode node, Hiker hiker) {

        //checking if hiker ran out of food
        if (hiker.getAmountOfFood() == 0) {
            return false;
        }
        int[] obstacles = calculateObstacles(node);

        int amountOfRivers = obstacles[0];
        int amountOfTrees = obstacles[1];

        //checking if hiker has enough axes to chop down trees
        if (hiker.getAmountOfAxes() < amountOfTrees) {
            return false;
        }
        //checking if hiker has enough rafts to cross the rivers
        if (hiker.getAmountOfRafts() < amountOfRivers) {
            return false;
        }

        return true;
    }

    /**
     * Updates hikers information based on data stored in RestStop
     * Uses one food to represent hiker advancing
     * Uses axes and rafts based on amount of rivers and fallen trees present in RestStop
     * @param node the RestStop that contains obstacle
     * @param hiker the hiker that stores amount of supplies
     */
    private void advanceHiker(BSTMountain.BSTNode node, Hiker hiker) {

        int[] obstacles = calculateObstacles(node);
        if (obstacles ==  null) {
            return;
        }
        int amountOfRivers = obstacles[0];
        int amountOfTrees = obstacles[1];

        //updates hiker's supply count based on amount of obstacles in the RestStop
        for (int i = 0; i < amountOfRivers; i++) {
            hiker.useRaft();
        }
        for (int i = 0; i < amountOfTrees; i++) {
            hiker.useAxe();
        }
        hiker.useFood();
    }

    /**
     * Finds the amount of each type of obstacle in a RestStop
     * @param node the RestStop that contains the obstacles
     * @return returns an int array with the first value the amount of rivers
     * and second value the amount of fallen trees
     */
    private int[] calculateObstacles(BSTMountain.BSTNode node) {
        if (node == null) {
            return null;
        }
        int amountOfRivers = 0;
        int amountOfTrees = 0;

        for (String obstacles: node.getRestStop().getObstacles()) {
            //finding amount of each obstacle that were in the RestStop
            if (obstacles.equals("river")) {
                amountOfRivers++;
            }
            if (obstacles.equals("fallentree")) {
                amountOfTrees++;
            }
        }
        int[] output = new int[2];
        output[0] = amountOfRivers;
        output[1] = amountOfTrees;
        return output;
    }

    /**
     * Updates state of hiker to include supplies that were stored in the RestStop
     * @param node the RestStop that contains the supplies
     * @param hiker the current state of the hiker
     */
    private void addSupplies(BSTMountain.BSTNode node, Hiker hiker ) {
        if (node == null) {
            return;
        }

        for (String supplies: node.getRestStop().getSupplies()) {
            if (supplies.equals("axe")) {
                hiker.addAxe();
            }
            if (supplies.equals("raft")) {
                hiker.addRaft();
            }
            if (supplies.equals("food")) {
                hiker.addFood();
            }
        }
    }

    /**
     * Handles printing out all possible paths down the mountain
     * @param allSolutions a list of list of string that represent a path down the mountain
     */
    private void printSolutions(List<List<String>> allSolutions) {
        for (List<String> list: allSolutions) {

            StringBuilder solutionsList = new StringBuilder();

            for (String label: list) {
                solutionsList.append(label + " ");
            }

            //removes any whitespaces in the String
            String output = solutionsList.toString().strip();
            System.out.println(output);
        }
    }
}
