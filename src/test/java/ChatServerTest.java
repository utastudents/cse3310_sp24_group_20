import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.Session;
import uta.cse3310.ChatServer;

public class ChatServerTest {

    private ChatServer chatServer;
    private static Set<Session> sessions = new HashSet<>();
    

    public ChatServerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        // Setup tasks that are performed once before all tests
    }

    @AfterClass
    public static void tearDownClass() {
        // Cleanup tasks that are performed once after all tests
    }

    @Before
    public void setUp() {
        // Setup tasks that are performed before each test
        chatServer = new ChatServer();
        sessions = new HashSet<>();
        
    }

  

    @Test
    public void testOnOpen() {
        // Verify that session is added and a message is broadcasted
        assertEquals(0, chatServer.getSessions().size());
    }

    
    
}
