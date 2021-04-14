package project3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueueOfSpacesTest {

    @Test
    void add() {
        QueueOfSpaces queues = new QueueOfSpaces();
        queues.add(new Location(0,0));
        queues.add(new Location(0,01));
        for (int i = 0; i < 5; i++) {
            Location a = queues.remove();
            Location b = queues.fr
        }

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