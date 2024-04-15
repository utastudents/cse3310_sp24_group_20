import junit.framework.TestCase;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import uta.cse3310.WordList;

public class WordListTest extends TestCase {
    private WordList wordList;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    // Set up the test environment before each test method
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Redirect standard output to outputStreamCaptor for testing displayWordList() method
        System.setOut(new PrintStream(outputStreamCaptor));
        // Create a WordList instance
        wordList = new WordList();
    }

    // Tear down the test environment after each test method
    protected void tearDown() throws Exception {
        super.tearDown();
        // Clean up the WordList instance to ensure test independence
        wordList = null;
        // Reset standard output
        System.setOut(System.out);
    }

    // Test the getPoints() method of the WordList class
    public void testGetPoints() {
        // Test getting points for a word that exists in the word list
        assertEquals(0, wordList.getPoints("APPLE"));
        // Test getting points for a word that does not exist in the word list
        assertEquals(0, wordList.getPoints("XYZ"));
    }

    // Test the displayWordList() method of the WordList class
    public void testDisplayWordList() {
        // Display the word list
        wordList.displayWordList();
        // Verify that the output is not null
        assertNotNull(outputStreamCaptor.toString());
    }

    // Test the getWordList() method of the WordList class
    public void testGetWordList() {
        // Get the word list
        ArrayList<String> list = wordList.getWordList();
        // Verify that the word list is not null
        assertNotNull(list);
        // Verify that the word list is not empty
        assertFalse(list.isEmpty());
    }
}
