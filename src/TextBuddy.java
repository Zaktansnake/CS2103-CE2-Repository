/* CS2103 AY1516 CE2
 * Name: David Chong Yong Ming
 * Matric No: A0116633L
 * Tutorial Group: F10
 */

/**
 * TextBuddy is a program that users can use to add, delete, search, display, clear and sort text.
 * Users can perform these functions by typing the commands in the command line
 * These are the proper usage of the commands,
 * To open the program, java TextBuddy filename.txt
 * For addding a line of text, add userinput
 * For deleting a line of text, delete linenumber
 * For searching for a specific word, search userinput
 * For displaying all the text, display
 * For clearing all the text, clear
 * For sorting text according to alphabetical order, sort
 * 
 * The file that stores the text is auto-saved after each action so it is always updated
 * @author David Chong
 */

import java.util.*;
import java.io.*;

public class TextBuddy {

	/**
	 * Stores the name of the file to be called upon when printing statements and for creating file objects.
	 */
	private static String fileName;

	public static void main(String[] args) {
		checkArgument(args);
		fileName = args[0];
		createFile(fileName);
	}

	/**
	 * Checks if arguments are valid.
	 * If valid, program will continue to run. 
	 * If invalid, informs user of proper usage and closes program.
	 * 
	 * @param args	arguments keyed in at the command line.
	 */
	public static void checkArgument(String[] args) {
		if (args.length == 0) {
			System.err.println("Error! Invalid use of TextBuddy! Proper Usage is: Java TextBuddy filename.txt");
			exitProgram();
		}
	}

	/**
	 * Creates the file object for text storage and manipulation with the functions in TextBuddy.
	 * 
	 * @param fileName	The name for the file to be created.
	 * @throws IOException	If file cannot be created
	 */
	public static void createFile(String fileName) {
		File textFile = new File(fileName);

		try {
			textFile.createNewFile();
			commandInterface(textFile);
		} catch (IOException e) {
			System.err.println("Error! File cannot be created!");
		}
	}

	/**
	 * Main interface for TextBuddy, user will input commands in this method.
	 * This method will call on certain methods depending on user commands.
	 * 
	 * @param textFile	The file for text storage/manipulation.
	 */
	public static void commandInterface(File textFile) {
		Scanner scan = new Scanner(System.in);
		String userCommand;
		System.out.println("Welcome to TextBuddy. " + fileName + " is ready for use");

		while (true) {
			System.out.print("command:");
			userCommand = scan.next();

			switch (userCommand) {
				case "add":
					String textToAdd = scan.nextLine();
					// Trims the whitespace keyed in by user
					textToAdd = textToAdd.trim();
					addText(textFile, textToAdd);
					break;

				case "delete":
					String lineToDelete = scan.nextLine();
					// Trims the whitespace keyed in by user
					lineToDelete = lineToDelete.trim();
					deleteLine(textFile, lineToDelete);
					break;

				case "search":
					String wordToSearch = scan.nextLine();
					// Trims the whitespace keyed in by user
					wordToSearch = wordToSearch.trim();
					searchWord(textFile, wordToSearch);
					break;

				case "display":
					displayFile(textFile);
					break;

				case "clear":
					clearFile(textFile);
					break;

				case "sort":
					sortFile(textFile);
					break;

				case "exit":
					scan.close();
					exitProgram();
			}
		}
	}

	/**
	 * Adds the user input text as the last line in the file.
	 * 
	 * @param textFile	The file for text storage/manipulation.
	 * @param textToAdd	The text that is to be added into the file.
	 * @throws IOException	If file cannot be opened
	 */
	public static void addText(File textFile, String textToAdd) {
		try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(textFile, true)))) {
			pw.println(textToAdd);
			System.out.println("added to " + fileName + ": \"" + textToAdd + "\"");
		} catch (IOException e) {
			System.err.println("Error! Invalid argument for add! Proper Usage is: add userinput");
		}
	}

	/**
	 * Deletes the user requested line number from the file by copying all lines except the line to delete 
	 * into a temporary storage and adding the lines in the temporary storage back into the file.
	 * 
	 * @param textFile		The file for text storage/manipulation.
	 * @param lineToDelete	The line number to be deleted in the file.
	 * @throws IOException	If file cannot be opened.
	 * @throws NumberFormatException	If user input is not an valid integer.
	 */
	public static void deleteLine(File textFile, String lineToDelete) {
		ArrayList<String> linesNotToDelete = new ArrayList<String>();
		PrintWriter pw = null;

		try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
			String fileOutput;
			int deleteLineNumber = Integer.parseInt(lineToDelete);
			int lineNumber = 0;

			while ((fileOutput = br.readLine()) != null) {
				lineNumber++;
				if (lineNumber == deleteLineNumber) {
					System.out.println("deleted from " + fileName + ": \"" + fileOutput + "\"");
				} else {
					linesNotToDelete.add(fileOutput);
				}
			}

			pw = new PrintWriter(textFile);

			for (int i = 0; i < linesNotToDelete.size(); i++) {
				pw.println(linesNotToDelete.get(i));
			}
		} catch (Exception e) {
			System.err.println("Error! Invalid argument for delete! Proper Usage is: delete linenumber");
		} finally {
			pw.close();
		}
	}

	/**
	 * Takes each line from the file and checks if the line contains the word to be searched. 
	 * If it does, it will store the line number in a temporary storage.
	 * If it does not contain the word, it will continue to read until the end of the file.
	 * The method will print the line numbers that contained the word to be searched to inform the user.
	 * 
	 * @param textFile		The file for text storage/manipulation.
	 * @param wordToSearch	The word to be searched in the file.
	 * @throws IOException	If file cannot be opened.
	 */
	public static void searchWord(File textFile, String wordToSearch) {
		ArrayList<String> linesFromFile = new ArrayList<String>();
		ArrayList<Integer> linesContainingWord = new ArrayList<Integer>();

		try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
			String fileOutput;

			while ((fileOutput = br.readLine()) != null) {
				linesFromFile.add(fileOutput);
			}

			for (int i = 0; i < linesFromFile.size(); i++) {
				String toCheck = linesFromFile.get(i);
				if (toCheck.contains(wordToSearch)) {
					linesContainingWord.add(i);
				}
			}

			if (linesContainingWord.isEmpty()) {
				System.out.println(fileName + " does not contain the word " + wordToSearch);
			} else {
				if (linesContainingWord.size() == 1) {
					System.out.print(fileName + " has the word " + wordToSearch + " appear in line ");
				} else {
					System.out.print(fileName + " has the word " + wordToSearch + " appear in lines ");
				}
				for (int j = 0; j < linesContainingWord.size(); j++) {
					int toPrint = linesContainingWord.get(j);
					toPrint++;
					System.out.print(toPrint + " ");
				}
				System.out.println();
			}
		} catch (IOException e) {
			System.err.println("Error! Invalid argument for search! Proper Usage is: search userinput");
		}
	}

	/**
	 * Method will check first if file is empty.
	 * If file is empty, the method will inform the user that the file is empty. 
	 * If file is not empty, method will print out all the lines in the file for the user.
	 * 
	 * @param textFile	The file for text storage/manipulation.
	 * @throws IOException	If file cannot be opened.
	 */
	public static void displayFile(File textFile) {
		int lineNumber = 1;

		try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
			String fileOutput = null;
			boolean isFileEmpty = checkFileEmpty(textFile);

			if (isFileEmpty) {
				System.out.println(fileName + " is empty");
			} else {
				while ((fileOutput = br.readLine()) != null) {
					System.out.println(lineNumber + ". " + fileOutput);
					lineNumber++;
				}
			}
		} catch (IOException e) {
			System.err.println("Error! " + fileName + " could not be displayed! Proper Usage is: display");
		}
	}

	/**
	 * Checks if the file contains any lines.
	 * If file contain lines, returns false.
	 * If file does not contain lines, returns true.
	 * 
	 * @param textFile		The file for text storage/manipulation.
	 * @return isFileEmpty	If file is empty or not.
	 * @throws IOException	If file cannot be opened.
	 */
	public static boolean checkFileEmpty(File textFile) {
		boolean isFileEmpty = true;

		try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
			if (br.readLine() == null) {
				br.close();
				isFileEmpty = true;
			} else {
				br.close();
				isFileEmpty = false;
			}
		} catch (IOException e) {
			System.err.println("Error! " + fileName + " could not be checked if empty!");
		}

		return isFileEmpty;
	}

	/**
	 * Clears all text from the file by replacing the file with an empty file.
	 * 
	 * @param textFile	The file for text storage/manipulation.
	 * @throws IOException	If file cannot be opened.
	 */
	public static void clearFile(File textFile) {
		PrintWriter pw = null;

		try {
			File tempFile = new File(textFile.getAbsolutePath());
			pw = new PrintWriter(tempFile);
			pw.print("");
			textFile.delete();
			tempFile.renameTo(textFile);
			System.out.println("all content deleted from " + fileName);
		} catch (IOException e) {
			System.err.println("Error! " + fileName + " could not be cleared! Proper Usage is: clear");
		} finally {
			pw.close();
		}
	}

	/**
	 * Sorts the text by storing all the lines from the file in a temporary storage.
	 * Method will sort the text according to alphabetical order in the temporary storage. 
	 * After sorting has been completed, the lines in the temporary storage are added back into the file.
	 * 
	 * @param textFile	The file for text storage/manipulation.
	 * @throws IOException	If file cannot be opened.
	 */
	public static void sortFile(File textFile) {
		ArrayList<String> storageForLines = new ArrayList<String>();
		PrintWriter pw = null;

		try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
			String fileOutput;

			while ((fileOutput = br.readLine()) != null) {
				storageForLines.add(fileOutput);
			}

			Collections.sort(storageForLines);
			pw = new PrintWriter(textFile);

			for (int i = 0; i < storageForLines.size(); i++) {
				pw.println(storageForLines.get(i));
			}

			System.out.println(fileName + " has been sorted");
		} catch (IOException e) {
			System.err.println("Error! " + fileName + " could not be sorted! Proper Usage is: sort");
		} finally {
			pw.close();
		}
	}

	/**
	 * Method is called on to exit the TextBuddy program.
	 */
	public static void exitProgram() {
		System.exit(0);
	}
}