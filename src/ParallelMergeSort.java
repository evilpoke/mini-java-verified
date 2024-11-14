
public class ParallelMergeSort extends Thread {
  private int[] arr;
  private int low;
  private int high;
  private int numberOfThreadLevels;

  public static void mergeSort(int[] arr, int numberOfThreadLevels) {
    ParallelMergeSort pM = new ParallelMergeSort(arr, 0, arr.length - 1, numberOfThreadLevels);
    pM.run(); // Keinen Thread starten!
  }

  private void mergeSort(int[] arr, int low, int high, int numberOfThreadLevels) {
    if (high <= low)
      return;

    int mid = (low + high) / 2;

    Thread leftSide = null;
    if (numberOfThreadLevels > 0) {
      leftSide = new ParallelMergeSort(arr, low, mid, numberOfThreadLevels / 2);
      leftSide.start();
    } else
      mergeSort(arr, low, mid, 0);

    mergeSort(arr, mid + 1, high, numberOfThreadLevels / 2);

    try {
      if (leftSide != null)
        leftSide.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    merge(arr, low, mid, high);
  }
  
  private static void merge(int[] arr, int low, int mid, int high) {
    int i = low;
    int j = mid + 1;
    int k = 0;

    final int SIZE = high - low + 1;
    int[] helperArr = new int[SIZE];

    while (i <= mid && j <= high) {
      if (arr[i] < arr[j]) {
        helperArr[k++] = arr[i++];
      } else {
        helperArr[k++] = arr[j++];
      }
    }

    while (i <= mid) {
      helperArr[k++] = arr[i++];
    }
    while (j <= high) {
      helperArr[k++] = arr[j++];
    }
    for (i = 0; i < SIZE; i++) {
      arr[low + i] = helperArr[i];
    }
  }

  @Override
  public void run() {
    mergeSort(arr, low, high, numberOfThreadLevels);
  }

  public ParallelMergeSort(int[] arr, int low, int high, int numberOfThreads) {
    this.arr = arr;
    this.low = low;
    this.high = high;
    this.numberOfThreadLevels = numberOfThreads;
  }
}
