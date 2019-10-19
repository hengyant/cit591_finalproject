import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author HengyanTao
 *
 */
public class SpellChecker {
	public SpellChecker() {

	}

	public void play() {
		// for this assignment we just hard code the engDictionary.txt
		// actually for extensibility, we can ask user for the dictionary input file
		WordRecommender myWordRecommender = new WordRecommender("engDictionary.txt");
		SpellCheckerHelpers mySpellCheckerHelpers = new SpellCheckerHelpers();
		getDictwords mygetDictwords = new getDictwords();
		ArrayList<String> ori_listOfWords = new ArrayList<String>();

		// generate the ArrayList of all the words in engDictionary
		ArrayList<String> dictWords = new ArrayList<String>();
		dictWords = mygetDictwords.getDictwords("engDictionary.txt");


		// for the to be checked file, get all the words to array list
		// for this assignment we just hard code the test.txt
		// actually for extensibility, we can ask user which file user want to do spellcheck
		Scanner s = null;
		File inputFile;
		Scanner userScanner = new Scanner(System.in);

		System.out.println("Please enter the name of a file to spell check: ");
		inputFile = new File(userScanner.nextLine());
		while(!inputFile.exists()) {
			System.out.println("File not found. Please input a file, like test.txt. use the txt file under HengyanTao_SpellChecker directory");
			System.out.println("Or put the .txt file you want to do spell check under HengyanTao_SpellChecker directory");
			inputFile = new File(userScanner.nextLine());
		}
		try {
			Scanner fileScanner = new Scanner(inputFile);
			while(fileScanner.hasNext()) {
				String word = fileScanner.next();
				ori_listOfWords.add(word);
			}

			// now lets go through the ArrayList for each word and find out if there's
			// any mispelled word
			ArrayList<String> misspelledWords = new ArrayList<String>();
			ArrayList<String> suggestedWords = new ArrayList<String>();

			for(int i = 0; i < ori_listOfWords.size(); i++) {
				String item = ori_listOfWords.get(i);
				// if word not in engDictionary then it's mis-spelled
				if(!dictWords.contains(item) && item.length() > 1) {
					//System.out.println(item + "misspelled\n");
					misspelledWords.add(item);
					//
					// If a word from the file does not exist in the provided list, 
					// then it is assumed to be misspelled and a set of alternatives provided to the user
					System.out.println("\n The word " + item + " is misspelled.");
					// compute the available recommended words
					suggestedWords = myWordRecommender.getWordSuggestions(item, 2, 0.5, 10);

					Scanner user_input = new Scanner(System.in);
					String userInput;
					String userTyped_word;
					if(suggestedWords.size() > 0) {
						//for(int j = 0; j < suggestedWords.size(); j++) {
						//	System.out.println(suggestedWords.get(j));
						//}
						System.out.println("The following suggestions are available");
						//myWordRecommender.prettyPrint(suggestedWords);
						mySpellCheckerHelpers.prettyPrint(suggestedWords);

						System.out.println("Press ‘r’ for replace, ‘a’ for accept as is, ‘t’ for type in manually. \n");
						// get user input
						userInput = user_input.nextLine();
						//System.out.println("attention: user just inputted " + userInput);
						//System.out.println("user input r is " + userInput.equals("r"));
						while(!userInput.equals("r") && !userInput.equals("t") && !userInput.equals("a")) {
							System.out.println("invalid input");
							System.out.println("Press ‘r’ for replace, ‘a’ for accept as is, ‘t’ for type in manually.");
							//userInput = user_input.nextLine();
							userInput = user_input.next();
						}

						//System.out.println("user just inputed " + userInput);

						if(userInput.equals("r")) {
							// handle replace
							System.out.println("Your word will now be replaced with one of the suggestions\n");
							//myWordRecommender.prettyPrint(suggestedWords);
							System.out.println("Enter the number corresponding to the word that you want to use for replacement.\n");

							while(!user_input.hasNextInt()) {
								user_input.next();
								System.out.println("invalid input!please Enter the number corresponding to the word that you want to use for replacement.\n");
							}

							int userOption = user_input.nextInt();
							//System.out.print("user input is " + userOption);
							// hengyan debug, handle user input here, should be int from 1 to suggestedWords.size();
							// user input some string *** need to handle it
							while(userOption < 1 || userOption > suggestedWords.size()) {
								System.out.println("please enter an integer number from 1 to " + suggestedWords.size() + "\n");
								while(!user_input.hasNextInt()) {
									user_input.next();
									System.out.println("invalid input!" + "please enter an integer number from 1 to " + suggestedWords.size() + "\n");
								}
								userOption = user_input.nextInt();
							}
							// depends on user option, choose the word to replace
							item = suggestedWords.get(userOption-1);
							ori_listOfWords.set(i, item);
							//System.out.println("after replace item is " + item);
							//System.out.println("after replace ori_listOfWords is " + ori_listOfWords.get(i));
						} else if(userInput.equals("t")) {
							// handle user type
							System.out.println("Please type the word that will be used as the replacement in the output file.");
							userTyped_word = user_input.next();
							// replace the word with userType
							//System.out.println("userType is " + userType);
							item = userTyped_word;
							ori_listOfWords.set(i, item);
							//System.out.println("after user type ori_listOfWords is " + ori_listOfWords.get(i));
						} else if(userInput.equals("a")) {
							// handle a leave it as is
							// accept as is, do nothing
						}
					} else {
						System.out.println("There are 0 suggestions in our dictionary for this word.");
						System.out.println("Press ‘a’ for accept as is, ‘t’ for type in manually.");
						Scanner in = new Scanner(System.in);
						userInput = in.nextLine();
						//System.out.println("user input is " + userInput);
						while(!userInput.equals("t") && !userInput.equals("a")) {
							System.out.println("invalid input");
							System.out.println("Press ‘a’ for accept as is, ‘t’ for type in manually.");
							userInput = user_input.nextLine();
						}

						if(userInput.equals("a")) {
							// accept as is, do nothing
						} else if(userInput.equals("t")) {
							// get user type and replace with user's input
							System.out.println("Please type the word that will be used as the replacement in the output file.");
							userTyped_word = in.next();
							// replace the word with userType
							//System.out.println("userType is " + userType);
							item = userTyped_word;
							ori_listOfWords.set(i, item);
							//System.out.println("after user type ori_listOfWords is " + ori_listOfWords.get(i));
						}
					}
				}
			}

			// this is to test the Arraylist has all the words from the test.txt after spell fix
			//for(String item:ori_listOfWords) {
			//	System.out.println(item);
			//}
			System.out.println("All misspelled words have been handled!");
			System.out.println("We're going to write a new file with the name of the original file plus the suffix ‘_chk’!");
			//String filename = inputFile.getName().concat("_chk");
			String filename = inputFile.getName();
			int lastDot = filename.lastIndexOf('.');
			String new_filename = filename.substring(0,lastDot) + "_chk" + filename.substring(lastDot);
			try {
				FileWriter fw = new FileWriter(new_filename);

				//System.out.println("What text should we put in the file?");
				// fileContents here is the ArrayList of the spell checked original file
				for(String item:ori_listOfWords) {
					fw.write(item + " ");
				}
				fw.close();
				System.out.println("File was successfully written!");
				System.out.println("Thanks for attending Spell Checker! All done now! Great Job!");
			}
			catch(IOException e) {
				// Do nothing
			}
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
}
