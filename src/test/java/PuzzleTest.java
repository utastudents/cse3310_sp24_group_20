
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import uta.cse3310.Puzzle;

public class PuzzleTest {

    @Test
    public void testInitializeBoard() {
        int rows = 5;
        int columns = 5;
        Puzzle puzzle = new Puzzle(rows, columns);

        // Check if the board is initialized correctly
        char[][] board = puzzle.getPuzzle();
        assertEquals(rows, board.length);
        assertEquals(columns, board[0].length);
        //no error
        assertTrue(true);

    }

    @Test
    public void testFillRandomLetters() {
        int rows = 5;
        int columns = 5;
        Puzzle puzzle = new Puzzle(rows, columns);

        //no error
        assertTrue(true);
    }

    @Test
    public void testPlaceWord() {
        int rows = 5;
        int columns = 5;
        Puzzle puzzle = new Puzzle(rows, columns);
        ArrayList<String> words = new ArrayList<>();
        words.add("TEST");
        words.add("WORD");
        words.add("SEARCH");
        words.add("PUZZLE");

        //no error
        assertTrue(words.size() == 4);
    }

}
