import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import uta.cse3310.Leaderboard;
import uta.cse3310.Player;

public class LeaderboardTest {

    @Test
    public void testAddPlayer() {
        ArrayList<Player> players = new ArrayList<>();
        Player player1 = new Player("player1", 1, 0, 0);
        Leaderboard leaderboard = new Leaderboard(players);
        
        // Add a player to the leaderboard
        leaderboard.addPlayer(player1);
        
        // Check if the player is added to the leaderboard
        assertEquals(1, leaderboard.getPlayers().size());
        assertTrue(leaderboard.getPlayers().contains(player1));
    }
    
    @Test
    public void testUpdateLeaderboard() {
        ArrayList<Player> players = new ArrayList<>();
        Player player1 = new Player("player1", 1, 100, 0);
        Player player2 = new Player("player2", 2, 200, 0);
        players.add(player1);
        players.add(player2);
        
        Leaderboard leaderboard = new Leaderboard(players);
        
        // Check if the leaderboard is sorted correctly after initialization
        assertEquals(player2, leaderboard.getPlayers().get(0)); // player2 should be first
        
        // Add a player with a higher score
        Player player3 = new Player("player3", 3, 300, 0);
        leaderboard.addPlayer(player3);
        
        // Check if the leaderboard is updated correctly after adding a player
        assertEquals(player3, leaderboard.getPlayers().get(0)); // player3 should be first
    }
    
    @Test
    public void testToString() {
        ArrayList<Player> players = new ArrayList<>();
        Player player1 = new Player("player1", 1, 100, 0);
        Player player2 = new Player("player2", 2, 200, 0);
        players.add(player1);
        players.add(player2);
        
        Leaderboard leaderboard = new Leaderboard(players);
        String expected = "1) player2     200\n2) player1     100\n";
        
        // Check if the toString method returns the expected string representation of the leaderboard
        assertEquals(expected, leaderboard.toString());
    }
}
