
public class WordCountsStudTest {

	
	public static void main(String[] args) {
		String testoutput = "";
		
		
		WordCountsArray subject = new WordCountsArray(5);
		WordCountsArray othersubject = new WordCountsArray(5);
		subject.add("wendigo", 5);
		subject.add("blaa", 7);
		subject.add("blac", 1);
		subject.add("blab", 2);
		testoutput += subject.size()+"\n";
		subject.add("wendigo", 2);
		testoutput += subject.size() +"\n"; 
		testoutput += subject.getIndexOfWord("bla2")+ "\n";
		testoutput += subject.getWord(2) + "\n";
		testoutput += subject.getCount(2) + "\n";
		subject.setCount(2, 5);
		testoutput += subject.getCount(2)+"\n";
		subject.sort();
		for(int i = 0; i < subject.size(); i++) {
			testoutput += subject.getWord(i)+ ", ";
			othersubject.add(subject.getWord(i), subject.getCount(i));
		}
		testoutput += "\n"+othersubject.equals(subject) +"\n";
		testoutput += othersubject.computeSimilarity(subject) +"\n";
		othersubject.setCount(1, 1);
		testoutput += othersubject.equals(subject) + "\n";
		testoutput += othersubject.computeSimilarity(subject) +"\n";
					
		
		
		
		
		
		System.out.println(testoutput);
		
		
	}	
	
}
