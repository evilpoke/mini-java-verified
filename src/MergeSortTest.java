
import java.util.Arrays;
import java.util.Random;

public class MergeSortTest {





    public static void main(String[] args) throws Exception {
        // Zufälliges großes Array zum Testen
        int n = 1000000;
        int maxValue = 10000000;
        Random rand = new Random();

        int[] randomArray = new int[n];
        for (int i = 0; i < n; i++) {
            randomArray[i] = rand.nextInt(maxValue);
        }
        int[] sortedArray = Arrays.copyOf(randomArray, randomArray.length);
        Arrays.sort(sortedArray);

        int[] copy1 = Arrays.copyOf(randomArray, randomArray.length);

        long timeStart = System.nanoTime();
        ParallelMergeSort.mergeSort(copy1, 0);
/*
        int d1 = copy1[copy1.length-15];
        int d2 = sortedArray[copy1.length-15];
        int d3 = randomArray[copy1.length-15];
*/
        long timeEnd = System.nanoTime();
        long timeDiff = timeEnd - timeStart;
        assertTrue("Single-Threaded MergeSort - Das Array sollte sortiert sein!",
                Arrays.equals(sortedArray, copy1));
        System.out.println("Single-Threaded MergeSort MergeSort took: " + timeDiff + " nanoseconds.");

        copy1 = Arrays.copyOf(randomArray, randomArray.length);
        timeStart = System.nanoTime();
        ParallelMergeSort.mergeSort(copy1, 4);
        timeEnd = System.nanoTime();
        timeDiff = timeEnd - timeStart;
        assertTrue("ParallelMergeSort - n = 4 - Das Array sollte sortiert sein!",
                Arrays.equals(sortedArray, copy1));
        System.out.println("Parallel MergeSort with n =   4 took: " + timeDiff + " nanoseconds.");

        // n = 8
        copy1 = Arrays.copyOf(randomArray, randomArray.length);
        timeStart = System.nanoTime();
        ParallelMergeSort.mergeSort(copy1, 8);
        timeEnd = System.nanoTime();
        timeDiff = timeEnd - timeStart;
        assertTrue("ParallelMergeSort - n = 8 - Das Array sollte sortiert sein!",
                Arrays.equals(sortedArray, copy1));
        System.out.println("Parallel MergeSort with n =   8 took: " + timeDiff + " nanoseconds.");

        // n = 16
        copy1 = Arrays.copyOf(randomArray, randomArray.length);
        timeStart = System.nanoTime();
        ParallelMergeSort.mergeSort(copy1, 16);
        timeEnd = System.nanoTime();
        timeDiff = timeEnd - timeStart;
        assertTrue("ParallelMergeSort - n = 16 - Das Array sollte sortiert sein!",
                Arrays.equals(sortedArray, copy1));
        System.out.println("Parallel MergeSort with n =  16 took: " + timeDiff + " nanoseconds.");

        // n = 32
        copy1 = Arrays.copyOf(randomArray, randomArray.length);
        timeStart = System.nanoTime();
        ParallelMergeSort.mergeSort(copy1, 32);
        timeEnd = System.nanoTime();
        timeDiff = timeEnd - timeStart;
        assertTrue("ParallelMergeSort - n = 32 - Das Array sollte sortiert sein!",
                Arrays.equals(sortedArray, copy1));
        System.out.println("Parallel MergeSort with n =  32 took: " + timeDiff + " nanoseconds.");

        // n = 128
        copy1 = Arrays.copyOf(randomArray, randomArray.length);
        timeStart = System.nanoTime();
        ParallelMergeSort.mergeSort(copy1, 128);
        timeEnd = System.nanoTime();
        timeDiff = timeEnd - timeStart;
        assertTrue("ParallelMergeSort - n = 128 - Das Array sollte sortiert sein!",
                Arrays.equals(sortedArray, copy1));
        System.out.println("Parallel MergeSort with n = 128 took: " + timeDiff + " nanoseconds.");
    }

    public static void assertTrue(String msg, boolean bb){
        if(bb){
            System.out.println(msg);
        } else {
            System.err.println("Error:" + msg);
        }
    }


}
