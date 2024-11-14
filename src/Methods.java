
public class Methods {

	public static void main(String[] args){
		
	}



	public static void print(int[] array) {
		String back = "{";
		for(int b : array) {
			back += ", "+b;
		}
		back+= "}";
		System.out.println(back);
	}
	
	public static int[] invert(int[] array){
		int[] back = new int[array.length];
		for(int i = 0; i < back.length; i++) {
			back[i] = array[back.length-1 -i];
		}
		return back;
	}
	public static int[] cut(int[] array, int length) {
		int[] back = new int[length];
		if(length < array.length) {
			for(int i = 0; i < back.length; i++) {
				back[i] = array[i];
			}
		} else {
			for(int i = 0; i < back.length; i++) {
				if(i >= array.length) {
					back[i] = 0;
				} else {
					back[i] = array[i];
				}
			}
		}
		return back;
	}
	
/*	public static int[] linearize(int[][] array) {	//WHATEVER
		//int
		String store = "";
		for(int i = 0; i < array.length; i++) {
			for(int d = 0; d < array[i].length; d++) {
			int[] temp = new int[array[i].length];
			}
		}
		return null;
		
		
		
	}*/
	
	
}


