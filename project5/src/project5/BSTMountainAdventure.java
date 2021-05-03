package project5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Main class where user input is taken in
 * Checks validity of input and uses input to build a BSTMountain that contains RestStop as nodes
 * Uses MountainSolver to find ways down the mountain and print them.
 * File I/O inspired by Joanna K from her ColorConverter Class on Ed
 *
 * @author Isaac Harris
 * @version 05/02/2021
 */
public class BSTMountainAdventure {

    /**
     * Handles File I/O by checking if input exist, is valid, and can be read
     * Uses information from file to build RestStops that will be used to build a BST
     *
     * @param args command line argument that should be a .txt file with information to build a BST
     */
    public static void main(String[] args) {

        //checking if input exist
        if (args.length == 0) {
            exitWErr("Usage Error: the program expects file name as an argument");
        }

        File mountainFile = new File(args[0]);
        //checking if a file was created
        if (!mountainFile.exists()) {
            exitWErr("Error: the file "+mountainFile.getAbsolutePath()+" does not exist.");
        }
        //checking if the file is readable
        if (!mountainFile.canRead()) {
            exitWErr("Error: the file "+mountainFile.getAbsolutePath()+" cannot be opened for reading.");
        }

        Scanner fileIn = null;
        try {
            fileIn = new Scanner(mountainFile);
        }
        catch (FileNotFoundException error) {
            exitWErr("Error: the file "+mountainFile.getAbsolutePath()+" cannot be opened for reading.");
        }

        //variables that will be used to build a RestStop and help the process along
        String label;
        String noSplitLine;
        String[] line;
        BSTMountain mountain = new BSTMountain();

        while (fileIn.hasNext()) {

            //List that will be used to store supplies, will used to generate a RestStop
            List<String> supplies = new ArrayList<>();
            List<String> obstacles = new ArrayList<>();

            try {
                //formatting line to remove leading and trailing whitespaces and storing it in a array
                noSplitLine = fileIn.nextLine();
                line = noSplitLine.strip().split(" ");

                //handling when line is blank
                if (line[0].equals("")) {
                    continue;
                }
                label = line[0];

                //Used to determine if we can add supplies or not since we can't add supplies after an obstacle
                boolean obstacleAdded = false;

                for (int i = 1; i < line.length; i++) {
                    //adding supplies
                    if (isSupply(line[i]) && obstacleAdded == false) {
                        supplies.add(line[i]);
                    }
                    //adding river to obstacles
                    if (line[i].equals("river")) {
                        obstacles.add(line[i]);
                        //set obstacles added to true so that no more supplies can be added
                        obstacleAdded = true;
                    }
                    //special case to deal with fallen trees
                    if (line[i].equals("fallen") && i != line.length-1 && line[i+1].equals("tree")) {
                        //added as fallentree not fallen tree to help me in the future
                        obstacles.add(line[i] + line[i + 1]);
                        //set obstacles added to true so that no more supplies can be added
                        obstacleAdded = true;
                    }
                }
                //generating a RestStop with information on each line then adding it to the mountain
                RestStop restStop = new RestStop(label, supplies, obstacles);
                mountain.add(restStop);
            }
            catch (NoSuchElementException error) {
                //in the event of a missformated line that was not handled by above algorithm
                continue;
            }
        }
        //building a mountainSolver to find ways down
        MountainSolver mountainSolver = new MountainSolver(mountain, new Hiker());
        //finding paths and printing them
        mountainSolver.solveMountain();
        //resetting fields to allow a rerun with potentially a different hiker
        mountainSolver.resetFields();
    }

    /**
     * Takes in a error message to be printed to error log
     * exits with status 1
     *
     * @param err a String that is the error message to be printed
     */
    public static void exitWErr(String err) {
        System.err.println(err);
        System.exit(1);
    }

    /**
     * Checks if inputted String is an supply
     *
     * @param item A String that will be checked if it is a supply
     * @return Returns true is the String is a supply, false if not
     */
    public static boolean isSupply(String item) {
        if (item.equals("food")) {
            return true;
        }
        if (item.equals("raft")) {
            return true;
        }
        if (item.equals("axe")) {
            return true;
        }
        return false;
    }
}
