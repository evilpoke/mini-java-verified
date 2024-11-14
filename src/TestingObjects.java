
public class TestingObjects {

	int x;
	int y;
	String[] a;
	TestingObjects[] whatever;
	TestingObjects(int x, int y){
		this.x = x;
		this.y = y;
	}
	public void maketest(String[] ab) {
		ab[1] = "test";
		this.a = ab;
		
	}
	
	public String[] gettest() {
		return this.a;
	}
	
	public static void main(String[] args) {
		TestingObjects obj = new TestingObjects(1,2);
		String[] bla = new String[2];
		bla[0] = "first";
		obj.maketest(bla);
		String[] secondbla = bla;
		//System.out.println(obj.gettest());
		//System.out.println(bla);
		//System.out.println(secondbla);
		String a = "asdf";
		String b = a;
		b = b+"ff";
		System.out.println(a);
		System.out.println(b);
		Integer d = new Integer(42);
		
	}
	
}

