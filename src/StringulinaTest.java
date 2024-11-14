
public class StringulinaTest {
	
	public static void main(String[] args) {
		
		
		System.out.println(Stringulina.substringPos("einpferdschlaefthoch", "pferd"));  
		String test = "StefanTestdieTestkdieTesTestdkTedkTestdke";
		System.out.println(Stringulina.countSubstring(test,"Tes"));
		String test2 = "sldfk(sl)sdf(((sdfsdf ())))";
		System.out.println(Stringulina.correctlyBracketed(test2));
		System.out.println(Stringulina.matches("abcccee","ab.{3}.e"));
		
	}
	

}
