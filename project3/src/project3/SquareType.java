package project3;

/**
 * This enumerated type describes all possible types of positions in
 * a maze.
 *
 * @author Joanna Klukowska
 *
 */
public enum SquareType {
    WALL     ('x'),
    CORRIDOR (' '),
    WAYOUT   ('o'),
    VISITED  ('.'),
    START    ('*'),
    EXIT     ('E');

    //symbol associated with a given type of space
    private char symbol;

    /**
     * Create a space of a given type
     * @param symbol
     */
    SquareType ( char symbol ) {
        this.symbol = symbol;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    public String toString () {
        switch (symbol) {
            //U+2588	█	FULL BLOCK
            //U+2591	░	LIGHT SHADE
            //U+25C9	◉
            case '*': return "\u25c9"; //"*";
            case 'x': return "\u2588"; //"x";
            case '.': return ".";//"\u2591";
            case ' ': return " ";
            case 'o': return " ";
            case 'E': return "E";
        }
        return "?";
    }

    /**
     * Determines if the given type of position can be
     * modified. Only CORRIDOR can be modified.
     * @return  true, if position is modifiable
     *          false, otherwise
     */
    public boolean canBeSet() {
        if (this.equals(SquareType.CORRIDOR))
            return true;
        else
            return false;
    }

    /**
     * Determines if the given type of position is
     * a way out of the maze.
     * @return  true, if position is a WAYOUT
     *          false, otherwise
     *
     */
    public boolean isWayOut () {
        if (this.equals(SquareType.WAYOUT))
            return true;
        else
            return false;
    }

    /**
     * Determines if the given type of position is
     * visited.
     * @return  true, if position is a VISITED
     *          false, otherwise
     *
     */
    public boolean isVisited () {
        if (this.equals(SquareType.VISITED))
            return true;
        else
            return false;
    }


    /**
     * Determines if the given type of position is
     * a wall.
     * @return true, if position is a WALL
     *         false, otherwise
     *
     */
    public boolean isWall () {
        if (this.equals(SquareType.WALL))
            return true;
        else
            return false;
    }

    /**
     * Determines if the given type of position is
     * a start position.
     * @return true, if position is a START
     *         false, otherwise
     */
    public boolean isStart () {
        if (this.equals(SquareType.START))
            return true;
        else
            return false;
    }

    /**
     * Creates a Square object, base on a character.
     * @param symbol
     *    character to be used to determine the type of Square
     * @return  a square object if symbol is one of 'x', 'o' or ' ',
     *    null, otherwise
     */
    public static SquareType fromChar (char symbol) {
        if (symbol == 'x') return SquareType.WALL;
        else if (symbol == ' ')  return SquareType.CORRIDOR;
        else if (symbol == 'o')  return SquareType.WAYOUT;
        return null;
    }

}