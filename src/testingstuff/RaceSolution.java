package testingstuff;

import java.util.concurrent.locks.ReentrantLock;

public class RaceSolution {

    public static void main(String args[]) throws InterruptedException {
        long svalue[] = {0};
        long sinc = 10000000; // must be big enough!

        ReentrantLock lock = new ReentrantLock();
        SecureCounter sc1 = new SecureCounter(svalue, lock, sinc);
        SecureCounter sc2 = new SecureCounter(svalue, lock, sinc); // same value, same lock!

        sc1.start();
        sc2.start();

        sc1.join();
        sc2.join();

        System.out
                .println("secure counter (much slower...why?): expected " + 2 * sinc + " got " + svalue[0]);
    }
}
