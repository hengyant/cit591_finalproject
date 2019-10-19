import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author HengyanTao
 *
 */
public class getDictwords {
	public getDictwords() {
		
	}
	
	public ArrayList<String> getDictwords(String filename){
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
		/*
				for(String item:dictWords) {
					System.out.println(item);
				}
		 */
		return dictWords;
	}
}
