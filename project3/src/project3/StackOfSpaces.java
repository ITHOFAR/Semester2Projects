package project3;

/**
 * Contains spaces stored using a stack
 * implements PossibleLocations interface
 *
 * @author Isaac Harris
 * @version March 28th, 2021
 */
public class StackOfSpaces implements PossibleLocations{

    private DoublyLinkedList<Location> listOfLocations = new DoublyLinkedList<>();

    /**
     * adds element to the back of the the list
     * @param s object to be added at the back of the list
     */
    @Override
    public void add(Location s) {
        listOfLocations.add(s, 0);
    }

    /**
     * Removes element from the back of the list
     * @return returns removed location
     */
    @Override
    public Location remove() {

        if (isEmpty()) { //checks if StackOfSpaces is empty
            return null;
        }
        else {
            return listOfLocations.remove(0);
        }
    }

    /**
     * Checks if list is empty
     * @return returns true if empty, false is not
     */
    @Override
    public boolean isEmpty() {
        return listOfLocations.isEmpty();
    }

    /**
     * Returns a formatted string of the elements in list
     * @return returns a formatted string of elements in list
     */
    @Override
    public String toString() {
        return listOfLocations.toString();
    }
}

