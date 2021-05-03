package project5;

import java.util.*;

/**
 * Stores supplies and obstacles
 * Represents a stop in the mountain
 */
public class RestStop implements Comparable<RestStop>{

    private String label;
    private List<String> supplies;
    private List<String> obstacles;

    /**
     * Instantiates a new RestStop based on provided information.
     *
     * @param label     the label
     * @param supplies  the supplies
     * @param obstacles the obstacles
     */
    public RestStop(String label, List<String> supplies, List<String> obstacles) {
        this.label = label;
        this.supplies = supplies;
        this.obstacles = obstacles;
    }

    /**
     * compares current RestStop to another RestStop
     * @param o other RestStop to be compared to
     * @return returns integer that tells if current RestStop is larger, smaller, or equal to other RestStop
     * @throws NullPointerException if other restStop is null
     */
    @Override
    public int compareTo(RestStop o) throws NullPointerException{
        if (o == null) {
            throw new NullPointerException("RestStop does not exist");
        }

        return this.label.compareTo(o.label);
    }

    /**
     * Gets label.
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Gets supplies.
     *
     * @return the supplies
     */
    public List<String> getSupplies() {
        return supplies;
    }

    /**
     * Gets obstacles.
     *
     * @return the obstacles
     */
    public List<String> getObstacles() {
        return obstacles;
    }
}
