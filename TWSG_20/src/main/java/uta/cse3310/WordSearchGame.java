package uta.cse3310;

import java.util.Set;
import java.util.List; 
import java.util.Random;

public class WordSearchGame { 
    private char[][] wordSearchPuzzle; 
    private int rows; 
    private int columns; 
    private char[] letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray(); 
    private Random rand = new Random();  

    public WordSearchGame(int rows, int columns) {
        this.rows = rows; 
        this.columns = columns; 
        this.wordSearchPuzzle = new char[rows][columns];
        generatePuzzle(); 

        
    } 

    public void generatePuzzle() {
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++) {
                wordSearchPuzzle[i][j] = letters[rand.nextInt(letters.length)];
            }
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
}
   
