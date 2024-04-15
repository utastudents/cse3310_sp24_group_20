import org.junit.Test;
import static org.junit.Assert.*;
import uta.cse3310.ServerEvent;

public class ServerEventTest {

    @Test
    public void testSetGameId() {
        int gameId = 12345;
        ServerEvent event = new ServerEvent();
        
        // Set the game ID
        event.setGameId(gameId);
        
        // Check if the game ID is set correctly
        assertEquals(gameId, event.getGameId());
    }
}
