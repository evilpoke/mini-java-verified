public class RecursiveDeterminant {

  public static void main(String[] args){
    {
      String msg = "Determinante falsch berechnet.";
      int[][] matrix;
      int expectedResult;
      int actualResult;

      matrix = new int[][] { { -1, -1 }, { -1, -1 } };
      expectedResult = 0;
      actualResult = RecursiveDeterminant.detNxN(matrix);
      assertEquals(msg, expectedResult, actualResult);

      matrix = new int[][] { { 1, 1 }, { 1, 1 } };
      expectedResult = 0;
      actualResult = RecursiveDeterminant.detNxN(matrix);
      assertEquals(msg, expectedResult, actualResult);

      matrix = new int[][] { { 3, 7, 9 }, { 1, -3, 5 }, { -8, 0, -2 } };
      expectedResult = -464;
      actualResult = RecursiveDeterminant.detNxN(matrix);
      assertEquals(msg, expectedResult, actualResult);

      matrix = new int[][] { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };
      expectedResult = 0;
      actualResult = RecursiveDeterminant.detNxN(matrix);
      assertEquals(msg, expectedResult, actualResult);

      matrix = new int[][] { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 } };
      expectedResult = 0;
      actualResult = RecursiveDeterminant.detNxN(matrix);
      assertEquals(msg, expectedResult, actualResult);

      matrix = new int[][] { { 0, 1, 2, 3 }, { 4, 5, 6, 7 }, { 8, 9, 10, 11 }, { 12, 13, 14, 15 } };
      expectedResult = 0;
      actualResult = RecursiveDeterminant.detNxN(matrix);
      assertEquals(msg, expectedResult, actualResult);

      matrix = new int[][] { { -1, 1, 2, 3 }, { 4, 5, 6, 7 }, { 8, 9, 10, 11 }, { 12, 13, 14, 15 } };
      expectedResult = 0;
      actualResult = RecursiveDeterminant.detNxN(matrix);
      assertEquals(msg, expectedResult, actualResult);

      matrix = new int[][] { { -1, 1, 2, 3 }, { 4, -5, 6, 7 }, { 8, 9, 10, 11 }, { 12, 13, 99, 15 } };
      expectedResult = -30470;
      actualResult = RecursiveDeterminant.detNxN(matrix);
      assertEquals(msg, expectedResult, actualResult);
    }
  }

  public static void assertEquals(String a, int b, int c){
    if(b!= c){
    System.out.println(a);}

  }

  public static int det2x2(int[][] matrix){
    return (matrix[0][0]*matrix[1][1]) - (matrix[1][0] * matrix[0][1]);
  }

  /**
   * Calculates det of given matrix
   * @param matrix
   * @return
   */
  public static int det3x3(int[][] matrix){
    int[][] test = removeColumn(matrix,0);
    test = removeRow(matrix,1);

    return
            (matrix[0][0]*det2x2(removeColumn(removeRow(matrix,0),0)) ) -
                    (matrix[1][0]*det2x2(removeColumn(removeRow(matrix,0),1)) ) +
                    (matrix[2][0]*det2x2(removeColumn(removeRow(matrix,0),2)) )

            ;


  }


  /**
   * Calcualtes matrix
   * @param matrix
   * @return
   */
  public static int detNxN(int[][] matrix){
    int[][] newbasematrix ;
    if(matrix.length==2){return det2x2(matrix);}
    newbasematrix = removeRow(matrix,0);
    int det = 0;
    int sign = +1;
    for(int i = 0; i < matrix.length; i++){
      det +=  sign * matrix[i][0]*detNxN(removeColumn(newbasematrix,i));
      sign *= -1;
    }
    return det;
  }


  /**
   * Removes the row specified at rowIndex from the matrix
   * @param matrix
   * @param rowIndex
   * @return
   */
  public static int[][] removeRow(int[][] matrix, int rowIndex){
    int[][] newmatrix = new int[matrix.length][matrix[0].length-1];
    int counter = 0;
    int localcounter = 0;
    while(counter < matrix[0].length){
      if(rowIndex!=counter){
        for (int i = 0; i < matrix.length; i++) {
          newmatrix[i][localcounter] = matrix[i][counter];
        }
        localcounter++;
      }
      counter++;
    }
    return newmatrix;
  }

  /**
   * Removes the column specified at columnindex from the matrix
   * @param matrix
   * @param columnindex
   * @return
   */
  public static int[][] removeColumn(int[][] matrix, int columnindex) {
    int[][] newmatrix = new int[matrix.length - 1][matrix.length];
    int counter = 0;
    int localcounter = 0;
    while (counter < matrix.length) {
      if (columnindex != counter) {
        for (int i = 0; i < matrix[0].length; i++) {
          newmatrix[localcounter][i] = matrix[counter][i];
        }

        localcounter++;
      }
      counter++;
    }
    return newmatrix;

  }


}
