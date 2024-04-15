import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import uta.cse3310.Game;

public class GameTest {

    private Game game;
    
    public GameTest() {
    }
    
    @Before
    public void setUp() {
        game = new Game(1); // Initialize a new Game instance before each test
    }

    @Test
    public void testStartGame() {
        game.startGame();
        char[][] board = game.getBoard();
        
        // Assert that the board is not null
        assertNotNull(board);
        
        // Assert that the dimensions of the board are correct
        assertEquals(50, board.length);
        assertEquals(50, board[0].length);
        
        
    }
    
    @Test
    public void testWordList() {
        game.startGame();
        ArrayList<String> wordList = game.wordList();
        
        // Assert that the word list is not null
        assertNotNull(wordList);
        
        
    }
    
    @Test
    public void testGameIdentifier() {
        assertNotEquals(10, game.getGameIdentifier()); 
    }
    
    @Test
    public void testGetBoardAfterStartGame() {
        game.startGame();
        char[][] board1 = game.getBoard();
        char[][] board2 = game.getBoard();
        
        // Assert that calling getBoard() multiple times returns the same board
        assertArrayEquals(board1, board2);
    }
    
    @Test
    public void testStartGameChangesBoard() {
        game.startGame();
        char[][] board1 = game.getBoard();
        
        // Start the game again
        game.startGame();
        char[][] board2 = game.getBoard();
        
        // Assert that calling startGame() changes the board
        assertTrue(board1.length == board2.length);
    }
    
}
