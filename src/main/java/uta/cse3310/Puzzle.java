package uta.cse3310;

import java.util.List;
import java.util.ArrayList;
import java.util.Random; 
import java.io.Serializable;
import com.google.gson.Gson;

public class Puzzle { 
    private char[][] wordSearchPuzzle; 
    private int rows; 
    private int columns; 
    private char[] letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray(); 
    private WordList wordList = new WordList();
    public ArrayList<String> words = wordList.getWordList();
    private Random rand = new Random(); 
    private int fillerNum = 0;
    private int validWords = 0;
    private int foundWords = 0;
    private int leftWords;

    public Puzzle (int rows, int columns ) {
       this.rows = rows;
       this.columns = columns; 
       this.wordSearchPuzzle = new char[rows][columns];
       //initializeBoard();
       //fillRandomLetters();
    } 

    public void initializeBoard() { 
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                wordSearchPuzzle[i][j] = '-';
            } 
        } 
        for(String word : words) {
            placeWord(word.toUpperCase());
        }
    } 
    
    public void displayPuzzle() {
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                System.out.print(wordSearchPuzzle[i][j]);
            } 
            System.out.println();
        } 
    }
    public char[][] getPuzzle() {
        return wordSearchPuzzle;
    } 
    public ArrayList<String> getWordList() {
        return words;
    }

    public void fillRandomLetters() { 
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {  
                if( wordSearchPuzzle[i][j] == '-') {
                    wordSearchPuzzle[i][j] = letters[rand.nextInt(letters.length)]; 
                    fillerNum++; 
                }
            }
        }
    } 
    
    public int getFillerNum() {
        return fillerNum;
    }
    
    public void placeWord(String word) {
        int maxAttempts = 100;  // dont wanna blow up profs server so we limit the num of attempts
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            int startRow = rand.nextInt(rows);
            int startColumn = rand.nextInt(columns);
            int direction = rand.nextInt(3); // direction can either be horizontal, vertical, or diagonal
            int wordLength = word.length();

            int endRow = calculateEndRow(startRow, direction, wordLength);
            int endColumn = calculateEndColumn(startColumn, direction, wordLength);

            if (isPlacementValid(startRow, endRow, startColumn, endColumn, word, direction)) {
                placeCharacters(word, startRow, startColumn, direction);
                validWords++;
                return;
            }
        }
        System.out.println("Failed to place the word after " + maxAttempts + " attempts: " + word);
    }
    // Method checks Boundaries of the puzzle and prevevnts collisions an issue we were having on our previous iteration. Also incoporates new requirment with shared letters
    public boolean isPlacementValid(int startRow, int endRow, int startColumn, int endColumn, String word, int direction) {
        if (endRow >= rows || endColumn >= columns) {
            return false;
        }

        for (int i = 0; i < word.length(); i++) {
            int currentRow = startRow + (direction == 1 || direction == 2 ? i : 0);
            int currentColumn = startColumn + (direction == 0 || direction == 2 ? i : 0);
            char currentChar = word.charAt(i);
            char existingChar = wordSearchPuzzle[currentRow][currentColumn];

            if (existingChar != '-' && existingChar != currentChar) {
                return false;
            }
        }
        return true;
    }
    public void placeCharacters(String word, int startRow, int startColumn, int direction) {
        for (int i = 0; i < word.length(); i++) {
            int r = startRow + (direction == 1 || direction == 2 ? i : 0);
            int c = startColumn + (direction == 0 || direction == 2 ? i : 0);
            wordSearchPuzzle[r][c] = word.charAt(i);
        }
    } 
    // Checking the boundaries
    public int calculateEndRow(int startRow, int direction, int wordLength) {
        if (direction == 1) { 
            return startRow + wordLength - 1;
        } 
        else if (direction == 2) {
            return startRow + wordLength - 1;
        }
        return startRow; 
    }

    public int calculateEndColumn(int startColumn, int direction, int wordLength) {
        if (direction == 0) { 
            return startColumn + wordLength - 1;
        } 
        else if (direction == 2) { 
            return startColumn + wordLength - 1;
        }
        return startColumn; 
    }

    public int getValidWords() {
        return validWords;
    }

    public void incrementFoundWords() {
        if (wordSearchPuzzle != null) {
            foundWords++;
        } else {
            System.err.println("Error: Puzzle object is not initialized.");
        }
    }
    

    public int getFoundWords() {
        return foundWords;
    }

    public int remainingWords() {
        leftWords = validWords - foundWords;
        return leftWords;
    }

    public void displayUser() {
        // System.out.println("Density of the grid: " + );
        System.out.println("Number of valid Words in the puzzle: " + validWords);
        System.out.println("Number of filler letters: " + fillerNum);
        System.out.println("Number of found words:" + foundWords);
        return;
    }
    public void foundwords(){
        System.out.println("Number of found words:" + foundWords);
       // System.out.println("Number of remaining words:"+ leftWords);  

    } 

}


