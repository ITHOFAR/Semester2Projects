package project5;

/**
 * Represents a Hiker
 * Contains the amount of axes, rafts, and food
 *
 * @author Isaac Harris
 * @version 05/02/2021
 */
public class Hiker {

    private int amountOfAxes;
    private int amountOfRafts;
    private int amountOfFood;

    /**
     * sets all values to zero
     */
    public Hiker() {
        amountOfAxes = 0;
        amountOfRafts = 0;
        amountOfFood= 0;
    }

    /**
     * Instantiates a new Hiker based on passed in Hiker to make a copy of it
     *
     * @param otherHiker Hiker we want to make a copy of
     */
    public Hiker(Hiker otherHiker) {
        this.amountOfAxes = otherHiker.getAmountOfAxes();
        this.amountOfRafts = otherHiker.getAmountOfRafts();
        this.amountOfFood = otherHiker.getAmountOfFood();

    }

    /**
     * Decreases amount of axes
     */
    public void useAxe() {
        if (amountOfAxes > 0) {
            amountOfAxes--;
        }
    }

    /**
     * Decreases amount of rafts
     */
    public void useRaft() {
        if (amountOfRafts > 0) {
            amountOfRafts--;
        }
    }

    /**
     * Decreases amount of food
     */
    public void useFood() {
        if (amountOfFood > 0) {
            amountOfFood--;
        }
    }

    /**
     * Add food.
     */
    public void addFood() {
        amountOfFood++;
    }

    /**
     * Add raft.
     */
    public void addRaft() {
        amountOfRafts++;
    }

    /**
     * Add axe.
     */
    public void addAxe() {
        amountOfAxes++;
    }

    /**
     * Gets amount of axes.
     *
     * @return the amount of axes
     */
    public int getAmountOfAxes() {
        return amountOfAxes;
    }

    /**
     * Gets amount of rafts.
     *
     * @return the amount of rafts
     */
    public int getAmountOfRafts() {
        return amountOfRafts;
    }

    /**
     * Gets amount of food.
     *
     * @return the amount of food
     */
    public int getAmountOfFood() {
        return amountOfFood;
    }

}
