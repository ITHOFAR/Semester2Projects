package project5;

/**
 * Represents a mountain using a BST
 * Stores the root and size of the mountain
 * Allows adding and removing nodes to represent a proper implementation of a BST
 * Heavily inspired by Joanna K's BST class on Ed
 *
 * @author Isaac Harris
 * @version 05/02/2021
 */
public class BSTMountain {

    private BSTNode root;
    private int size;

    //Helper variables to be used in add and remove respectively
    private boolean added;
    private boolean found;

    /**
     * Sets root to null and size to 0
     */
    public BSTMountain() {
        root = null;
        size = 0;
    }

    /**
     * Wrapper function for addRec
     * checks if data is null
     * increases size if successfully added to tree
     * Adds the inputted RestStop to the tree if not already present
     *
     * @param data the RestStop to be added to the tree
     * @return returns true if added, false if not
     */
    public boolean add(RestStop data) {
        added = false;

        if (data == null) {
            return added;
        }

        root = addRec(data, root);

        //increasing size
        if (added) size++;
        return added;
    }

    /**
     * recursive implementation of adding to the tree for the add method
     *
     * @param data The RestStop to be added to the tree
     * @param node the node at which the recursive call is made
     * @return returns a reference to the subtree in which the RestStop was added
     */
    private BSTNode addRec(RestStop data, BSTNode node) {

        if (node == null) {
            added = true;
            //updating depth of node
            BSTNode newNode = new BSTNode(data);
            newNode.depth = setDepth(newNode);
            return newNode;
        }

        int comp = 0;
        comp = node.data.compareTo(data);

        //finding the location to add the RestStop
        if (comp > 0) {
            //adding to the left subtree
            node.leftStop = addRec(data, node.leftStop);
        }
        else if (comp < 0) {
            //adding to the right subtree
            node.rightStop = addRec(data, node.rightStop);
        }
        else {
            //duplicate in tree, RestStop is not added
            added = false;
            return node;
        }
        //updating depth of the node
        node.depth = setDepth(node);

        return node;
    }

    /**
     * Removes the specified element from this tree if it is present.
     * Returns true if this tree contained the element (or equivalently,
     * if this tree changed as a result of the call).
     * (This tree will not contain the element once the call returns.)
     * @param target RestStop to be removed from this tree, if present
     * @return true if this set contained the specified element
     */
    public boolean remove(RestStop target)
    {
        //replace root with a reference to the tree after target was removed
        root = recRemove(target, root);

        //decreasing size if RestStop was removed
        if (found) size--;
        return found;
    }


    /**
     * Actual recursive implementation of remove method: find the node to remove.
     *
     * This function recursively finds and eventually removes the node with the target element
     * and returns the reference to the modified tree to the caller.
     *
     * @param target RestStop to be removed from this tree, if present
     * @param node node at which the recursive call is made
     */
    private BSTNode recRemove(RestStop target, BSTNode node)
    {
        if (node == null)  { //value not found
            found = false;
            return node;
        }

        int comp = 0 ;
        comp = target.compareTo(node.data);

        if (comp < 0)       // target might be in a left subtree
            node.leftStop = recRemove(target, node.leftStop);
        else if (comp > 0)  // target might be in a right subtree
            node.rightStop = recRemove(target, node.rightStop);
        else {          // target found, now remove it
            node = removeNode(node);
            found = true;
        }
        //updating depth
        node.depth = setDepth(node);

        return node;
    }

    /**
     * Actual recursive implementation of remove method: perform the removal.
     *
     * @param node the item to be removed from this tree
     * @return a reference to the node itself, or to the modified subtree
     */
    private BSTNode removeNode(BSTNode node) {
        RestStop data;
        if (node.leftStop == null) {   //handle the leaf and one child node with right subtree
            return node.rightStop;
        }
        else if (node.rightStop  == null) { //handle one child node with left subtree
            return node.leftStop;
        }
        else {                   //handle nodes with two children
            data = getPredecessor(node.leftStop);
            node.data = data;
            node.leftStop = recRemove(data, node.leftStop);
            return node;
        }
    }

    /**
     * Returns the information held in the rightmost node of subtree
     *
     * @param subtree root of the subtree within which to search for the rightmost node
     * @return returns data stored in the rightmost node of subtree
     */
    private RestStop getPredecessor(BSTNode subtree) throws NullPointerException {
        if (subtree==null) //this should not happen
            throw new NullPointerException("getPredecessor called with an empty subtree");
        BSTNode temp = subtree;
        while (temp.rightStop  != null)
            temp = temp.rightStop;
        return temp.data;
    }

    /**
     * Wrapper function for toStringTreeRec
     * prints out tree in preorder traversal
     * prints out label, supplies, obstacles, and level of each node
     * @return String representing the BST in pre order traversal
     */
    public String toStringTree() {
        StringBuffer sb = new StringBuffer();
        toStringTreeRec(sb, root, 0);
        return sb.toString();
    }

    /**
     * formats and adds information to the StringBuffer
     *
     * @param sb stringBuffer to be added to
     * @param node current Node to be added to StringBuffer
     * @param level current level of node in tree
     */
    private void toStringTreeRec(StringBuffer sb, BSTNode node, int level) {
        //display the node
        if (level > 0) {
            for (int i = 0; i < level - 1; i++) {
                sb.append("   ");
            }
            sb.append("|--");
        }
        if (node == null) {
            sb.append("->\n");
            return;
        }
        else {
            sb.append(node.data.getLabel() + node.data.getSupplies() + node.data.getObstacles() + level + "\n");
        }

        //display the left subtree
        toStringTreeRec(sb, node.leftStop, level + 1);
        //display the right subtree
        toStringTreeRec(sb, node.rightStop, level + 1);
    }

    /**
     * wrapper function for setDepthRec
     * updates depth of node
     * @param target node that will have its depth updated
     * @return returns an int that is the depth of the node
     */
    private int setDepth(BSTNode target) {
        return setDepthRec(root, 0, target);
    }

    /**
     * recursive implementation of setDepth
     * finds depth of node
     * @param node the node that we are currently at
     * @param level the current depth
     * @param target the node we want to find the depth of
     * @return returns an int that is the depth of the node
     */
    private int setDepthRec(BSTNode node, int level, BSTNode target) {
        if (node == null) {
            return level;
        }
        if (node.compareTo(target) > 0) {
            return setDepthRec(node.leftStop, level + 1, target);
        }
        else if (node.compareTo(target) < 0) {
            return setDepthRec(node.rightStop, level + 1, target);
        }
        else {
            return level;
        }
    }

    //GETTERS
    /**
     * Getter for the size field
     *
     * @return returns an int representing the size of tree
     */
    public int size() {
        return size;
    }

    /**
     * A getter to return the root of the tree
     *
     * @return the root of the tree
     */
    public BSTNode getRoot() {
        if (root == null) {
            return null;
        }
        return root;
    }

    /**
     * Wrapper function for findDepth
     * Finds the maximum depth in the tree
     * Getter function for the max depth
     *
     * @return returns an int representing the max depth of tree
     */
    public int getMaxDepth() {
        return findDepth(this.root);
    }

    /**
     * recursive implementation to getMaxDepth
     * Finds the maximum depth of the tree
     * @param node the node where we find the depth from
     * @return returns the maximum depth of the subtrees plus one
     */
    private int findDepth(BSTNode node) {
        if (node == null) {
            return 0;
        }
        else {
            int maxHeight = 1 + Math.max(findDepth(node.rightStop), findDepth(node.leftStop));
            return maxHeight;
        }
    }

    /**
     * Node class for this BST
     * visibility set to package-private (default) to allow use in MountainSolver
     */
    class BSTNode implements Comparable<BSTNode> {

        private RestStop data;
        private BSTNode leftStop;
        private BSTNode rightStop;
        //depth stored for use in MountainSolver
        private int depth;

        /**
         * Instantiates a new Bst node.
         *
         * @param data the RestStop to be stored in the node
         */
        public BSTNode(RestStop data) {
            this.data = data;
            this.depth = 0;
        }

        /**
         * Instantiates a new Bst node.
         *
         * @param data      the RestStop to be stored in the node
         * @param leftStop  the left child node
         * @param rightStop the right child node
         */
        public BSTNode(RestStop data, BSTNode leftStop, BSTNode rightStop) {
            this.data = data;
            this.leftStop = leftStop;
            this.rightStop = rightStop;
            this.depth = 0;
        }

        /**
         * compares RestStop of current node to RestStop of other node
         * @param other Node to be compared to, compares RestStops
         * @return returns integer that tells if node is larger or smaller than other node
         */
        public int compareTo(BSTNode other) {
            return this.data.compareTo(other.data);
        }

        //GETTERS
        /**
         * Gets restStop stored in node
         *
         * @return the rest stop
         */
        public RestStop getRestStop() {
            return data;
        }

        /**
         * Gets left child node
         *
         * @return the left child node
         */
        public BSTNode getLeftStop() {
            return leftStop;
        }

        /**
         * Gets right child node
         *
         * @return the right child node
         */
        public BSTNode getRightStop() {
            return rightStop;
        }

        /**
         * Gets depth of node.
         *
         * @return the depth
         */
        public int getDepth() {
            return depth;
        }
    }
}
