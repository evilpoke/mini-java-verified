public class Queue<T> {
    private T[] elements;
    private int start; //start is element that gets removed with dequeue
    private int stop;

    public int testvalue = 15;

    @SuppressWarnings("unchecked")
    public Queue() {
        this.elements = (T[]) new Object[3];
        this.start = 0;
        this.stop = 0;
    }

    /**
     * Shifts the elements in array elements one to the right
     */
    private void shiftonetoright() {

        if (start == 2) {
            //do nothing
        } else {
            elements[2] = elements[1];
            elements[1] = elements[0];
            elements[0] = null;
            stop++;
            start++;
        }
    }

    public void enqueue(T value) throws InterruptedException {
        synchronized (this) {
            while (stop == -1) {
                this.wait();
            }
            elements[stop] = value;
            stop--; //!!
            shiftonetoright();
            this.notify();
        }
    }

    public T dequeue() throws InterruptedException {
        synchronized (this) {
            while (stop == start) {
                wait();
            }
            T returnvalue = elements[start];

            start--;

            this.notify();

            return returnvalue;
        }
    }


}
