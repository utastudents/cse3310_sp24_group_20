import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import uta.cse3310.Lobby;
import uta.cse3310.Player;

/**
 *
 * @author admin
 */
public class LobbyTest {
    
    public LobbyTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

   // Test the authenticatePlayer() method of the Lobby class
    public void testAuthenticatePlayer() {
        // Create a Lobby instance
        Lobby lobby = new Lobby();
        
        // Authenticate a player with the name "John"
        Player player = lobby.authenticatePlayer("John");
        
        // Verify that the returned player has the correct name and score
        assertEquals("test Player", player.getName());
        assertEquals(0, player.getScore());
    }
}
