package uta.cse3310;

import java.util.List;

public class GameBoard { 
    private char[][] board = new char[50][50]; 

    public GameBoard(char[][] board ) { 
        this.board = board; 

    } 

    public void initializeBoard() {

    } 
    public void displayBoard() {

    } 
    public void fillRandomLetters() {

    }
    public void placeWords(List<String> words) {

    } 
    public char getCell(int x, int y) {
        char cell = 'o';

        return cell;
    }
}