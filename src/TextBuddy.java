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
			userCommand = scan.nextLine();
			
			if(userCommand.contains("delete")) {
				deleteLine(textFile, userCommand);
			} else if(userCommand.contains("add")) {
				addText(textFile, userCommand);
			} else if(userCommand.contains("search")) {
				searchWord(textFile, userCommand);
			} else {
				switch(userCommand) {
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
						System.exit(0);
				}
			}
		}
	}

	public static void addText (File textFile, String userCommand) {
		String toAdd = userCommand.substring(4);
		
		try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(textFile, true)))) {
			pw.println(toAdd);
			System.out.println("added to " + fileName + ": \"" + toAdd + "\"");
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
		boolean result = true;
		
		try(BufferedReader br = new BufferedReader(new FileReader(textFile))) {
			if(br.readLine() == null) {
				br.close();
				result = true;
			} else {
				br.close();
				result = false;
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void deleteLine(File textFile, String userCommand) {
		ArrayList<String> tempArrayList = new ArrayList<String>();
		PrintWriter pw = null;
		
		try(BufferedReader br = new BufferedReader(new FileReader(textFile))) {
			String fileOutput;
			int deleteLineNumber = Integer.parseInt(userCommand.substring(7));
			int lineNumber = 0;
			
			while((fileOutput = br.readLine()) != null) {
				lineNumber++;
				if(lineNumber == deleteLineNumber) {
					System.out.println("deleted from " + fileName + ": \"" + fileOutput + "\"");
				} else {
					tempArrayList.add(fileOutput);
				}
			}
			
			pw = new PrintWriter(textFile);
			
			for(int i = 0; i < tempArrayList.size(); i++) {
				pw.println(tempArrayList.get(i));
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
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
		ArrayList<String> tempArrayList = new ArrayList<String>();
		PrintWriter pw = null;
		
		try(BufferedReader br = new BufferedReader(new FileReader(textFile))) {
			String fileOutput;
			
			while((fileOutput = br.readLine()) != null) {
				tempArrayList.add(fileOutput);
			}
			
			Collections.sort(tempArrayList);
			pw = new PrintWriter(textFile);
			
			for(int i = 0; i < tempArrayList.size(); i++) {
				pw.println(tempArrayList.get(i));
			}
			
			System.out.println(fileName + " has been sorted");
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}
	
	public static void searchWord(File textFile, String userCommand) {
		ArrayList<String> tempArrayList = new ArrayList<String>();
		ArrayList<Integer> linesContainingWord = new ArrayList<Integer>();
		String wordToSearch = userCommand.substring(7);
		
		try(BufferedReader br = new BufferedReader(new FileReader(textFile))) {
			String fileOutput;
			
			while((fileOutput = br.readLine()) != null) {
				tempArrayList.add(fileOutput);
			}
			
			for(int i = 0; i < tempArrayList.size(); i++) {
				String toCheck = tempArrayList.get(i);
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
					System.out.print(toPrint + " ");
				}
				System.out.println();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}