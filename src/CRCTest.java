
public class CRCTest {

	public static void main(String[] args) {
	
		

		CRC test = new CRC(100); //Entspricht 1100100 oder 1*x^6+1*x^5+0*x^4+0*x^3+1*x^2+0*x+0
		System.out.println(test.crcASCIIString("na"));
		
		CRC test2 = new CRC(38); //gg Beispiel
		System.out.println(test2.crcASCIIString("az"));
		
		
	}
}
