package uta.cse3310; 

public class Game { 
    private Puzzle puzzle = new Puzzle(50,50); 
    int gameIdentifier; 


    public Game(int gameIdentifier) { 
        this.gameIdentifier = gameIdentifier;
    } 
    public void startGame() {
        puzzle.initializeBoard(); 
        puzzle.fillRandomLetters();
    } 
    public char[][] getBoard(){
        return puzzle.getPuzzle();
    }
    
} 
