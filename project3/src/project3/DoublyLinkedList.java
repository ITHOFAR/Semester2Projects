package project3;

import java.util.Iterator;
import java.util.Objects;

/**
 * DoublyLinkedList class, allows access to next node and previous node
 * Contains a head and tail node
 * Contains two nested classes: Node and Iterator
 * Does not allow null objects to be added
 * Inspired by code from Joanna K in her LinkedList with Iterator project on Ed
 *
 * @param <E> the type of the parameter the DoublyLinkedList contains
 * @author Isaac Harris
 * @version March 28th, 2021
 */
public class DoublyLinkedList<E> implements Iterable<E> {

    //Two fields that are needed to add and remove from the front and rear.
    //No getters or setters since other methods take care of that
    private Node<E> head;
    private Node<E> tail;
    private int size;

    /**
     * Constructor sets head and tail to null
     * Creates an empty DoublyLinkedList
     */
    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Adds an element at the end of the doublylinkedlist
     * element to be added cannot be null
     *
     * @param e the element to be added at the end of the list
     * @return true if added, false if element is null
     * @throws ClassCastException This exception should never be thrown, instead the caller of this method would throw
     *                            the exception in runtime.
     */
    public boolean add(E e) throws ClassCastException {
        //Code does not throwClassCastException based on Ed Post #393 by Joanna K

        //checking when element is null
        if (e == null) {
            return false;
        }

        Node<E> newNode = new Node<E>(e);

        //checking if list is empty
        if (head == null) {
            head = newNode;
            tail = newNode;
        }
        //adding to the end of the list
        else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }

        size++;
        return true;
    }

    /**
     * Adds element at the specified index, all elements after index will be shifted to the right
     *
     * @param e   the input to be added to the list at index
     * @param pos the index the element will be added at
     * @return true if added, false if element is null
     * @throws ClassCastException        This exception should never be thrown, instead the caller of this method would throw
     *                                   the exception in runtime.
     * @throws IndexOutOfBoundsException thrown when the index is less than zero or greater than size of list
     */
    public boolean add(E e, int pos) throws ClassCastException, IndexOutOfBoundsException {
        //Code does not throwClassCastException based on Ed Post #393 by Joanna K

        //checking if element is null
        if (e == null) {
            return false;
        }

        //checking is pos is invalid
        if (pos < 0 || pos > size) {
            throw new IndexOutOfBoundsException("index is out of bounds");
        }

        Node<E> newNode = new Node<>(e);

        if (size == 0) { //checking if list is empty
            head = newNode;
            tail = newNode;
        }
        else if (pos == 0) { //checking if adding to front of the list
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
        }
        else if (pos == size) { //checking if adding to the end
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
        else {
            //new element will be inserted after this node
            Node<E> currentNode = head;

            //Iterating through the list to get to element before index
            for (int i = 0; i < pos - 1; i++) {
                currentNode = currentNode.next;
            }

            newNode.next = currentNode.next;
            currentNode.next.previous = newNode; //because of previous cases, this cannot be null
            currentNode.next = newNode;
            newNode.previous = currentNode;
        }

        size++;
        return true;
    }

    /**
     * Clears the list of all elements by setting head and tail to null
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Checks if list contains the object that is inputted
     *
     * @param o the object we are trying to see if the list contains
     * @return returns true if the list contains the object, false if not
     */
    public boolean contains(Object o) {

        //since our list does not contain null elements, returns false if object is null
        if (o == null) {
            return false;
        }

        Iterator<E> thisItr = this.iterator();

        while (thisItr.hasNext()) {
            E current = thisItr.next();
            if (Objects.equals(o, current)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if list is equal to object that is inputted
     * Checks if they are the same object, null, same type, same size, and all elements are equal
     *
     * @param o object that we are comparing to see if equal with list
     * @return true if list and object meet conditions, false if not
     */
    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null) return false;

        if (!(o instanceof DoublyLinkedList<?>)) return false;

        DoublyLinkedList<?> other = (DoublyLinkedList<?>) o;

        if (this.size() != other.size()) return false;

        Iterator<E> thisItr = this.iterator();
        Iterator<?> otherItr = other.iterator();

        //checking elements in both list if they are equal
        while (thisItr.hasNext() && otherItr.hasNext()) {
            if (!(thisItr.next().equals(otherItr.next()))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns element at specified index
     *
     * @param pos the index of the position we return
     * @return returns element at index, if index is invalid return null
     */
    public E get(int pos) {

        //checking if invalid index
        if (pos < 0 || pos >= size) {
            return null;
        }
        //checking if index is the last element
        if (pos == size - 1) {
            return tail.data;
        }
        int counter = 0;
        Node<E> currentNode = head;

        while (counter < pos) {
            currentNode = currentNode.next;
            counter++;
        }

        return currentNode.data;
    }

    /**
     * Checks if the list contains no elements
     *
     * @return true if list is empty, false if contains elements
     */
    public boolean isEmpty() {

        if (size == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Finds index of object that is inputted in the list.
     *
     * @param o object that we want to find the index of in the list
     * @return returns the index of element, if no such element exist returns -1
     */
    public int indexOf(Object o) {

        int index = 0;

        //checking if list is empty or object is null
        if (head == null || o == null) {
            return -1;
        }

        Iterator<E> thisItr = this.iterator();

        while (thisItr.hasNext()) {
            E current = thisItr.next();
            if (current.equals(o)) {
                return index;
            }
            index++;
        }

        return -1;
    }

    /**
     * Removes inputted object from the list. Shifts everything after element to the left.
     *
     * @param o the object that is to be removed.
     * @return returns true if element is removed, false if nothing in the list changes.
     * @throws ClassCastException   throws exception if element to be removed is not of the type of elements in list
     * @throws NullPointerException throws exception if element to be removed is null
     */
    public boolean remove(Object o) throws ClassCastException, NullPointerException {

        if (head == null) {
            return false;
        }
        if (o == null) {
            throw new NullPointerException("Cannot remove a null element");
        }
        //checking if type of element to be added matches elements in list
        if (head != null && !(head.data.getClass().equals(o.getClass()))) {
            throw new ClassCastException("Type of the element to be removed does not match type of objects in list");
        }

        int index = indexOf(o);
        //checks if element exist in the list
        if (index == -1) return false;

        Node<E> currentNode = head;
        if (index == 0) {
            head = head.next;
        }
        else {
            for (int i = 0; i < index - 1; i++) { //brings current node to element before index of removal
                currentNode = currentNode.next;
            }
            if (index == size - 1) { //fixes tail pointer and prevents null.previous in else block of code
                currentNode.next = currentNode.next.next;
                tail = currentNode;
            }
            else {
                currentNode.next.next.previous = currentNode;
                currentNode.next = currentNode.next.next;
            }
        }

        size--;
        return true;
    }

    /**
     * Remove element at specified index in the list and returns said element
     *
     * @param pos index at which element is to be removed
     * @return returns the element at specified index
     * @throws IndexOutOfBoundsException throws exception when index is less than 0 or greater than or equal to size.
     */
    public E remove(int pos) throws IndexOutOfBoundsException {

        if (head == null) {
            return null;
        }

        //checks if index is valid
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("index is out of bounds");
        }

        //checks if removing from front of list
        if (pos == 0) {
            E temp = head.data;
            head = head.next;
            if (size == 1) { //fixes tail if only one element in list
                tail = null;
            }
            size--;
            return temp;
        }

        Node<E> currentNode = head;
        for (int i = 0; i < pos - 1; i++) {
            currentNode = currentNode.next;
        }

        E temp = currentNode.next.data;
        if (pos == size - 1) { //handles when removing from end of list
            currentNode.next = currentNode.next.next;
            tail = currentNode;
        }
        else {
            currentNode.next.next.previous = currentNode;
            currentNode.next = currentNode.next.next;
        }

        size--;
        return temp;
    }

    /**
     * Returns the size of list
     *
     * @return returns the size of the list
     */
    public int size() {

        //checks if list is empty
        if (head == null) {
            return 0;
        }
        return size;
    }

    /**
     * Returns string of elements in format of "[x, x, x...]"
     *
     * @return a string of elements in above mentioned format
     */
    @Override
    public String toString() {

        //checking if list is empty
        if (size == 0) {
            return "[]";
        }

        //creating a string builder to allow appending elements to string
        StringBuilder strOutput = new StringBuilder();
        strOutput.append("[");
        int counter = 0;
        Iterator<E> thisItr = iterator();

        while (thisItr.hasNext()) {
            E currentData = thisItr.next();
            if (counter < size - 1) { //handling when adding last element to string
                strOutput.append(String.valueOf(currentData));
                strOutput.append(", ");
            }
            else {
                strOutput.append(String.valueOf(currentData));
            }
            counter++;
        }
        strOutput.append("]");
        String output = strOutput.toString();

        return output;
    }

    /**
     * Inner node class used for doubly linked list
     * Contains data, a next node, and a previous node
     * Inspired by Joanna K Node class in Linked List workspace on Ed
     *
     * @param <E> the type of element that is the data of the node
     * @author Isaac Harris
     * @version March 26th, 2021
     */
    private class Node<E> {

        public E data;
        public Node<E> next;
        public Node<E> previous;

        /**
         * constructs a new node that checks if data is null
         * sets next node and previous node to null
         *
         * @param data the element to be in the node, cannot be null
         * @throws NullPointerException throws exception if data is null
         */
        public Node(E data) {

            if (data == null) {
                throw new NullPointerException("list does not allow null elements");
            }
            this.data = data;
            this.next = null;
            this.previous = null;
        }
    }

    /**
     * Inner iterator class used to iterate through list
     * implements interface Iterator<E>
     * Inspired by Joanna K Iterator class in Linked List workspace on Ed
     *
     * @author Isaac Harris
     * @version March 26th, 2021
     */
    private class Itr implements Iterator<E> {

        private Node<E> current = head;

        /**
         * Checks if there is a next element in the list
         *
         * @return returns true if the current element is not null, false if not
         */
        public boolean hasNext() {
            return current != null;
        }

        /**
         * returns data of current node and makes current node the next node
         *
         * @return returns data of current node
         */
        public E next() {
            E temp = current.data;
            current = current.next;
            return temp;
        }
    }

    /**
     * Generates a new iterator to be used by the list
     *
     * @return returns an iterator to be used by the list
     */
    public Iterator<E> iterator() {
        return new Itr();
    }
}
