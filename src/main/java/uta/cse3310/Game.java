package uta.cse3310; 

import java.util.ArrayList;

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
    public ArrayList<String> wordList() {
        return puzzle.getWordList();
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public int getGameIdentifier() {
        return gameIdentifier;
    } 
    boolean checkForWord(String userWord) { 
        ArrayList<String> wordsFromList = puzzle.getWordList();  

        for(String word : wordsFromList ) {
            if(word == userWord) {
                System.out.println("Word is in the wordlist");
                return true; 
            }
            else if(word != userWord) { 
                System.out.println("Word is not the wordlist");
                return false;
            } 
        }

        return true;
    }
    
    
    
}