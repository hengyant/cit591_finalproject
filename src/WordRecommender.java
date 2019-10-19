import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author HengyanTao
 *
 */
public class WordRecommender {
	// has a dictionary of words
	// which is engDictionary.txt in this assignment
	String filename;

	public WordRecommender(String fileName) {
		this.filename = fileName;
	}

	// given a misspelled word, return a list of legal word suggestions
	public ArrayList<String> getWordSuggestions(String word, int n, double commonPercent, int topN) {
		SpellCheckerHelpers mySpellCheckerHelpers = new SpellCheckerHelpers();
		getDictwords mygetDictwords = new getDictwords();
		ArrayList<String> recommwordlist = new ArrayList<String>();
		ArrayList<String> possiblewordlist = new ArrayList<String>();
		ArrayList<String> dictWords = new ArrayList<String>();
		double mycommonPercent = 0.0;

		// get all words in dictionary
		//dictWords = getDictwords();
		dictWords = mygetDictwords.getDictwords(filename);

		//System.out.println("the misspelled word is " + word);
		int wlen = word.length();

		// list of candidate words two criteria:
		// 1. candidate word length is ​word​ length +/- ​n​ characters
		// 2. have at least commonPercent% of the letters in common
		for(int i = 0; i < dictWords.size(); i++) {
			//mycommonPercent = getcommonPercent(word, dictWords.get(i));
			mycommonPercent = mySpellCheckerHelpers.getcommonPercent(word, dictWords.get(i));
			if(Math.abs(dictWords.get(i).length() - wlen) <= n && mycommonPercent >= commonPercent) {
				possiblewordlist.add(dictWords.get(i));
			}
		}

		double similarityMax = 0.0;
		double similarityCurrent = 0.0;
		//for(int i = 0; i < possiblewordlist.size(); i++) {
		//	System.out.println("word " + possiblewordlist.get(i) + " similarity is " + getSimilarityMetric(word, possiblewordlist.get(i)));
		//}

		int count = 0;
		String maxSimilarityWord = null;
		int maxSimilarityWord_index = 0;
		while(count <= topN && possiblewordlist.size() > 0) {
			similarityMax = mySpellCheckerHelpers.getSimilarityMetric(word, possiblewordlist.get(0));
			maxSimilarityWord = possiblewordlist.get(0);
			maxSimilarityWord_index = 0;
			for(int i = 1; i < possiblewordlist.size(); i++) {
				similarityCurrent = mySpellCheckerHelpers.getSimilarityMetric(word, possiblewordlist.get(i));
				if(similarityCurrent > similarityMax) {
					similarityMax = similarityCurrent;
					maxSimilarityWord = possiblewordlist.get(i);
					maxSimilarityWord_index = i;
				}
			}
			// after the for loop we found the maxSimilarityWord
			recommwordlist.add(maxSimilarityWord);
			possiblewordlist.remove(maxSimilarityWord_index);
			count++;
		}

		return recommwordlist;
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
	 
	/*
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
	 */
	/*
	public ArrayList<String> getDictwords(){
		// generate the ArrayList of all the words in engDictionary
		ArrayList<String> dictWords = new ArrayList<String>();
		//File dict_f  = new File("engDictionary.txt");
		File dict_f  = new File(filename);
		Scanner dict_s;
		try {
			dict_s = new Scanner(dict_f);
			while(dict_s.hasNext()) {
				String word = dict_s.next();
				dictWords.add(word);
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

				for(String item:dictWords) {
					System.out.println(item);
				}

		return dictWords;
	}*/
	/*
	public static void main(String[] args) {
		// 10/5 afternoon 3:43pm test, all below code works

		WordRecommender myWordRecommender = new WordRecommender("engDictionary.txt");
		ArrayList<String> listOfWords = new ArrayList<String>();
		ArrayList<String> myTestlist;

		listOfWords.add("ban");
		listOfWords.add("bang");
		listOfWords.add("mange");
		listOfWords.add("gang");
		listOfWords.add("cling");
		listOfWords.add("loo");
		System.out.println("Hello World Hengyan!");

		ArrayList<Character> myNset = new ArrayList<Character>();
		ArrayList<Character> myDset = new ArrayList<Character>();
		myNset = Numeratorset("gardener", "nerdier");
		System.out.println("size of myNset is: " + myNset.size());
		myDset = Denominatorset("gardener", "nerdier");
		System.out.println("size of myDset is: " + myDset.size());

		String word = "cloong";
		myTestlist = getWordsWithCommonLetters(word, listOfWords, 3);


}*/

	
	// test only
	public ArrayList<String> getWordsWithCommonLetters(String word, ArrayList<String> listOfWords, int n){
		ArrayList<String> myTestlist = new ArrayList<String>();

		for(String item:listOfWords) {
			if(getCommonletters(word, item) >= n) {
				myTestlist.add(item);
			}
		}
		// check listOfWords to see if any word has n similarity of word
		// if so, add to myTestlist
		for(int i = 0; i < myTestlist.size(); i++) {
			System.out.println(myTestlist.get(i));
		}
		return myTestlist;
	}

	public static int getCommonletters(String word1, String word2) {
		int commonLetter = 0;

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

		for(Character item:myS2set) {
			if(myS1set.contains(item)) {
				//System.out.println(item);
				commonLetter++;
			}
		}

		System.out.println("commonLetter is " + commonLetter);
		return commonLetter;
	}
	 
	/*	public String prettyPrint(ArrayList<String> list) {
		String retString = null;
		int i = 1;
		for(String item : list) {
			System.out.println(i + ". " + item);
			retString += Integer.toString(i) + ". " + item + "\n";
			i++;
		}

		return retString;
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
	 */
}
