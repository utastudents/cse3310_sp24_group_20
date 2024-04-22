import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uta.cse3310.ActiveUserList;

/**
 *
 * @author admin
 */
public class ActiveUserListTest {
    
    private ActiveUserList activeUserList;
    
    public ActiveUserListTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        // Setup tasks that are performed before each test
        Set<String> activeUsers = new HashSet<>();
        activeUsers.add("user1");
        activeUsers.add("user2");
        activeUserList = new ActiveUserList(activeUsers);
    }
    
    @After
    public void tearDown() {
        // Cleanup tasks that are performed after each test
        activeUserList = null;
    }

    // Test method to verify if getActiveUsers returns correct set of active users
    @Test
    public void testGetActiveUsers() {
        Set<String> expectedUsers = new HashSet<>();
        expectedUsers.add("user1");
        expectedUsers.add("user2");
        assertEquals(expectedUsers, activeUserList.getActiveUsers());
    }

    // Test method to verify if setActiveUsers sets active users correctly
    @Test
    public void testSetActiveUsers() {
        Set<String> newActiveUsers = new HashSet<>();
        newActiveUsers.add("user3");
        newActiveUsers.add("user4");
        activeUserList.setActiveUsers(newActiveUsers);
        assertEquals(newActiveUsers, activeUserList.getActiveUsers());
    }
}
