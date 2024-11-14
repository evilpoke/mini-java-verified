
public class MergeSort {

	
	
	//private int[] 
	private int[] swap(int[] arr, int a, int b) {
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
		return arr;
	}
	
	/**
	 * Sortiert ein double array mit merge sort
	 * @param a
	 * @return
	 */
	public static double[][] mergeSortIt(double[][] a) {
		
		int capturesize = 1;
		int cBegin= 0; 
		double[][] writearray = new double[a.length][2];
		double[][] readarray = a;
		int leftcounter, rightcounter;
		leftcounter = rightcounter =0;
		loop1: while(true) {
			while(leftcounter < capturesize && cBegin+capturesize+rightcounter < readarray.length && rightcounter < capturesize) {
				if(readarray[cBegin+leftcounter][1]>readarray[cBegin+capturesize+rightcounter][1]) {
				     writearray[cBegin+leftcounter+rightcounter][1] = readarray[cBegin+leftcounter][1];
				     writearray[cBegin+leftcounter+rightcounter][0] = readarray[cBegin+leftcounter][0];
				     leftcounter++;
				} else {
					writearray[cBegin+leftcounter+rightcounter][0] = readarray[cBegin+capturesize+rightcounter][0];
					writearray[cBegin+leftcounter+rightcounter][1] = readarray[cBegin+capturesize+rightcounter][1];
					rightcounter++;
				}
			}
				
			while(leftcounter < capturesize && cBegin + leftcounter < readarray.length) {
				writearray[cBegin+rightcounter+leftcounter][0] = readarray[cBegin+leftcounter][0];
				writearray[cBegin+rightcounter+leftcounter][1] = readarray[cBegin+leftcounter][1];
				leftcounter++;
			}
			while(cBegin+capturesize+rightcounter < readarray.length && rightcounter < capturesize) {
				writearray[cBegin+leftcounter+rightcounter] = readarray[cBegin+capturesize+rightcounter];
				rightcounter++;
			}
			leftcounter = rightcounter = 0;
			cBegin += 2*capturesize;
			if (cBegin >= readarray.length) { 
				cBegin = 0;
				capturesize *= 2;
				if(capturesize >=readarray.length) break loop1;
				double[][] temp = writearray;
				writearray = readarray;
				readarray = temp;
				continue loop1;
				
			}
			
		}
		return writearray;
		
		
		
	}
	
	
	/**
	 * Sortiert mit merge sort
	 * @param a
	 * @return
	 */
	public static int[] mergeSortIt(int[] a) {
	
		int capturesize = 1;
		int cBegin= 0; 
		int[] writearray = new int[a.length];
		int[] readarray = a;
		int leftcounter, rightcounter;
		leftcounter = rightcounter =0;
		loop1: while(true) {
			while(leftcounter < capturesize && cBegin+capturesize+rightcounter < readarray.length && rightcounter < capturesize) {
				if(readarray[cBegin+leftcounter]<readarray[cBegin+capturesize+rightcounter]) {
				     writearray[cBegin+leftcounter+rightcounter] = readarray[cBegin+leftcounter];
				     leftcounter++;
				} else {
					writearray[cBegin+leftcounter+rightcounter] = readarray[cBegin+capturesize+rightcounter];
					rightcounter++;
				}
			}
				
			while(leftcounter < capturesize && cBegin + leftcounter < readarray.length) {
				writearray[cBegin+rightcounter+leftcounter] = readarray[cBegin+leftcounter];
				leftcounter++;
			}
			while(cBegin+capturesize+rightcounter < readarray.length && rightcounter < capturesize) {
				writearray[cBegin+leftcounter+rightcounter] = readarray[cBegin+capturesize+rightcounter];
				rightcounter++;
			}
			leftcounter = rightcounter = 0;
			cBegin += 2*capturesize;
			if (cBegin >= readarray.length) { 
				cBegin = 0;
				capturesize *= 2;
				if(capturesize >=readarray.length) break loop1;
				int[] temp = writearray;
				writearray = readarray;
				readarray = temp;
				continue loop1;
				
			}
			
		}
		return writearray;
		
		
		
	}
	
	
	public static void main(String[] args) {
		int a = 7/2;
		int[] testing = {10,3,2,5,60,9,9,8,7,6,5,4,3,2,1,5};
		int[] result = mergeSortIt(testing);
		for(int i: result) {
			System.out.println(i+"\n");
		}
		
	}
	
}
