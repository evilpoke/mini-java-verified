

public class StringulinaNewTest {
  /*
   * Tests für Teilaufgabe 1
   */

  // 0.5P
  
	public void assertEquals(int a, int b) {
		if (a==b) {
			System.out.println("Equal; Succss");
		}
		else {
			System.out.println("Equal; Fails");
		}
	}
	
	public void assertTrue(boolean a) {
		if(a) {
			System.out.println("True Succss");
		} else {
			System.out.println("True Fails");
		}
	}
	public void assertFalse(boolean a) {
		if(!a){
			System.out.println("false Succss");
		} else {
			System.out.println("false Fails");
		}
	}
	
	public static void main(String[] args) {
		StringulinaNewTest d = new StringulinaNewTest();
		d.testcorrectlyBracketedComplex();
		d.testcorrectlyBracketedMultipleTypes();
		d.testcorrectlyBracketedNoBrackets();
		d.testcorrectlyBracketedSimple();
		d.testCountSubstringOne();
		d.testSubstringPosStandard();
		d.testSubstringPosNotFoundEmpty();
		d.testSubstringPosNotFound();
		d.testSubstringPosLonger();
		d.testMatchesZero();
		d.testMatchesTwo();
		d.testMatchesSimple();
		d.testMatchesMultiOne();
		d.testMatchesMultiDoubleDigitTrue();
		d.testMatchesMultiDoubleDigitFalse();
		d.testMatchesMultiDoubleDigitDotFalse();
		d.testMatchesMissingStart();
		d.testMatchesMissingEnd();
		d.testMatchesLongTrue();
		d.testMatchesLongFalse2();
		d.testMatchesLongFalse1();
		d.testMatchesEqual();
		d.testMatchesDotNoMatch();
		d.testMatchesDot();
		d.testCountSubstringZero();
		d.testCountSubstringTwo();
		d.testCountSubstringSame();
		d.testCountSubstringEmpty();
		d.testCountSubstringSame();
	
		
	}
	
	
  public void testSubstringPosStandard() {
    assertEquals(0, Stringulina.substringPos("halloduda", "halloduda"));
    assertEquals(2, Stringulina.substringPos("halloduda", "llo"));
    assertEquals(2, Stringulina.substringPos("hallodudahalloduda", "llo"));
  }

  // 0.2P
  
  public void testSubstringPosNotFound() {
    assertEquals(-1, Stringulina.substringPos("halloduda", "alli"));
  }

  // 0.2P
  
  public void testSubstringPosNotFoundEmpty() {
    assertEquals(-1, Stringulina.substringPos("", "alli"));
  }

  // 0.1P
  
  public void testSubstringPosLonger() {
    assertEquals(-1, Stringulina.substringPos("halloduda", "hallodudax"));
  }

  /*
   * Tests für Teilaufgabe 2
   */

  // 0.2P
  
  public void testCountSubstringZero() {
    assertEquals(0, Stringulina.countSubstring("halloduhalloda", "llox"));
  }

  // 0.2P
  
  public void testCountSubstringOne() {
    assertEquals(1, Stringulina.countSubstring("halloduhalloda", "du"));
  }

  // 0.2P
  
  public void testCountSubstringTwo() {
    assertEquals(2, Stringulina.countSubstring("halloduhalloda", "hall"));
  }

  // 0.2P
  
  public void testCountSubstringEmpty() {
    assertEquals(0, Stringulina.countSubstring("", "hall"));
  }
  
  // 0.2P
  
  public void testCountSubstringSame() {
    assertEquals(3, Stringulina.countSubstring("bxxxxxz", "xxx"));
  }

  /*
   * Tests für Teilaufgabe 3
   */

  // 0.5P
  
  public void testcorrectlyBracketedSimple() {
    assertTrue(Stringulina.correctlyBracketed("hall()"));
    assertTrue(Stringulina.correctlyBracketed("hall(x)"));
    assertFalse(Stringulina.correctlyBracketed("hall(x"));
    assertFalse(Stringulina.correctlyBracketed("hallx)"));
    assertFalse(Stringulina.correctlyBracketed("(hallx"));
  }

  // 0.5P
  
  public void testcorrectlyBracketedMultipleTypes() {
    assertTrue(Stringulina.correctlyBracketed("{hal]l()"));
    assertTrue(Stringulina.correctlyBracketed("ha[]ll(x)"));
    assertFalse(Stringulina.correctlyBracketed("{hall(x}"));
    assertFalse(Stringulina.correctlyBracketed("ha{l]lx)"));
    assertFalse(Stringulina.correctlyBracketed("(h[[[allx"));
  }

  // 0.5P
  
  public void testcorrectlyBracketedComplex() {
    assertTrue(Stringulina.correctlyBracketed("{(hal]l((())()))["));
    assertTrue(Stringulina.correctlyBracketed("{()()(hal]l((()abc)(x)()))[()"));
    assertFalse(Stringulina.correctlyBracketed("{()()(hal]l((()))))[()"));
    assertFalse(Stringulina.correctlyBracketed("{()()()hal]l((())()))[()"));
    assertFalse(Stringulina.correctlyBracketed("{()()(hal]l((())()))[("));
    assertFalse(Stringulina.correctlyBracketed("{)()()(hal]l((())()))[()"));
  }

  // 0.5P
  
  public void testcorrectlyBracketedNoBrackets() {
    assertTrue(Stringulina.correctlyBracketed("Pinguin"));
    assertTrue(Stringulina.correctlyBracketed(""));
  }

  /*
   * Tests für Teilaufgabe 4
   */

  // 0.25P
  
  public void testMatchesEqual() {
    assertTrue(Stringulina.matches("Foo", "Foo"));
  }

  // 0.5P
  
  public void testMatchesSimple() {
    assertTrue(Stringulina.matches("Foo", "Fo{2}"));
  }

  // 0.25P für Gruppe 1 {
  
  public void testMatchesDot() {
    assertTrue(Stringulina.matches("Foo", "F.o"));
  }

  
  public void testMatchesDotNoMatch() {
    assertFalse(Stringulina.matches("Foo", "F..o"));
  }
  // } (Ende Gruppe 1)

  // 2P für Gruppe 2, 0.25 Abzug pro Fehlschlag {
  
  public void testMatchesTwo() {
    assertTrue(Stringulina.matches("FoooBaaaaar", "Fo{3}Baa{4}r"));
  }

  
  public void testMatchesMissingEnd() {
    assertFalse(Stringulina.matches("FoooBaaaaar", "Fo{3}Baa{4}"));
  }

  
  public void testMatchesMissingStart() {
    assertFalse(Stringulina.matches("FoooBaaaaar", "o{3}Baa{4}r"));
  }

  
  public void testMatchesMultiOne() {
    assertTrue(Stringulina.matches("FoooBaaaaar", "F{1}o{3}Baa{4}r"));
  }

  
  public void testMatchesMultiDoubleDigitTrue() {
    assertTrue(Stringulina.matches("FoooBaaaaaaaaaaar", "F{1}o{3}Baa{10}r"));
  }

  
  public void testMatchesMultiDoubleDigitFalse() {
    assertFalse(Stringulina.matches("FoooBaaaaaaaaaaaar", "F{1}o{3}Baa{10}r"));
  }

  
  public void testMatchesMultiDoubleDigitDot() {
    assertTrue(Stringulina.matches("FoooBaaaaaaaaaaar", "F{1}.{3}B.a{10}r"));
  }

  
  public void testMatchesMultiDoubleDigitDotFalse() {
    assertFalse(Stringulina.matches("FoooBaaaaaaaaaaar", "F{1}.{2}B.a{10}r"));
  }
  // } (Ende Gruppe 2)

  // 1P für Gruppe 3, alles oder nichts {
  
  public void testMatchesLongTrue() {
    int n = 124356;
    String pattern = "F{" + n + "}.{" + (2 * n) + "}r";
    StringBuilder inputBuilder = new StringBuilder();
    for (int i = 0; i < n; i++)
      inputBuilder.append("F");
    String baz = "PinguineSindSoSuperSuess";
    for (int i = 0; i < 2 * n; i++)
      inputBuilder.append(baz.charAt(i % baz.length()));
    inputBuilder.append('r');
    assertTrue(Stringulina.matches(inputBuilder.toString(), pattern));
  }

  
  public void testMatchesLongFalse1() {
    int n = 124356;
    String pattern = "F{" + n + "}.{" + (2 * n + 1) + "}r";
    StringBuilder inputBuilder = new StringBuilder();
    for (int i = 0; i < n; i++)
      inputBuilder.append("F");
    String baz = "PinguineSindSoSuperSuess";
    for (int i = 0; i < 2 * n; i++)
      inputBuilder.append(baz.charAt(i % baz.length()));
    inputBuilder.append('r');
    assertFalse(Stringulina.matches(inputBuilder.toString(), pattern));
  }

  
  public void testMatchesLongFalse2() {
    int n = 124356;
    String pattern = "F{" + (2 * n) + "}.{" + (2 * n) + "}r";
    StringBuilder inputBuilder = new StringBuilder();
    for (int i = 0; i < n; i++)
      inputBuilder.append("F");
    String baz = "PinguineSindSoSuperSuess";
    for (int i = 0; i < 2 * n; i++)
      inputBuilder.append(baz.charAt(i % baz.length()));
    inputBuilder.append('r');
    assertFalse(Stringulina.matches(inputBuilder.toString(), pattern));
  }
  // } (Ende Gruppe 3)

  // 0.5P Abzug, falls dieser Test nicht durchläuft
  
  public void testMatchesZero() {
    assertTrue(Stringulina.matches("Pinguin", "X{0}y{0}P.{1}nge{0}ui{1}.w{0}"));
    assertFalse(Stringulina.matches("Pingeuin", "X{0}y{0}P.{1}nge{0}ui{1}.w{0}"));
  }
}

