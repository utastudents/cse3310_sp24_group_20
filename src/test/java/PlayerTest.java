import org.junit.Test;
import static org.junit.Assert.*;
import uta.cse3310.Player;

public class PlayerTest {

    @Test
    public void testGetPlayerUsername() {
        String username = "testUser";
        Player player = new Player(username, 1, 0, 0);
        
        // Check if the player's username is set correctly
        assertEquals(username, player.getPlayerUsername());
    }
    
    @Test
    public void testIncreaseScore() {
        Player player = new Player("testUser", 1, 0, 0);
        
        // Increase the player's score by 10 points
        player.increaseScore(10);
        
        // Check if the player's score is incremented correctly
        assertEquals(10, player.getPlayerScore());
    }
    
    @Test
    public void testGetPlayerScore() {
        Player player = new Player("testUser", 1, 100, 0);
        
        // Check if the player's score is retrieved correctly
        assertEquals(100, player.getPlayerScore());
    }
    
    @Test
    public void testIncreaseWins() {
        Player player = new Player("testUser", 1, 0, 0);
        
        // Increase the player's total wins by 1
        player.increaseWins(1);
        
        // Check if the player's total wins is incremented correctly
        assertEquals(1, player.getTotalGamesWon());
    }
    
    @Test
    public void testGetPlayerIdentifier() {
        int identifier = 1;
        Player player = new Player("testUser", identifier, 0, 0);
        
        // Check if the player's identifier is retrieved correctly
        assertEquals(identifier, player.getPlayerIdentifier());
    }
}
