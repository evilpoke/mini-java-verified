
public class Stringulina {
  private static int substringPos(String haystack, String needle, int from) {
    // Ausgehend von jedem Buchstaben...
    outer: for (int i = from; i + needle.length() - 1 < haystack.length(); i++) {
      // ... prüfen wir, ob wir needle finden.
      for (int j = 0; j < needle.length(); j++)
        if (needle.charAt(j) != haystack.charAt(i + j))
          continue outer;
      return i;
    }
    return -1;
  }

  public static String substring(String input, int startindex, int endindex) {
		char[] chararray = new char[endindex-startindex+1];
		for(int i = startindex; i<=endindex; i++) chararray[i-startindex] = input.charAt(i);
		String back = "";
		for(char item : chararray) {
			back += item;
		}
		return back;
	}
  
  public static int substringPos(String haystack, String needle) {
    return substringPos(haystack, needle, 0);
  }

  public static int countSubstring(String haystack, String needle) {
    int sum = -1;
    int from = -1;
    do {
      // Ab jeder Position nach needle suchen, jeweils ab dort
      // fortfahren
      from = substringPos(haystack, needle, from + 1);
      sum++;
    } while (from != -1);
    return sum;
  }

  public static boolean correctlyBracketed(String str) {
    int bracketCount = 0;

    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == '(')
        bracketCount++;
      else if (str.charAt(i) == ')') {
        bracketCount--;
        // Haben wir irgendwann mehr öffnende als schließende
        // Klammern gesehen, ist der String nicht korrekt
        // geklammert.
        if (bracketCount < 0)
          return false;
      }
    }

    return bracketCount == 0;
  }

  public static boolean matches(String str, String pattern) {
    int posStr = 0;
    int posPat = 0;
    while (posPat < pattern.length()) {
      char nextPat = pattern.charAt(posPat++);
      int multiplicity = 1;
      // Bei öffnender Klammer...
      if (posPat < pattern.length() && pattern.charAt(posPat) == '{') {
        // ... extrahieren wir die Vielfachheit
        String multi_str = "";
        for (int i = posPat + 1; pattern.charAt(i) != '}'; i++)
          multi_str += pattern.charAt(i);
        multiplicity = Integer.parseInt(multi_str);
        // Wir überspringen die Vielfachheits-Angabe im Pattern
        posPat += 2 + multi_str.length();
      }
      // Wir prüfen den Buchstaben aus dem Pattern und seine Vielfachheit auf
      // Vorhandenheit im String
      for (int i = 0; i < multiplicity; i++)
        if (posStr >= str.length() || (str.charAt(posStr++) != nextPat && nextPat != '.'))
          return false;
    }

    return posStr == str.length();
  }
}
