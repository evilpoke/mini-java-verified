///
public class MatricesStudTest {
 
	public static void main(String[] args) {
		double[] dtestvec1 = {1.0, 5.0, 9.9, 25.35, 30};
		double[] dtestvec2 = {5.0, 7.0, 2.7, 13.0, 5};
		int[] itestvec1 = {5,8,-4};
		int[] itestvec12 = {6,9,-5};
		int[] itestvec13 = {4,7,-2};
		int[] itestvec2 = {5,7,2,13,5};
		int[][] itestmatr = new int[3][3];
		
		System.out.println(MatrixVectorOperations.euclideanDistance(dtestvec1, dtestvec2));
	
			itestmatr[0] = itestvec1;
			itestmatr[1] = itestvec12;
			itestmatr[2] = itestvec13;
		
		double[][] dtestmatr = new double[5][5];
		for(int i = 0; i < dtestvec1.length; i++) {
			dtestmatr[i] = dtestvec2;
		}
		String p = "";
		int[][] permu = MatrixVectorOperations.permutations(11);
		for(int i = 0; i < permu.length; i++) {
			for(int d = 0; d < permu[0].length; d++) {
				p+= permu[i][d]+ " ";
			}
			p+= "\n";
		}
		System.out.println(p);
		
		System.out.println(MatrixVectorOperations.sgn(itestvec2));
		
		
		//MatrixVectorOperations.permutations(5);
		int det = MatrixVectorOperations.determinant(itestmatr);
		System.out.println(det);
		System.out.println(MatrixVectorOperations.cosineSimilarity(dtestvec1, dtestvec2));
		System.out.println(MatrixVectorOperations.dotProduct(dtestvec1, dtestvec2));
		double[] res = MatrixVectorOperations.multiply(dtestmatr, dtestvec1); 
		for(double item : res) {
			System.out.println(item);
		}
		String outp = "";
		for(int i = 0; i < dtestmatr[0].length; i++) {
			for(int d = 0; d < dtestmatr.length; d++) {
				outp += dtestmatr[i][d]+ " ";
			}
			outp += "\n";
		}
		System.out.println(outp);
		outp = "";
		dtestmatr = MatrixVectorOperations.transpose(dtestmatr);
		for(int i = 0; i < dtestmatr[0].length; i++) {
			for(int d = 0; d < dtestmatr.length; d++) {
				outp += dtestmatr[i][d]+ " ";
			}
			outp += "\n";
		}
		System.out.println(outp);
		
		
		
		
	}
	
}
