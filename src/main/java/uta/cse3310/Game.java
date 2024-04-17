package uta.cse3310; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game { 
    private Puzzle puzzle = new Puzzle(50,50); 
    int gameIdentifier; 
    private Map<String, Integer> userScores = new HashMap<>();

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
            if(word.equals(userWord)) {
                System.out.println("Word is in the wordlist");
                return true; 
            }
        } 
        System.out.println("Word is not the wordlist");
        return false;
    }

    public boolean isWordInList(String word) {
     ArrayList<String> wordsFromList = puzzle.getWordList();  
        for (String wordInList : wordsFromList) {
            if (wordInList.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }
     
    
    private void updateUserScore(String username, int score) {
        userScores.put(username, userScores.getOrDefault(username, 0) + score);
    }

    public Map<String, Integer> getUserScores() {
        return userScores;
    }
    
}