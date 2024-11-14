import java.lang.Math;

public class MatrixVectorOperations {


	
  public static int determinant(int[][] A) {
    int n = A.length;
    int[][] perms = permutations(n);
    int sum = 0;

    for (int i = 0, product = 1; i < perms.length; i++, product = 1) {
      for (int j = 0; j < n; j++)
        product *= A[j][perms[i][j]];
      sum += sgn(perms[i]) * product;
    }

    return sum;
  }

  public static int ggT(int a, int b) {
    if(b > a) {
      int t = a;
      a = b;
      b = t;
    }
    while (b != 0) {
      int t = b;
      b = a % b;
      a = t;
    }
    return a;
  }
  
  private static int abs(int value) {
    if(value < 0)
      value *= -1;
    return value;
  }

  public static int sgn(int[] permutation) {
    int num = 1;
    int denom = 1;
    for (int i = 0; i < permutation.length; i++)
      for (int j = i + 1; j < permutation.length; j++) {
        num *= (permutation[j] - permutation[i]);
        denom *= (j - i);
        int ggt = ggT(abs(num), abs(denom));
        num /= ggt;
        denom /= ggt;
      }
    return num / denom;
  }

  private static int factorial(int n) {
    int res = 1;
    while (n > 1) {
      res *= n;
      n--;
    }
    return res;
  }

  private static void swap(int[] a, int x, int y) {
    int t = a[x];
    a[x] = a[y];
    a[y] = t;
  }

  public static int[][] permutations(int n) {
    /*
     * Idee: Tausche Element an Position x an alle andere Positionen
     * > x, berechne nach jeder Tauschung alle Permutationen der
     * Teilfelder mit Indizes > x.
     */
    int[][] permutations = new int[factorial(n)][n];
    int permCounter = 0;
    // Speichert die aktuelle Permutation
    int[] perm = new int[n];
    // Speichert den Tausch-Index
    int[] cnt = new int[n - 1];
    for (int i = 0; i < n - 1; i++) {
      perm[i] = n - i - 1;
      // Initial tauschen wir jeweils den Index i mit sich selbst
      cnt[i] = i;
    }
    // 'pos' ist die aktuelle Position im Permutationen-Feld
    //
    // Beginnend ab Position 0 im Array werden zunächst jeweils Tauschungen
    // mit des Indexes i mit sich selbst durchgeführt. Da dies keinen Effekt
    // hat, fangen wir mit Position n - 2 an.
    int pos = n - 2;
    System.arraycopy(perm, 0, permutations[permCounter++], 0, n);
    // Müssten wir eine Vertauschung am Index -1 durchführen, wissen wir, dass
    // wir fertig sind.
    while (pos >= 0) {
      // Kommen wir erstmals vorwärts zu einer Position, ...
      if (cnt[pos] >= n) {
        // ... setzen wir ihren Zähler zurück und
        cnt[pos] = pos;
        // berechnen zunächst die Permutationen ab dieser Position
        // ohne Vertauschung.
        if (pos < n - 2)
          pos++;
        continue;
      }
      // Wir machen eine (eventuelle) vorherige Vertauschung rückgängig
      swap(perm, pos, cnt[pos]++);
      // Gibt es noch eine weiter hinten liegende Position, auf die wir
      // tauschen können, ...
      if (cnt[pos] < n) {
        // ... tauschen wir zu dieser,
        swap(perm, pos, cnt[pos]);
        // ... speichern das aktuelle Array als neue Permuatione und ...
        System.arraycopy(perm, 0, permutations[permCounter++], 0, n);
        // Gehen ggf. zur folgenden Position.
        if (pos < n - 2)
          pos++;
      } else
      // Haben wir unseren aktuellen Index 'pos' mit allen höheren Indizes
      // gatauscht, gehen wir rückwärts
        pos--;
    }
    return permutations;
  }

  public static double dotProduct(double[] vector1, double[] vector2) {
    double sum = 0;
    int n = vector1.length;
    for (int i = 0; i < n; i++)
      sum += vector1[i] * vector2[i];
    return sum;
  }

  public static double[] multiply(double[][] matrix, double[] vector) {
    int n = matrix.length;
    double[] resultVector = new double[n];
    for (int i = 0; i < n; i++)
      resultVector[i] = dotProduct(matrix[i], vector);
    return resultVector;
  }

  public static double cosineSimilarity(double[] a, double[] b) {
    return dotProduct(a, b) / Math.sqrt(dotProduct(a, a) * dotProduct(b, b));
  }

  public static double[][] transpose(double[][] A) {
    int n = A.length;
    int m = A[0].length;
    double[][] transpose = new double[m][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < m; j++)
        transpose[j][i] = A[i][j];
    return transpose;
  }

    public static double euclideanDistance(double[] vector1, double[] vector2) {
    int n = vector1.length;
    double sum = 0.0;
    for (int i = 0; i < n; i++)
      sum += Math.pow(vector1[i] - vector2[i], 2);
    return Math.sqrt(sum);
  }
}
