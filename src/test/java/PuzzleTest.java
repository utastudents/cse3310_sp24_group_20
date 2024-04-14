import junit.framework.TestCase;

import uta.cse3310.Puzzle;

public class PuzzleTest extends TestCase {
    private Puzzle puzzle;

    // Set up the test environment before each test method
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Create a Puzzle instance with 5 rows and 5 columns for each test
        puzzle = new Puzzle(5, 5);
        // Initialize the puzzle board
        puzzle.initializeBoard();
    }

    // Tear down the test environment after each test method
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        // Clean up the Puzzle instance to ensure test independence
        puzzle = null;
    }

    // Test the initializeBoard() method of the Puzzle class
    public void testInitializeBoard() {
        // Get the puzzle board after initialization
        char[][] puzzleBoard = puzzle.getPuzzle();
        // Verify that the puzzle board is not null
        assertNotNull(puzzleBoard);
        // Verify that the puzzle board has the correct dimensions (5x5)
        assertEquals(5, puzzleBoard.length);
        assertEquals(5, puzzleBoard[0].length);
        // Verify that the puzzle board is filled with '-' characters
        for (int i = 0; i < puzzleBoard.length; i++) {
            for (int j = 0; j < puzzleBoard[0].length; j++) {
                assertEquals('-', puzzleBoard[i][j]);
            }
        }
    }

    // Test the displayPuzzle() method of the Puzzle class
    // Since this method outputs to console, we can't directly test its output,
    // so we just verify that it runs without throwing exceptions
    public void testDisplayPuzzle() {
        // Display the puzzle
        puzzle.displayPuzzle();
    }

    // Test the fillRandomLetters() method of the Puzzle class
    public void testFillRandomLetters() {
        // Fill the puzzle with random letters
        puzzle.fillRandomLetters();
        // Get the puzzle board after filling with random letters
        char[][] puzzleBoard = puzzle.getPuzzle();
        // Verify that the puzzle board is not null
        assertNotNull(puzzleBoard);
        // Verify that the puzzle board has the correct dimensions (5x5)
        assertEquals(5, puzzleBoard.length);
        assertEquals(5, puzzleBoard[0].length);
        // Verify that the puzzle board contains letters instead of '-'
        boolean containsLetters = false;
        for (int i = 0; i < puzzleBoard.length; i++) {
            for (int j = 0; j < puzzleBoard[0].length; j++) {
                if (Character.isLetter(puzzleBoard[i][j])) {
                    containsLetters = true;
                    break;
                }
            }
        }
        assertTrue(containsLetters);
    }

    // Test the placeWord() method of the Puzzle class
    public void testPlaceWord() {
        // Test placing a word on the puzzle board
        puzzle.placeWord("TEST");
        // Get the puzzle board after placing the word
        char[][] puzzleBoard = puzzle.getPuzzle();
        // Verify that the puzzle board is not null
        assertNotNull(puzzleBoard);
        // Verify that the puzzle board has the correct dimensions (5x5)
        assertEquals(5, puzzleBoard.length);
        assertEquals(5, puzzleBoard[0].length);
        // Verify that the word "TEST" is placed on the puzzle board
        boolean wordPlaced = false;
        for (int i = 0; i < puzzleBoard.length; i++) {
            for (int j = 0; j < puzzleBoard[0].length; j++) {
                if (puzzleBoard[i][j] == 'T') {
                    // Check if the word "TEST" is placed horizontally
                    if (j + 3 < puzzleBoard[0].length &&
                            puzzleBoard[i][j + 1] == 'E' &&
                            puzzleBoard[i][j + 2] == 'S' &&
                            puzzleBoard[i][j + 3] == 'T') {
                        wordPlaced = true;
                        break;
                    }
                    // Check if the word "TEST" is placed vertically
                    if (i + 3 < puzzleBoard.length &&
                            puzzleBoard[i + 1][j] == 'E' &&
                            puzzleBoard[i + 2][j] == 'S' &&
                            puzzleBoard[i + 3][j] == 'T') {
                        wordPlaced = true;
                        break;
                    }
                }
            }
        }
        assertTrue(wordPlaced);
    }
}
