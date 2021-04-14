package project3;


/**
 * Exception class used by the Simulation program for signaling problems
 * with exploration of the Labyrinth.
 *
 * @author Joanna Klukowska
 *
 */

@SuppressWarnings("serial")
public class LabyrinthSearchException extends RuntimeException {

    public LabyrinthSearchException(String message) {
        super(message);
    }

}