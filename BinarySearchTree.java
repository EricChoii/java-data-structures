import java.util.LinkedList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Binary Search Tree implementation with a Node inner class for representing
 * the nodes within a binary search tree.  You can use this class' insert
 * method to build a binary search tree, and its toString method to display
 * the level order (breadth first) traversal of values in that tree.
 */
public class BinarySearchTree<T extends Comparable<T>> {

    /**
     * This class represents a node holding a single value within a binary tree
     * the parent, left, and right child references are always be maintained.
     */
    protected static class Node<T> {
        public T data;
        public Node<T> parent; // null for root node
        public Node<T> leftChild; 
        public Node<T> rightChild; 
        public Node(T data) { this.data = data; }
        /**
         * @return true when this node has a parent and is the left child of
         * that parent, otherwise return false
         */
        public boolean isLeftChild() {
            return parent != null && parent.leftChild == this;
        }
        /**
         * This method performs a level order traversal of the tree rooted
         * at the current node.  The string representations of each data value
         * within this tree are assembled into a comma separated string within
         * brackets (similar to many implementations of java.util.Collection).
         * @return string containing the values of this tree in level order
         */
        @Override
        public String toString() { // display subtree in order traversal
            String output = "[";
            LinkedList<Node<T>> q = new LinkedList<>();
            q.add(this);
            while(!q.isEmpty()) {
                Node<T> next = q.removeFirst();
                if(next.leftChild != null) q.add(next.leftChild);
                if(next.rightChild != null) q.add(next.rightChild);
                output += next.data.toString();
                if(!q.isEmpty()) output += ", ";
            }
            return output + "]";
        }
    }

    protected Node<T> root; // reference to root node of tree, null when empty

    /**
     * Performs a naive insertion into a binary search tree: adding the input
     * data value to a new node in a leaf position within the tree.  After  
     * this insertion, no attempt is made to restructure or balance the tree.
     * This tree will not hold null references, nor duplicate data values.
     * @param data to be added into this binary search tree
     * @throws NullPointerException when the provided data argument is null
     * @throws IllegalArgumentException when the tree already contains data
     */
    public void insert(T data) throws NullPointerException,
				      IllegalArgumentException {
        // null references cannot be stored within this tree
        if(data == null) throw new NullPointerException(
            "This RedBlackTree cannot store null references.");

        Node<T> newNode = new Node<>(data);
        if(root == null) { root = newNode; } // add first node to an empty tree
        else insertHelper(newNode,root); // recursively insert into subtree
    }

    /** 
     * Recursive helper method to find the subtree with a null reference in the
     * position that the newNode should be inserted, and then extend this tree
     * by the newNode in that position.
     * @param newNode is the new node that is being added to this tree
     * @param subtree is the reference to a node within this tree which the 
     *      newNode should be inserted as a descenedent beneath
     * @throws IllegalArgumentException when the newNode and subtree contain
     *      equal data references (as defined by Comparable.compareTo())
     */
    private void insertHelper(Node<T> newNode, Node<T> subtree) {
        int compare = newNode.data.compareTo(subtree.data);
        // do not allow duplicate values to be stored within this tree
        if(compare == 0) throw new IllegalArgumentException(
            "This RedBlackTree already contains that value.");

        // store newNode within left subtree of subtree
        else if(compare < 0) {
            if(subtree.leftChild == null) { // left subtree empty, add here
                subtree.leftChild = newNode;
                newNode.parent = subtree;
            // otherwise continue recursive search for location to insert
            } else insertHelper(newNode, subtree.leftChild);
        }

        // store newNode within the right subtree of subtree
        else { 
            if(subtree.rightChild == null) { // right subtree empty, add here
                subtree.rightChild = newNode;
                newNode.parent = subtree;
            // otherwise continue recursive search for location to insert
            } else insertHelper(newNode, subtree.rightChild);
        }
    }

    /**
     * This method performs a level order traversal of the tree. The string 
     * representations of each data value within this tree are assembled into a
     * comma separated string within brackets (similar to many implementations 
     * of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
     * @return string containing the values of this tree in level order
     */
    @Override
    public String toString() { return root.toString(); }

    /**
     * Performs the rotation operation on the provided nodes within this BST.
     * When the provided child is a leftChild of the provided parent, this
     * method will perform a right rotation.  When the provided child is a
     * rightChild of the provided parent, this method will perform a left
     * rotation.  When the provided nodes are not related in one of these ways,
     * this method will throw an IllegalArgumentException.
     * @param child is the node being rotated from child to parent position
     *      (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *      (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *      node references are not initially (pre-rotation) related that way
     */
    private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
	// TODO: Implement this method.
        int compare = child.data.compareTo(parent.data);

       	if (compare == 0)
	    throw new IllegalArgumentException("This RedBlackTree already contains that value.");

       	else if (compare < 0)
	    rightRotate(parent);
	else
	    leftRotate(parent);
    }

    /**
     * Rotate binary tree node with right child.
     */
    private void leftRotate(Node<T> x) {
	Node<T> y = x.rightChild;
       	x.rightChild = y.leftChild;
	if (y.leftChild != null) {
	    y.leftChild.parent = x;
	}
       	y.parent = x.parent;
	if (x.parent == null) { // x is root
	    this.root = y;
	} else if (x == x.parent.leftChild) { // x is left child
	    x.parent.leftChild = y;
	} else { // x is right child
	    x.parent.rightChild = y;
	}

	y.leftChild = x;
	x.parent = y;
    }

    /**
     * Rotate binary tree node with left child.
     */
    private void rightRotate(Node<T> x) {
	Node<T> y = x.leftChild;
	x.leftChild = y.rightChild;
	if (y.rightChild != null) {
	    y.rightChild.parent = x;
       	}
	y.parent = x.parent;
       	if (x.parent == null) { // x is root
	    this.root = y;
       	} else if (x == x.parent.rightChild) { // x is left child
	    x.parent.rightChild = y;
       	} else { // x is right child
	    x.parent.leftChild = y;
       	}
	
	y.rightChild = x;
       	x.parent = y;
    }
    
    // For the next two test methods, review your notes from the Module 4: Red 
    // Black Tree Insertion Activity.  Questions one and two in that activity
    // presented you with an initial BST and then asked you to trace the
    // changes that would be applied as a result of performing different
    // rotations on that tree. For each of the following tests, you'll first
    // create the initial BST that you performed each of these rotations on.
    // Then apply the rotations described in that activity: the right-rotation
    // in the Part1 test below, and the left-rotation in the Part2 test below.
    // Then ensure that these tests fail if and only if the level ordering of
    // tree values dose not match the order that you came up with in that
    // activity.

    @Test
    public void week04ActivityTestPart1() {
        // TODO: Implement this method to recreate the example from:
	// Module 04: Red Black Tree Insert Activity - Step 1 (right rotation).
	BinarySearchTree<Integer> t = new BinarySearchTree<>();
	String answer = "[28, 16, 45, 32, 64, 57, 82]";
	
	t.insert(45);
        t.insert(28);
	t.insert(64);
        t.insert(16);
	t.insert(32);
        t.insert(57);
	t.insert(82);
	    
        t.rotate(t.root.leftChild, t.root);

	if(t.toString().equals(answer))
	    System.out.println("Right rotation Complete!\nThe level ordering of tree values: " + t);
	else
	    fail("This test has not been implemented.");
    }

    @Test
    public void week04ActivityTestPart2() {
        // TODO: Implement this method to recreate the example from:
	// Module 04: Red Black Tree Insert Activity - Step 2 (left rotation).
	BinarySearchTree<Integer> t = new BinarySearchTree<>();
	String answer = "[45, 32, 64, 28, 57, 82, 16]";
	
        t.insert(45);
        t.insert(28);
        t.insert(64);
        t.insert(16);
        t.insert(32);
	t.insert(57);
	t.insert(82);
	    
        t.rotate(t.root.leftChild.rightChild, t.root.leftChild);

	if(t.toString().equals(answer))
	    System.out.println("Left rotation Complete!\nThe level ordering of tree values: " + t);
	else
	    fail("This test has not been implemented.");
    }

}
