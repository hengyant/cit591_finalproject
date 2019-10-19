import java.util.ArrayList;

/**
 * 
 * @author HengyanTao
 *
 */
public class SpellCheckerHelpers {
	public SpellCheckerHelpers() {

	}

	public String prettyPrint(ArrayList<String> list) {
		String retString = null;
		int i = 1;
		for(String item : list) {
			System.out.println(i + ". " + item);
			retString += Integer.toString(i) + ". " + item + "\n";
			i++;
		}

		return retString;
	}
	
	public double getSimilarityMetric(String word1, String word2) {
		// given two words, this function computes two measures of similarity
		// and returns the average
		int l1 = word1.length();
		int l2 = word2.length();
		int lmin = Math.min(l1, l2);
		int leftSimilarity = 0;
		int rightSimilarity = 0;

		// calculate leftSimilarity
		for(int i = 0; i < lmin; i++) {
			if(word1.charAt(i) == word2.charAt(i)) {
				leftSimilarity += 1;
			}
		}

		// calculate rightSimilarity
		for(int j = 0; j < lmin; j++) {
			if(word1.charAt(l1-j-1) == word2.charAt(l2-j-1)) {
				rightSimilarity += 1;
			}
		}
		return (leftSimilarity + rightSimilarity)/2.0;
	}
	
	public double getcommonPercent(String word1, String word2){
		// Numerator = letters in common
		// Denominator = all the letters
		// Common percent = Numerator/Denominator
		int Numerator = 0;
		double Denominator = 0.0;
		double commonPercent = 0.0;

		ArrayList<Character> myNset = new ArrayList<Character>();
		myNset = getNumeratorset(word1, word2);
		Numerator = myNset.size();

		ArrayList<Character> myDset = new ArrayList<Character>();
		myDset = getDenominatorset(word1, word2);
		Denominator = myDset.size();

		// calculate Numerator and Denominator
		commonPercent = Numerator/Denominator;

		return commonPercent;
	}
	
	public static ArrayList<Character> getNumeratorset(String word1, String word2) {
		ArrayList<Character> myNumeratorset = new ArrayList<Character>();
		ArrayList<Character> myS1set = new ArrayList<Character>();
		ArrayList<Character> myS2set = new ArrayList<Character>();

		for(int i = 0; i < word1.length(); i++) {
			if(!myS1set.contains(word1.charAt(i))) {
				myS1set.add(word1.charAt(i));
			}
		}

		for(int i = 0; i < word2.length(); i++) {
			if(!myS2set.contains(word2.charAt(i))) {
				myS2set.add(word2.charAt(i));
			}
		}

		//myNumeratorset = myS1set;

		if(myS1set.size() < myS2set.size()) {
			for(Character item:myS1set) {
				if(myS2set.contains(item)) {
					//System.out.println(item);
					myNumeratorset.add(item);
				}
			}
		} else {
			for(Character item:myS2set) {
				if(myS1set.contains(item)) {
					//System.out.println(item);
					myNumeratorset.add(item);
				}
			}
		}
		return myNumeratorset;
	}

	public static ArrayList<Character> getDenominatorset(String word1, String word2) {
		ArrayList<Character> myDenominatorset = new ArrayList<Character>();
		ArrayList<Character> myS1set = new ArrayList<Character>();
		ArrayList<Character> myS2set = new ArrayList<Character>();

		for(int i = 0; i < word1.length(); i++) {
			if(!myS1set.contains(word1.charAt(i))) {
				myS1set.add(word1.charAt(i));
			}
		}

		for(int i = 0; i < word2.length(); i++) {
			if(!myS2set.contains(word2.charAt(i))) {
				myS2set.add(word2.charAt(i));
			}
		}

		// Denominatorset have all the letters in word1 and word2 without repeat
		//myDenominatorset = myS1set;
		if(myS1set.size() < myS2set.size()) {
			myDenominatorset = myS2set;
			for(Character item:myS1set) {
				if(!myS2set.contains(item)) {
					myDenominatorset.add(item);
				}
			}
		} else {
			myDenominatorset = myS1set;
			for(Character item:myS2set) {
				if(!myS1set.contains(item)) {
					myDenominatorset.add(item);
				}
			}
		}
		return myDenominatorset;
	}
}
