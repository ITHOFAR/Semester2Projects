package project3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackOfSpacesTest {

    @Test
    void add() {
        StackOfSpaces stacks = new StackOfSpaces();
        stacks.add(new Location(0,0));
        stacks.add(new Location(0,01));
        stacks.add(new Location(0,02));
        stacks.add(new Location(0,03));
        stacks.remove();
        System.out.println(stacks.toString());

    }

    @Test
    void remove() {
    }

    @Test
    void isEmpty() {
    }

    @Test
    void testToString() {
    }
}