/**
 * This interface is used for implementation of a Queue, has three methods enqueue, dequeue, getSize
 * @param <E> This is the type, that can be defined by other classes
 * @author Almaz Alikhan
 */
public interface Queue<E> {
	/**
	 * This method is used to add into the Queue
	 * @param value the element that is added to the Queue
	 */
    public void enqueue(E value);
    /**
     * This method removes and returns the first element in the Queue
     * @return returns the value of the first element in Queue
     */
    public E dequeue();
    /**
     * This method returns the size of the queue
     * @return it returns the size of the queue
     */
    public int getSize();
    void clear();
}