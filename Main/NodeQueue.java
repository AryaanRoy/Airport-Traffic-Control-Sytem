/**
 * This class implements Queue
 * @see Queue
 * @param <E> This is the type, that is used for the Queue
 */
public class NodeQueue<E> implements Queue<E> {
	/**
	 * Initializes using referenced type Node<E>, It is first element of the Queue
	 */
    private Node<E> front;

    /**
     * Initializes using referenced type Node<E>, It is last element of the Queue
     */
    private Node<E> back;
    /**
     * Initializes using primitive type integer, It is the size of the Queue
     */
    private int size = 0;



    /**
     * A constructor that set the values of front and back to null
     */
    NodeQueue() {
        this.front = null;



        this.back = null;
    }

    @Override
    public void enqueue(E value) {



        Node<E> newNode = new Node<>(value);
        if (size==0) {
            this.front = this.back = newNode;
            size= size + 1;
            return;
        }         
        this.back.next = newNode;
        this.back = newNode;
        size++;
        return;
    }
    
    @Override
    public E dequeue() {


        if (this.front == null) {
            return null;
        }
        Node<E> temp = this.front;
        this.front = this.front.getNext();
        size--;
        if (this.front == null) {
            this.back = null;
        }
        return temp.getValue();
    }
    
    @Override
    public int getSize() {
        return size;
    }
    /**
     * This method returns first value in the Queue
     * @return It returns first value in the queue
     * 		   Or reutrns null if empty
     */
    public Node<E> getFirst() {
        return front;
    }
    /**
     * This method returns the last value in the Queue
     * @return  It returns the last value in the Queue
     * 			Or returns null if empty
     */
    public Node<E> getLast() {
        return back;
    }
    /**
     * This mehtod checks if the Queue is empty
     * @return It returns 0, if queue is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
    // Clear all elements by resetting the front and back pointers and size
        this.front = null;
        this.back = null;
        this.size = 0;
}

public void remove(int index) {

    // If the index is 0, remove the front
    if (index == 0) {
        front = front.next;
    } else {
        Node<E> current = front;
        for (int i = 0; i < index - 1; i++) {
            if (current == null) {
                throw new IndexOutOfBoundsException("Invalid index: " + index);
            }
            current = current.next;
        }
        if (current.next == null) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        current.next = current.next.next;
    }

    size--;

}


}