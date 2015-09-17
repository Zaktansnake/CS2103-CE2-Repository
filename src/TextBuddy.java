//CS2103 AY1516 CE2
//Name: David Chong Yong Ming
//Matric No: A0116633L
//Tutorial Group: F10
//Program Assumptions: This program assumes that all commands inputted by the user are correct and valid.
//If a invalid command is entered, it is ignored by the program and the program will continue to run till
//the exit command is entered.

import java.util.*;
import java.io.*;

public class TextBuddy {
	
	private static String fileName;
	
	public static void main(String[] args) {
		fileName = args[0];
		File textFile = new File(fileName);
		
		try {
			textFile.createNewFile();
			commandInterface(textFile);
		} catch(IOException e) {
			e.printStackTrace();
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
		    e.printStackTrace();
		}
	}
	
	public static void deleteLine(File textFile, String lineToDelete) {
		ArrayList<String> linesNotToDelete = new ArrayList<String>();
		PrintWriter pw = null;
				
		int deleteLineNumber = Integer.parseInt(lineToDelete);
		
		try(BufferedReader br = new BufferedReader(new FileReader(textFile))) {
			String fileOutput;
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
		} catch(IOException e) {
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}
	
	public static void exitFile() {
		System.exit(0);
	}
}