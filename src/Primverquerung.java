
public class Primverquerung {
	
	/**
	 * Gibt die Wurzel oder die nächst kleinere Zahl der Wurzel als Integer zurück.
	 * Die Suche nach der Wurzel ist mit einer Binär-Suche implementiert
	 * @param n
	 * @return
	 */
	public static int squareRoot(int n) {
		int topEnd = n;
		int lowEnd = 2;
		int mid = (topEnd+lowEnd)/2;
		int test = mid*mid;
		while(lowEnd <= topEnd) {
			if(test > n) {
				topEnd = mid -1;
			} else {
				lowEnd = mid +1;
			} 
			mid = (topEnd + lowEnd)/2;
			test = mid*mid;
		}
		return mid;
	}
	
	/**
	 * Das Primzahlensieb nach Eratosthenes.
	 * @param n
	 * @return
	 */
	public static int[] sieveOfEratosthenes(int n) {
		boolean[] numberbody = new boolean[n];
		int[] primelist;
		int nonprimes = 0;
		//Setze alle Zahlen auf "true" ~ Möglicherweise Prim.
		//numberbody[2] entspricht übersichtshalber 2
		for(int i = 2; i< n; i++) numberbody[i]=true;
		//Siebe die Zahlen
		for(int i =2; i <= squareRoot(n); i++) {
			 int d = i+i;
			 while(d < n) {
				 if(numberbody[d]) {
				 numberbody[d]= false;
				 nonprimes++; 
				 }
			     d += i;
			 }
		}
		//Filtere alle Nicht-Primzahlen
		primelist = new int[(n- nonprimes) -2];
		int k = 0;
		for(int i = 2; i < n; i++) {
			if(numberbody[i]) {
				primelist[k++] = i;
			}
		}	
		return primelist;
	}
	/**
	 * Gibt die Summe der Primzahlen p <n wieder, deren Quersumme gerade ist.
	 * @param n
	 * @return
	 */
	public static int querPrim(int n) {
	
		if(n<=0) {
			return 0;
		}
		int[] primes = sieveOfEratosthenes(n);
		int sum = 0;
		for(int number: primes) {
			int localsum = 0;
			for(char character : (""+number).toCharArray()) {localsum+= Character.getNumericValue(character); }
			if(localsum%2 ==0) {
				sum += number;
			}
		}
		return sum;
	}

	public static void main(String[] args) {
		System.out.println( querPrim(11));
	}
}
