import junit.framework.TestCase;
import uta.cse3310.Player;

public class PlayerTest extends TestCase {
    private Player player;

    // Set up the test environment before each test method
    protected void setUp() throws Exception {
        super.setUp();
        
        // Create a Player instance with a name "TestPlayer" and an initial score of 100 for each test
        player = new Player("TestPlayer", 100);
    }

    // Tear down the test environment after each test method
    protected void tearDown() throws Exception {
        super.tearDown();
        
        // Clean up the Player instance to ensure test independence
        player = null;
    }

    // Test the getName() method of the Player class
    public void testGetName() {
        // Verify that the getName() method returns the correct name "TestPlayer"
        assertEquals("TestPlayer", player.getName());
    }

    // Test the getScore() method of the Player class
    public void testGetScore() {
        // Verify that the getScore() method returns the correct score 100
        assertEquals(0, player.getScore());
    }

    // Test the updateScore() method of the Player class
    public void testUpdateScore() {
        // Update the player's score by adding 50
        player.updateScore(50);
        
        // Verify that the score has been updated to 150
        assertEquals(150, player.getScore());
    }
}
