package testingstuff;

import java.util.Arrays;

public class Parallelverordnung implements Runnable {
    private int[] numbers;

    public Parallelverordnung(int[] numbers) {
        this.numbers = numbers;
    }


    void perm(int from) {
        if (from == numbers.length - 1) {
            System.out.println(Arrays.toString(numbers));
        } else {
            perm(from + 1);
            for (int j = from + 1; j < numbers.length; j++) {
                int t = numbers[j];
                numbers[j] = numbers[from];
                numbers[from] = t;
                perm(from + 1);
                numbers[from] = numbers[j];
                numbers[j] = t;
            }
        }
    }

    @Override
    public void run() {
        perm(1);
    }

    public static void main(String[] args) {
        int[] numbers = readArray();

        Thread[] threads = new Thread[numbers.length];
        for (int i = 0; i < threads.length; i++) {
            int[] numbersThread = new int[numbers.length];
            System.arraycopy(numbers, 0, numbersThread, 0, numbers.length);
            numbersThread[0] = numbers[i];
            numbersThread[i] = numbers[0];
            threads[i] = new Thread(new Parallelverordnung(numbersThread));
            threads[i].start();
        }

        for (Thread t : threads)
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    static int[] readArray() {
        return new int[] {1, 2, 3, 4, 5, 7, 8, 9, 10};
    }

} // Ende der Klasse Parallelverordnung
