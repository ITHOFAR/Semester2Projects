package project3;

/**
 * This class represents 2D coordinates of a square in the maze.
 * @author Joanna Klukowska
 *
 */
public class Location {
    //row and column locations
    private int row;
    private int column;

    /**
     * Create a SquarePosition object given row and column.
     * @param row
     * @param column
     */
    public Location ( int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Returns row of a position.
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns column of a position.
     * @return the column
     */
    public int getColumn() {
        return column;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Location other = (Location) obj;
        if (column != other.column)
            return false;
        if (row != other.row)
            return false;
        return true;
    }

    @Override
    public String toString() {
        String output = row + " " + column;
        return output;
    }


}