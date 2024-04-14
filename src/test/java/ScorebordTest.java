import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import uta.cse3310.Scoreboard;

public class ScorebordTest extends TestCase {
    private Scoreboard scoreboard;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    // Set up the test environment before each test method
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Redirect standard output to outputStreamCaptor for testing displayScore() method
        System.setOut(new PrintStream(outputStreamCaptor));
        // Create a Scoreboard instance with an initial score of 0 for each test
        scoreboard = new Scoreboard(0);
    }

    // Tear down the test environment after each test method
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        // Clean up the Scoreboard instance to ensure test independence
        scoreboard = null;
        // Reset standard output
        System.setOut(System.out);
    }

    // Test the updateScore() method of the Scoreboard class
    public void testUpdateScore() {
        // Update the score by adding 50
        scoreboard.updateScore(50);
        // Verify that the score has been updated to 50
        assertEquals(50, scoreboard.getScore());
    }

    // Test the displayScore() method of the Scoreboard class
    public void testDisplayScore() {
        // Display the score
        scoreboard.displayScore();
        // Verify that the correct score message is printed
        assertEquals("Score: 0\n", outputStreamCaptor.toString());
    }
}
