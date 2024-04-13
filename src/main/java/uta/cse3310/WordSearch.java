package uta.cse3310;


public class WordSearch {
    
    private String word;
    private String direction;  
    private  int[] startPosition; 
    
    public WordSearch(String word, int[] startPosition, String direction) {
        this.word = word; 
        this.startPosition = startPosition; 
        this.direction = direction; 

    } 
    public String getWord() { 
        String Word = "word";
        return Word; 
    } 
    public int getStartPosition() { 
        int Number = 0;
        return Number;
    }
    public String getDirection() { 
        String direction = "direction";
        return direction;
    }

}