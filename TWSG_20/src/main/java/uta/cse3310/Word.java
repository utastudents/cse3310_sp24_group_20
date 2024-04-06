package uta.cse3310; 

public class Word {
    private String word; 
    private int pointPerWord; 

    public Word(String word, int pointPerWord) {
        this.word = word; 
        this.pointPerWord = pointPerWord; 
    }  
    public int getScore() { 
        return pointPerWord;

    } 
    @Override
    public String toString() {
        return String.format("%s", word);
    }
}