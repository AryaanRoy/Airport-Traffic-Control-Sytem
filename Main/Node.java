/**
 * This class is created to act as a node for the NodeQueue
 * @param <E> This is the type, that is used for the Queue
 * 
 * @see NodeQueue
 * @author Almaz Alikhan
 */
public class Node<E> {
	/**
	 *  Initializes the referenced type E, which will be used to store the value of the Node
	 */
    private E value;
    /**
     *  Initializes the referenced type Node, for storing the value of next Node
     */
    Node<E> next;

    /**
     * Constructor that initializes value and sets next to null
     * @param value the value of the this Node
     */
    public Node(E value) {
        this.value = value;
        this.next = null;
    }
    
    /**
     * An overloaded constructor that initializes value and next
     * @param value the value of the current Node
     * @param next the value of the next Node
     */
    public Node(E value, Node<E> next) {
        this.next = next;
        this.value = value;
    }
    
    /**
     * Method that returns the value of the current Node
     * @return it returns the value of the Node
     */
    public E getValue() {
        return value;
    }
    
    /**
     * Mehtod that returns the value of the next Node
     * @return It reutrns value of the next Node
     */
    public Node<E> getNext() {
        return next;
    }
    
    /**
     * Method that sets the value of the next Node
     * @param nextNode value for the next Node
     */
    public void setNext(Node<E> nextNode) {
        this.next = nextNode;

    }

    @Override
    public String toString() {
        return "Node: " + this.value;
    }

}