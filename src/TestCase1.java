import java.io.File;
import junit.framework.TestCase;

public class TestCase1 extends TestCase {

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
		testTextBuddy.deleteLine(myTestFile, "all");
	}
}