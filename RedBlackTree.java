// --== CS400 File Header Information ==--
// Name:                Eric Choi
// Email:               hchoi256@wisc.edu
// Team:                CT
// Role:                Test Engineer
// TA:                  Mu Cai
// Lecturer:            Gary
// Notes to Grader:     <optional extra notes>

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Binary Search Tree implementation with a Node inner class for representing
 * the nodes within a binary search tree. You can use this class' insert method
 * to build a binary search tree, and its toString method to display the level
 * order (breadth first) traversal of values in that tree.
 */
public class RedBlackTree<T extends Comparable<T>> {

	/**
	 * This class represents a node holding a single value within a binary tree the
	 * parent, left, and right child references are always be maintained.
	 */
	protected static class Node<T> {
		public T data;
		public boolean isBlack; // set to false initially
		public Node<T> parent; // null for root node
		public Node<T> leftChild;
		public Node<T> rightChild;

		/**
		 * Creates a new instance of Node with its data
		 * 
		 * @param data data of this Node
		 */
		public Node(T data) {
			this.data = data;
			this.isBlack = false; // all nodes are set to red initially
		}

		/**
		 * @return true when this node has a parent and is the left child of that
		 *         parent, otherwise return false
		 */
		public boolean isLeftChild() {
			return parent != null && parent.leftChild == this;
		}

		/**
		 * @return the color of this node, "B" for black, "R" for red.
		 */
		public String color() {
			return isBlack ? "B" : "R";
		}

		/**
		 * This method performs a level order traversal of the tree rooted at the
		 * current node. The string representations of each data value within this tree
		 * are assembled into a comma separated string within brackets (similar to many
		 * implementations of java.util.Collection).
		 * 
		 * @return string containing the values of this tree in level order
		 */
		@Override
		public String toString() { // display subtree in order traversal
			String output = "[";
			LinkedList<Node<T>> q = new LinkedList<>();
			q.add(this);
			while (!q.isEmpty()) {
				Node<T> next = q.removeFirst();
				if (next.leftChild != null)
					q.add(next.leftChild);
				if (next.rightChild != null)
					q.add(next.rightChild);
				output += next.data.toString();
				if (!q.isEmpty())
					output += ", ";
			}
			return output + "]";
		}

		/**
		 * This method draws a red black tree to the console by recursively building
		 * characters to a StringBuilder. Note that the root in this version is pointed
		 * by a line of different direction from Eric's version, because the root is
		 * considered as a "right child" since its isLeftChild() method will return
		 * false due to its parent is null. This is an optimzied version of tree-drawing
		 * method by Eric Choi, Haining.
		 * 
		 * @return StringBuilder a string builder (sequence of characters) containing
		 *         the visualization of the subtree of the current node
		 * @param prefix  a string builder (sequence of characters) containing the
		 *                visualization part that should go in front of the current node
		 *                in the RBT tree visualization
		 * @param current a string builder (sequence of characters) containing the
		 *                current visualization of the RBT tree to be recursively built
		 *                on
		 */
		protected StringBuilder drawHelper(StringBuilder prefix, StringBuilder current) {
			if (this.rightChild != null) {
				this.rightChild.drawHelper(
						new StringBuilder().append(prefix).append(this.isLeftChild() ? "│   " : "    "), current);
			}
			current.append(prefix).append(this.isLeftChild() ? "└── " : "┌── ")
					.append(this.data.toString() + this.color()).append("\n");
			if (this.leftChild != null) {
				this.leftChild.drawHelper(
						new StringBuilder().append(prefix).append(this.isLeftChild() ? "    " : "│   "), current);
			}
			return current;
		}
	}

	protected Node<T> root; // reference to root node of tree, null when empty

	/**
	 * Performs a naive insertion into a binary search tree: adding the input data
	 * value to a new node in a leaf position within the tree. After this insertion,
	 * no attempt is made to restructure or balance the tree. This tree will not
	 * hold null references, nor duplicate data values.
	 * 
	 * @param data to be added into this binary search tree
	 * @throws NullPointerException     when the provided data argument is null
	 * @throws IllegalArgumentException when the tree already contains data
	 */
	public void insert(T data) throws NullPointerException, IllegalArgumentException {
		// null references cannot be stored within this tree
		if (data == null)
			throw new NullPointerException("This RedBlackTree cannot store null references.");

		Node<T> newNode = new Node<>(data);
		if (root == null) {
			root = newNode;
		} // add first node to an empty tree
		else
			insertHelper(newNode, root); // recursively insert into subtree
		root.isBlack = true; // set root to be black
	}

	/**
	 * Recursive helper method to find the subtree with a null reference in the
	 * position that the newNode should be inserted, and then extend this tree by
	 * the newNode in that position.
	 * 
	 * @param newNode is the new node that is being added to this tree
	 * @param subtree is the reference to a node within this tree which the newNode
	 *                should be inserted as a descenedent beneath
	 * @throws IllegalArgumentException when the newNode and subtree contain equal
	 *                                  data references (as defined by
	 *                                  Comparable.compareTo())
	 */
	private void insertHelper(Node<T> newNode, Node<T> subtree) {
		int compare = newNode.data.compareTo(subtree.data);
		// do not allow duplicate values to be stored within this tree
		if (compare == 0)
			throw new IllegalArgumentException("This RedBlackTree already contains that value.");

		// store newNode within left subtree of subtree
		else if (compare < 0) {
			if (subtree.leftChild == null) { // left subtree empty, add here
				subtree.leftChild = newNode;
				newNode.parent = subtree;
				enforceRBTreePropertiesAfterInsert(newNode);
				// otherwise continue recursive search for location to insert
			} else
				insertHelper(newNode, subtree.leftChild);
		}

		// store newNode within the right subtree of subtree
		else {
			if (subtree.rightChild == null) { // right subtree empty, add here
				subtree.rightChild = newNode;
				newNode.parent = subtree;
				enforceRBTreePropertiesAfterInsert(newNode);
				// otherwise continue recursive search for location to insert
			} else
				insertHelper(newNode, subtree.rightChild);
		}
	}

	/**
	 * This method performs a level order traversal of the tree. The string
	 * representations of each data value within this tree are assembled into a
	 * comma separated string within brackets (similar to many implementations of
	 * java.util.Collection, like java.util.ArrayList, LinkedList, etc).
	 * 
	 * @return string containing the values of this tree in level order, "[]" if the
	 *         tree is empty (the root is null)
	 */
	@Override
	public String toString() {
		return root == null ? "[]" : root.toString();
	}

	/**
	 * Makes a call to the drawHelper method and return the complete tree
	 * visualization.
	 * 
	 * @return a string containing the complete tree visualization, "empty tree" if
	 *         the tree is empty (the root is null)
	 */
	public String drawTree() {
		return this.root == null ? "Empty Tree"
				: this.root.drawHelper(new StringBuilder(), new StringBuilder()).toString();
	}

	/**
	 * Performs the rotation operation on the provided nodes within this BST. When
	 * the provided child is a leftChild of the provided parent, this method will
	 * perform a right rotation (sometimes called a left-right rotation). When the
	 * provided child is a rightChild of the provided parent, this method will
	 * perform a left rotation (sometimes called a right-left rotation). When the
	 * provided nodes are not related in one of these ways, this method will throw
	 * an IllegalArgumentException.
	 * 
	 * @param child  is the node being rotated from child to parent position
	 *               (between these two node arguments)
	 * @param parent is the node being rotated from parent to child position
	 *               (between these two node arguments)
	 * @throws IllegalArgumentException when the provided child and parent node
	 *                                  references are not initially (pre-rotation)
	 *                                  related that way
	 */
	protected void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
		// TODO: Implement this method.
		if (parent.leftChild != child && parent.rightChild != child) {
			throw new IllegalArgumentException();
		}
		if (child == null || parent == null) {
			throw new IllegalArgumentException();
		}
		if (child.isLeftChild()) {
			// Right rotation
			parent.leftChild = child.rightChild;
			if (child.rightChild != null)
				child.rightChild.parent = parent;
			child.rightChild = parent;
		} else {
			// Left rotation
			parent.rightChild = child.leftChild;
			if (child.leftChild != null)
				child.leftChild.parent = parent;
			child.leftChild = parent;
		}
		if (parent == root) {
			root = child;
		} else if (parent.isLeftChild()) {
			parent.parent.leftChild = child;
		} else {
			parent.parent.rightChild = child;
		}
		child.parent = parent.parent;
		parent.parent = child;
	}

	/**
	 * Enforces the RBT properties after a red node insertion
	 * 
	 * @param inserted
	 */
	private void enforceRBTreePropertiesAfterInsert(Node<T> inserted) {
		if (inserted == root || inserted.parent == root)
			return;
		if (inserted.parent.isBlack)
			return;
		if (inserted.parent.parent.leftChild != null && inserted.parent.parent.rightChild != null
				&& !inserted.parent.parent.leftChild.isBlack && !inserted.parent.parent.rightChild.isBlack) {
			// case 1: parent's sibling is red (i.e. parent's parent has two red children)
			inserted.parent.parent.leftChild.isBlack = true; // change parent and parent's sibling both
			inserted.parent.parent.rightChild.isBlack = true; // to black
			inserted.parent.parent.isBlack = false; // change parent's parent to red
			enforceRBTreePropertiesAfterInsert(inserted.parent.parent);
		} else if (!(inserted.isLeftChild() ^ inserted.parent.isLeftChild())) {
			// case 2: parent's sibling is black and on the opposite side as the new node
			rotate(inserted.parent, inserted.parent.parent);
			// maintain the black height equality
			inserted.parent.isBlack = true;
			inserted.parent.leftChild.isBlack = false;
			inserted.parent.rightChild.isBlack = false;
		} else {
			// case 3: parent's sibling is black and on the same side as the new node
			rotate(inserted, inserted.parent);
			if (inserted.isLeftChild()) {
				enforceRBTreePropertiesAfterInsert(inserted.leftChild);
			} else {
				enforceRBTreePropertiesAfterInsert(inserted.rightChild);
			}
		}
	}

	/**
	 * Performs a naive removal from a binary search tree: removing the input data
	 * value that is contained by an existing node in a leaf position within the
	 * tree. Balancing and property-enforcing is performed in helper method. Null
	 * pointer CANNOT be removed nor passed as an argument to this method.
	 * 
	 * @param data data to be removed from this tree
	 * @return the node removed from thie tree
	 * @throws NullPointerException     when this tree is empty
	 * @throws IllegalArgumentException when the data is not contained in the tree
	 */
	public Node<T> remove(T data) throws NullPointerException, IllegalArgumentException {
		if (root == null)
			throw new NullPointerException();
		Node<T> nodeRemove = new Node<T>(data);
		return removeHelper(nodeRemove, root);
	}

	/**
	 * Recursive helper method for finding the node to be removed in the RBT and
	 * remove that node from the tree while maintaining all properties by calling
	 * the property-enforcing method.
	 * 
	 * @param nodeRemove node to be removed
	 * @param subtree    is the reference to a node within this tree in which the
	 *                   nodeRemove should be located
	 * @return the node removed from the tree
	 */
	private Node<T> removeHelper(Node<T> nodeRemove, Node<T> subtree) {
		int compare = nodeRemove.data.compareTo(subtree.data);
		if (compare == 0) { // Node is found in the RBT
			enforceRBTreePropertiesAfterRemove(subtree);
			return subtree;
		} else if (compare < 0) { // Node should be in the left subtree
			if (subtree.leftChild == null) { // Node does not exist in this RBT
				throw new IllegalArgumentException();
			} else
				removeHelper(nodeRemove, subtree.leftChild); // Recurse on the left child
		} else { // Node should be in the right subtree
			if (subtree.rightChild == null) { // Node does not exist in this RBT
				throw new IllegalArgumentException();
			} else
				removeHelper(nodeRemove, subtree.rightChild); // Recurse on the right child
		}
		return null; // This method will not reach this line
	}

	/**
	 * Enforces the RBT properties after a removal of either a red node or a black
	 * node
	 * 
	 * @param removed node that should be removed
	 */
	private void enforceRBTreePropertiesAfterRemove(Node<T> removed) {
		if (removed.leftChild != null && removed.rightChild != null) {
			// Case 1: removing a node, either red or black, with two children
			// Replace this node with the smallest node in the right subtree
			Node<T> smallestInRight = removed.rightChild;
			while (smallestInRight.leftChild != null) {
				smallestInRight = smallestInRight.leftChild;
			}
			// The data is replaced with the data stored in the smallest node in the right
			// subtree
			// This "removes" the node (i.e. clears the data) without changing any
			// relationship nor any
			// colors
			removed.data = smallestInRight.data;
			// Recurse on the smallest node in the right subtree to remove that node from
			// the tree
			// Note that in this recursion, a case 3 or a case 4 should be encountered
			enforceRBTreePropertiesAfterRemove(smallestInRight);
		} else if (removed.leftChild != null || removed.rightChild != null) {
			// Case 2: removing a black node with exactly one red child (the only possible
			// case)
			if (removed.leftChild != null) {
				// The data is replaced with the data stored in the left child
				// This "removes" the node (i.e. clears the data) without changing any
				// relationship nor any
				// colors
				removed.data = removed.leftChild.data;
				// Recurse on the left child to remove left child node
				enforceRBTreePropertiesAfterRemove(removed.leftChild);
			} else {
				// The data is replaced with the data stored in the right child
				// This "removes" the node (i.e. clears the data) without changing any
				// relationship nor any
				// colors
				removed.data = removed.rightChild.data;
				// Recurse on the right child to remove right child node
				// Note that in this recursion, a case 3 or a case 4 should be encountered
				enforceRBTreePropertiesAfterRemove(removed.rightChild);
			}
		} else if (!removed.isBlack) {
			// Case 3: remove a red node with no children
			if (removed.isLeftChild()) {
				removed.parent.leftChild = null;
			} else {
				removed.parent.rightChild = null;
			}
		} else {
			// Case 4: remove a black node with no children
			resolveDoubleBlack(removed);
			// Once double black is resolved in the tree, the node to be removed is safe to
			// be removed
			if (removed.parent == null) // if the node removed is root
				root = null; // then set root to null
			else if (removed.isLeftChild()) {
				removed.parent.leftChild = null;
			} else {
				removed.parent.rightChild = null;
			}
		}
	}

	/**
	 * Recursively resolves the double black node situation in the RBT tree until
	 * the tree has no double black tree and all properties are maintained.
	 * 
	 * @param doubleBlack the node that is deemed as a double black node
	 */
	private void resolveDoubleBlack(Node<T> doubleBlack) {
		if (doubleBlack == root)
			return; // Root is double black thus done
		if (!doubleBlack.parent.leftChild.isBlack || !doubleBlack.parent.rightChild.isBlack) {
			// Case 1: the sibling of the double black node is red
			if (doubleBlack.isLeftChild()) {
				// Color swap double black's parent and sibling and rotate
				doubleBlack.parent.isBlack = false;
				doubleBlack.parent.rightChild.isBlack = true;
				rotate(doubleBlack.parent.rightChild, doubleBlack.parent);
			} else {
				// Color swap double black's parent and sibling and rotate
				doubleBlack.parent.isBlack = false;
				doubleBlack.parent.leftChild.isBlack = true;
				rotate(doubleBlack.parent.leftChild, doubleBlack.parent);
			}
		} else if ((doubleBlack.isLeftChild()
				&& (doubleBlack.parent.rightChild.rightChild == null
						|| doubleBlack.parent.rightChild.rightChild.isBlack)
				&& (doubleBlack.parent.rightChild.leftChild == null || doubleBlack.parent.rightChild.leftChild.isBlack))
				|| (!doubleBlack.isLeftChild()
						&& (doubleBlack.parent.leftChild.rightChild == null
								|| doubleBlack.parent.leftChild.rightChild.isBlack)
						&& (doubleBlack.parent.leftChild.leftChild == null
								|| doubleBlack.parent.leftChild.leftChild.isBlack))) {
			// Case 2: the sibling of the double black node is black, and the children of
			// the sibling are
			// both black or null
			if (doubleBlack.isLeftChild()) {
				// Set sibling to red
				doubleBlack.parent.rightChild.isBlack = false;
			} else {
				// Set sibling to red
				doubleBlack.parent.leftChild.isBlack = false;
			}
			if (!doubleBlack.parent.isBlack) {
				// Set red parent to black and then done
				doubleBlack.parent.isBlack = true;
			} else {
				// Propagate double black up if parent is black, recursing on the double black
				// parent
				resolveDoubleBlack(doubleBlack.parent);
			}
		} else if ((doubleBlack.isLeftChild() && !doubleBlack.parent.rightChild.leftChild.isBlack
				&& (doubleBlack.parent.rightChild.rightChild == null
						|| doubleBlack.parent.rightChild.rightChild.isBlack))
				|| (!doubleBlack.isLeftChild() && !doubleBlack.parent.leftChild.rightChild.isBlack
						&& (doubleBlack.parent.leftChild.leftChild == null
								|| doubleBlack.parent.leftChild.leftChild.isBlack))) {
			// Case 3: the sibling of the double black node is black, and the same side (as
			// double black)
			// child of the sibling is red and the other child is black or null
			// Convert this case into case 4 by rotation and color swap between sibling and
			// its red child
			if (doubleBlack.isLeftChild()) {
				doubleBlack.parent.rightChild.isBlack = false;
				doubleBlack.parent.rightChild.leftChild.isBlack = true;
				rotate(doubleBlack.parent.rightChild.leftChild, doubleBlack.parent.rightChild);
			} else {
				doubleBlack.parent.leftChild.isBlack = false;
				doubleBlack.parent.leftChild.rightChild.isBlack = true;
				rotate(doubleBlack.parent.leftChild.rightChild, doubleBlack.parent.leftChild);
			}
			resolveDoubleBlack(doubleBlack); // Recurse on the original double black node for case 4
		} else {
			// case 4: the sibling of the double black node is black, and the opposite side
			// (as double
			// black) child of the sibling is red
			// Change the only known red node to black, and color swap double black's parent
			// and its
			// sibling and rotate them
			if (doubleBlack.isLeftChild()) {
				doubleBlack.parent.rightChild.rightChild.isBlack = true;
				doubleBlack.parent.rightChild.isBlack = doubleBlack.parent.isBlack;
				doubleBlack.parent.isBlack = true;
				rotate(doubleBlack.parent.rightChild, doubleBlack.parent);
			} else {
				doubleBlack.parent.leftChild.leftChild.isBlack = true;
				doubleBlack.parent.leftChild.isBlack = doubleBlack.parent.isBlack;
				doubleBlack.parent.isBlack = true;
				rotate(doubleBlack.parent.leftChild, doubleBlack.parent);
			}
		}
	}
}
