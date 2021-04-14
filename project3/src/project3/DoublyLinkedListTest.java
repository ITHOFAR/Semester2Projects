package project3;

import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;

class DoublyLinkedListTest {

    @org.junit.jupiter.api.Test
    void addEnd() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.add("First String");
        list.add("Second String");
        list.remove(1);
        list.remove(0);
        System.out.println(list.size());
        System.out.println(list.toString());
    }

    @org.junit.jupiter.api.Test
    void addIndex() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.add("First String", 0);
        list.add("Second String", 0);
        list.add("Third String", 2);
        list.remove("Third String");
        System.out.println(list.toString());
    }

    @org.junit.jupiter.api.Test
    void clear() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.add("First String", 0);
        list.add("Second String", 0);
        list.add("Third String", 2);
        list.clear();
        list.add("String");
        System.out.println(list.size());
    }

    @org.junit.jupiter.api.Test
    void contains() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.add("First String", 0);
        list.add("Second String", 0);
        list.add("Third String", 2);
        list.add(null);
        System.out.println(list.contains("Third String"));
    }

    @org.junit.jupiter.api.Test
    void testEquals() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.add("First String", 0);
        list.add("Second String", 0);
        list.add("Third String", 2);
        DoublyLinkedList<String> list1 = new DoublyLinkedList<>();
        list1.add("First String", 0);
        list1.add("Second String", 0);
        System.out.println(list.equals(list1));
    }

    @org.junit.jupiter.api.Test
    void get() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.add("First String", 0);
        list.add("Second String", 0);
        list.add("Third String", 2);
        System.out.println(list.get(3));

    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.add("First String", 0);
        list.add("Second String", 0);
        list.add("Third String", 2);
        list.clear();
        System.out.println(list.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void indexOf() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.add("First String");
        list.add("Second String");
        System.out.println( list.indexOf("First String"));
        System.out.println( list.indexOf("Second String"));
        System.out.println( list.indexOf("Third String"));
    }

    @org.junit.jupiter.api.Test
    void removeObject() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.remove("String");
        System.out.println(list.remove("String"));
    }

    @org.junit.jupiter.api.Test
    void removeIndex() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.add("First String", 0);
        list.add("Second String", 0);
        list.add("Third String", 2);
        list.remove(0);
        System.out.println(list.toString());
    }

    @org.junit.jupiter.api.Test
    void size() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.add("First String", 0);
        list.add("Second String", 0);
        list.add("Third String", 2);
        list.remove("Third String");
        list.remove(1);
        System.out.println(list.size());
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.add("First String", 0);
        list.add("Second String", 0);
        list.add("Third String", 2);
        list.remove("String");
        System.out.println(list.toString());
    }
}