/* CS2103 AY1516 CE2
 * Name: David Chong Yong Ming
 * Matric No: A0116633L
 * Tutorial Group: F10
 */

import java.util.*;
import java.io.*;

public class TextBuddy {
	
	private static String fileName;
	
	public static void main(String[] args) {
		checkArgument(args);
		fileName = args[0];
		createFile(fileName);
	}
	
	/**
	 * @param arguments keyed in at the command line
	 * method: checks if arguments are valid
	 * If valid, program will continue to run
	 * If invalid, informs user of proper usage and closes program
	 */ 
	public static void checkArgument(String[] args) {
		if(args.length == 0) {
			System.err.println("Error! Invalid use of TextBuddy! Proper Usage is: Java TextBuddy filename.txt");
			exitFile();
		}
	}
	
	public static void createFile(String fileName) {
		File textFile = new File(fileName);
		
		try {
			textFile.createNewFile();
			commandInterface(textFile);
		} catch(IOException e) {
			System.err.println("Error! File cannot be created!");
		}
	}
	
	public static void commandInterface(File textFile) {
		Scanner scan = new Scanner(System.in);
		String userCommand;
		System.out.println("Welcome to TextBuddy. " + fileName + " is ready for use");
		
		while(true) {
			System.out.print("command:");
			userCommand = scan.next();
			
			switch(userCommand) {
				case "add":
					String textToAdd = scan.nextLine();
					textToAdd = textToAdd.trim();
					addText(textFile, textToAdd);
					break;
					
				case "delete":
					String lineToDelete = scan.nextLine();
					lineToDelete = lineToDelete.trim();
					deleteLine(textFile, lineToDelete);
					break;
						
				case "search":
					String wordToSearch = scan.nextLine();
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
					exitFile();
				}
			}
	}

	public static void addText(File textFile, String textToAdd) {
		try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(textFile, true)))) {
			pw.println(textToAdd);
			System.out.println("added to " + fileName + ": \"" + textToAdd + "\"");
		} catch(IOException e) {
			System.err.println("Error! Invalid argument for add! Proper Usage is: add userinput");
		}
	}
	
	public static void deleteLine(File textFile, String lineToDelete) {
		ArrayList<String> linesNotToDelete = new ArrayList<String>();
		PrintWriter pw = null;
		
		try(BufferedReader br = new BufferedReader(new FileReader(textFile))) {
			String fileOutput;
			int deleteLineNumber = Integer.parseInt(lineToDelete);
			int lineNumber = 0;
			
			while((fileOutput = br.readLine()) != null) {
				lineNumber++;
				if(lineNumber == deleteLineNumber) {
					System.out.println("deleted from " + fileName + ": \"" + fileOutput + "\"");
				} else {
					linesNotToDelete.add(fileOutput);
				}
			}
			
			pw = new PrintWriter(textFile);
			
			for(int i = 0; i < linesNotToDelete.size(); i++) {
				pw.println(linesNotToDelete.get(i));
			}
		} catch(Exception e) {
			System.err.println("Error! Invalid argument for delete! Proper Usage is: delete linenumber");
		} finally {
			pw.close();
		}
	}
	
	public static void searchWord(File textFile, String wordToSearch) {
		ArrayList<String> linesFromFile = new ArrayList<String>();
		ArrayList<Integer> linesContainingWord = new ArrayList<Integer>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(textFile))) {
			String fileOutput;
			
			while((fileOutput = br.readLine()) != null) {
				linesFromFile.add(fileOutput);
			}
			
			for(int i = 0; i < linesFromFile.size(); i++) {
				String toCheck = linesFromFile.get(i);
				if(toCheck.contains(wordToSearch)) {
					linesContainingWord.add(i);
				}
			}
			
			if(linesContainingWord.isEmpty()) {
				System.out.println(fileName + " does not contain the word " + wordToSearch);
			} else {
				if(linesContainingWord.size() == 1) {
					System.out.print(fileName + " has the word " + wordToSearch + " appear in line ");
				} else {
					System.out.print(fileName + " has the word " + wordToSearch + " appear in lines ");
				}
				for(int j = 0; j < linesContainingWord.size(); j++) {
					int toPrint = linesContainingWord.get(j);
					toPrint++;
					System.out.print(toPrint + " ");
				}
				System.out.println();
			}
		} catch(IOException e) {
			System.err.println("Error! Invalid argument for search! Proper Usage is: search userinput");
		}
	}
	
	public static void displayFile(File textFile) {
		int lineNumber = 1;
		
		try(BufferedReader br = new BufferedReader(new FileReader(textFile))) {
			String fileOutput = null;
			boolean isFileEmpty = checkFileEmpty(textFile);
			
			if(isFileEmpty) {
				System.out.println(fileName + " is empty");
			} else {
				while((fileOutput = br.readLine()) != null) {
					System.out.println(lineNumber + ". " + fileOutput);
					lineNumber++;
				}	
			}
		} catch(IOException e) {
			System.err.println("Error! " + fileName + " could not be displayed! Proper Usage is: display");
		}
	}
	
	public static boolean checkFileEmpty(File textFile) {
		boolean isFileEmpty = true;
		
		try(BufferedReader br = new BufferedReader(new FileReader(textFile))) {
			if(br.readLine() == null) {
				br.close();
				isFileEmpty = true;
			} else {
				br.close();
				isFileEmpty = false;
			}
		} catch(IOException e) {
			System.err.println("Error! " + fileName + " could not be checked if empty!");
		}
		
		return isFileEmpty;
	}
	
	public static void clearFile(File textFile) {
		PrintWriter pw = null;
		
		try {
			File tempFile = new File(textFile.getAbsolutePath());
			pw = new PrintWriter(tempFile);
			pw.print("");
			textFile.delete();
			tempFile.renameTo(textFile);
			System.out.println("all content deleted from " + fileName);
		} catch(IOException e) {
			System.err.println("Error! " + fileName + " could not be cleared! Proper Usage is: clear");
		} finally {
			pw.close();
		}
	}
	
	public static void sortFile(File textFile) {
		ArrayList<String> storageForLines = new ArrayList<String>();
		PrintWriter pw = null;
		
		try(BufferedReader br = new BufferedReader(new FileReader(textFile))) {
			String fileOutput;
			
			while((fileOutput = br.readLine()) != null) {
				storageForLines.add(fileOutput);
			}
			
			Collections.sort(storageForLines);
			pw = new PrintWriter(textFile);
			
			for(int i = 0; i < storageForLines.size(); i++) {
				pw.println(storageForLines.get(i));
			}
			
			System.out.println(fileName + " has been sorted");
		} catch(IOException e) {
			System.err.println("Error! " + fileName + " could not be sorted! Proper Usage is: sort");
		} finally {
			pw.close();
		}
	}
	
	public static void exitFile() {
		System.exit(0);
	}
}