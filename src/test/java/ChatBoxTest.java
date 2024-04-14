import junit.framework.TestCase;
import java.util.ArrayList;
import uta.cse3310.ChatBox;

public class ChatBoxTest extends TestCase {
    private ChatBox chatBox;

    // Set up the test environment before each test method
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Create a ChatBox instance with an empty list of messages for each test
        chatBox = new ChatBox(new ArrayList<>());
    }

    // Tear down the test environment after each test method
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        // Clean up the ChatBox instance to ensure test independence
        chatBox = null;
    }

    // Test the addMessage() method of the ChatBox class
    public void testAddMessage() {
        // Add a message to the chat box
        chatBox.addMessage("Hello!");
        // Verify that the message has been added to the list of messages
        assertEquals(1, chatBox.getMessages().size());
        assertEquals("Hello!", chatBox.getMessages().get(0));
    }

    // Test the displayChat() method of the ChatBox class
    // Since this method outputs to console, we can't directly test its output,
    // so we just verify that it runs without throwing exceptions
    public void testDisplayChat() {
        // Display the chat
        chatBox.displayChat();
    }
}
