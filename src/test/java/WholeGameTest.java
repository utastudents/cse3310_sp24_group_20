
import com.google.gson.JsonObject;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.junit.Test;
import org.mockito.Mockito;
import uta.cse3310.App;
import static org.mockito.Mockito.*;
import uta.cse3310.Game;


public class WholeGameTest {

    private App app;
    private WebSocket mockWebSocket;
    private ClientHandshake mockHandshake;
    private Map<String, WebSocket> userSessions;
    private Game mockGame1;
    private Game mockGame2;
    private WebSocket mockWebSocket1;
    private WebSocket mockWebSocket2;

    public WholeGameTest() {
    }

    @Before
    public void setUp() {
        app = new App(8080); // Assuming port 8080
        mockWebSocket = Mockito.mock(WebSocket.class);
        mockHandshake = Mockito.mock(ClientHandshake.class);
        userSessions = new HashMap<>();
        mockWebSocket1 = Mockito.mock(WebSocket.class);
        mockWebSocket2 = Mockito.mock(WebSocket.class);
        mockGame1 = Mockito.mock(Game.class);
        mockGame2 = Mockito.mock(Game.class);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testOnOpen() {
        // Mocking behavior for WebSocket connection
        when(mockWebSocket.getRemoteSocketAddress()).thenReturn(new InetSocketAddress("localhost", 1234));

        // Call the method to be tested
        app.onOpen(mockWebSocket, mockHandshake);

        // Verify interactions
        verify(mockWebSocket).send(anyString()); 

    }

   @Test
    public void testStartGame() {
        // Mock behavior for WebSocket connections
        Map<String, WebSocket> userSessions = new HashMap<>();
        userSessions.put("session1", mockWebSocket1);
        userSessions.put("session2", mockWebSocket2);
        when(mockWebSocket1.getAttachment()).thenReturn(mockGame1);
        when(mockWebSocket2.getAttachment()).thenReturn(mockGame2);
        when(mockWebSocket1.isOpen()).thenReturn(true);
        when(mockWebSocket2.isOpen()).thenReturn(true);

        // Mock behavior for Game objects
        char[][] mockPuzzleGrid1 = {{'A', 'B'}, {'C', 'D'}};
        char[][] mockPuzzleGrid2 = {{'E', 'F'}, {'G', 'H'}};
        when(mockGame1.getBoard()).thenReturn(mockPuzzleGrid1);
        when(mockGame2.getBoard()).thenReturn(mockPuzzleGrid2);
        when(mockGame1.wordList()).thenReturn(new ArrayList<>(Arrays.asList("WORD1", "WORD2")));
        when(mockGame2.wordList()).thenReturn(new ArrayList<>(Arrays.asList("WORD3", "WORD4")));

        

        // Verify interactions
        verify(mockWebSocket1).send("{\"type\": \"puzzle\", \"data\": [[\"A\",\"B\"],[\"C\",\"D\"]]}");
        verify(mockWebSocket1).send("{\"type\": \"wordList\", \"data\": [\"WORD1\",\"WORD2\"]}");
        verify(mockWebSocket2).send("{\"type\": \"puzzle\", \"data\": [[\"E\",\"F\"],[\"G\",\"H\"]]}");
        verify(mockWebSocket2).send("{\"type\": \"wordList\", \"data\": [\"WORD3\",\"WORD4\"]}");
      
    }
    
    
    @Test
    public void testOnMessage_UsernameMessageType() {
        // Mock behavior for the WebSocket connection
        when(mockWebSocket.getAttachment()).thenReturn(null); // No game attached

        // Call the method to be tested with a message of type "username"
        app.onMessage(mockWebSocket, "{\"type\": \"username\", \"data\": \"John\"}");

        // Verify that handleUsername method is called with the correct parameters
        verify(app).handleUsername(mockWebSocket, "John");
       
    }

    @Test
    public void testOnMessage_ChatMessageType() {
        // Mock behavior for the WebSocket connection
        when(mockWebSocket.getAttachment()).thenReturn(null); // No game attached

        // Call the method to be tested with a message of type "chatMessage"
        app.onMessage(mockWebSocket, "{\"type\": \"chatMessage\", \"data\": {\"username\": \"Alice\", \"message\": \"Hello!\"}}");

        // Verify that handleChatMessage method is called with the correct parameters
        verify(app).handleChatMessage(eq(mockWebSocket), any());
        
    }
    
    
    @Test
    public void testHandleWordCheck_WordFoundInList() {
        // Mock behavior for the WebSocket connection
        Game mockGame = Mockito.mock(Game.class);
        when(mockWebSocket.getAttachment()).thenReturn(mockGame);

        // Mock message data
        JsonObject messageJson = new JsonObject();
        messageJson.addProperty("username", "John");
        messageJson.addProperty("word", "apple");

        // Mock game behavior
        when(mockGame.isWordInList("apple")).thenReturn(true);
        when(mockGame.getNumPlayers()).thenReturn(2); 
        when(mockGame.getBoard()).thenReturn(new char[10][10]); 

        // Call the method to be tested
        app.handleWordCheck(mockWebSocket, messageJson);

    }
}
