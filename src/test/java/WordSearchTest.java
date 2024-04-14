import junit.framework.TestCase;
import uta.cse3310.WordSearch;

public class WordSearchTest extends TestCase {
    private WordSearch wordSearch;

    // Set up the test environment before each test method
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Create a WordSearch instance with a word "TEST", start position (0, 0), and direction "RIGHT" for each test
        wordSearch = new WordSearch("TEST", new int[]{0, 0}, "RIGHT");
    }

    // Tear down the test environment after each test method
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        // Clean up the WordSearch instance to ensure test independence
        wordSearch = null;
    }

    // Test the getWord() method of the WordSearch class
    public void testGetWord() {
        // Verify that the getWord() method returns the correct word "TEST"
        assertEquals("TEST", wordSearch.getWord());
    }

    // Test the getStartPosition() method of the WordSearch class
    public void testGetStartPosition() {
        // Verify that the getStartPosition() method returns the correct start position (0, 0)
        int startPosition = wordSearch.getStartPosition();
        assertNotNull(startPosition);
        assertEquals(0, startPosition);
        
    }

    // Test the getDirection() method of the WordSearch class
    public void testGetDirection() {
        // Verify that the getDirection() method returns the correct direction "RIGHT"
        assertEquals("RIGHT", wordSearch.getDirection());
    }
}
