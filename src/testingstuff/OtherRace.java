package testingstuff;

import java.util.concurrent.locks.ReentrantLock;

class SecureCounter extends Thread {
    private long target[]; // used as reference on value
    private long amount; // how many times shall we count

    private ReentrantLock lock;

    SecureCounter(long target[], ReentrantLock lock, long amount) {
        this.target = target;
        this.amount = amount;
        this.lock = lock;
    }

    public void run() {
        for (long c = 0; c < amount; c++) {
            lock.lock(); // enter critical section


            // synchronized(target) { //could use synchronized here
            // (target is shared between both threads and contains it's own lock)

            long tmp = target[0];
            tmp = tmp + 1;
            target[0] = tmp;

            // }

            lock.unlock(); // synchronized only works inside methods, lock can be held after leaving a
            // method
        }
    }
}


