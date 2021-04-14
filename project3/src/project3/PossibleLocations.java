package project3;

/**
 * Represents a collection of spaces used in the algorithm
 * that explores a maze.
 *
 * @author Joanna Klukowska
 *
 */
public interface PossibleLocations {
    /**
     * Add a Location object to this collection.
     * @param s object to be added
     * @throws NullPointerException if the given location is null
     */
    void add ( Location s );

    /**
     * Remove the next object from this collection. The specific
     * item returned is determined by the underlying structure
     * by which this collection is represented.
     * @return the next object, or null if set is empty
     */
    Location remove ();

    /**
     * Determines if this collection is empty or not.
     * @return  true, if set is empty, false, otherwise.
     */
    boolean isEmpty();

    /**
     * Returs a string representation of this collection.
     * The string representation consists of a list of the collection's
     * elements in the order they would be removed and returned by future
     * calls to remove(). The elements should be enclosed in square brackets (`"[]"`).
     * Adjacent elements are separated by the characters `", "` (comma and space).
     * Elements are converted to strings as by `String.valueOf(Object)`.
     * @return string representation of this colleciton
     */
    String toString();
}