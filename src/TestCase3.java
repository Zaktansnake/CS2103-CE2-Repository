import java.io.File;
import junit.framework.TestCase;

public class TestCase3 extends TestCase {
	public void test() {
		String fileName = "myTestFile.txt";
		File myTestFile = new File(fileName);
		
		//Constructs the TextBuddy environment to run the test cases
		TextBuddy testTextBuddy = new TextBuddy();
		
		//Test case to test the different functions in TextBuddy
		testTextBuddy.addText(myTestFile, "little brown fox");
		testTextBuddy.addText(myTestFile, "jumped over the moon");
		testTextBuddy.displayFile(myTestFile);
		testTextBuddy.deleteLine(myTestFile, "1");
		testTextBuddy.checkFileEmpty(myTestFile);
		testTextBuddy.clearFile(myTestFile);
		testTextBuddy.addText(myTestFile, "little brown fox");
		testTextBuddy.displayFile(myTestFile);
		testTextBuddy.sortFile(myTestFile);
		testTextBuddy.searchWord(myTestFile, "little");
		testTextBuddy.addText(myTestFile, "thank you for listening");
		testTextBuddy.addText(myTestFile, "why u no like code");
		testTextBuddy.addText(myTestFile, "CS is awesome");
		testTextBuddy.displayFile(myTestFile);
		testTextBuddy.addText(myTestFile, "Software Engineering is awesome");
		testTextBuddy.searchWord(myTestFile, "awesome");
		testTextBuddy.addText(myTestFile, "Biology is awesome");
		testTextBuddy.sortFile(myTestFile);
		testTextBuddy.displayFile(myTestFile);
		testTextBuddy.deleteLine(myTestFile, "3");
		testTextBuddy.displayFile(myTestFile);
		testTextBuddy.deleteLine(myTestFile, "1");
		testTextBuddy.displayFile(myTestFile);
		testTextBuddy.deleteLine(myTestFile, "4");
		testTextBuddy.displayFile(myTestFile);
		testTextBuddy.sortFile(myTestFile);
		testTextBuddy.clearFile(myTestFile);
		testTextBuddy.displayFile(myTestFile);
		testTextBuddy.addText(myTestFile, "Biology is cool");
		testTextBuddy.displayFile(myTestFile);
		testTextBuddy.addText(myTestFile, "CS is cool");
		testTextBuddy.displayFile(myTestFile);
		testTextBuddy.addText(myTestFile, "Software Engineering is cool");
		testTextBuddy.displayFile(myTestFile);
		testTextBuddy.addText(myTestFile, "Computational Biology rocks");
		testTextBuddy.displayFile(myTestFile);
		testTextBuddy.searchWord(myTestFile, "cool");
	}
}
