package uta.cse3310;

public class Player { 
  
    private String playerUsername; 
    private int playerIdentifier; 
    private int score; 
    private int totalGamesWon; 

    public Player(String playerUsername, int playerIdentifier) {
      this.playerUsername = playerUsername; 
      this.playerIdentifier = playerIdentifier; 
      this.score = 0; 
      this.totalGamesWon = 0;
    } 
    public String getPlayerUsername() {
      return playerUsername; 
    } 
    public int increaseScore(int pointsEarned) {
     return score + pointsEarned; 
    } 
    public int getPlayerScore() {
      return score; 
    } 
    public int increaseWins(int win) {
      return totalGamesWon + win; 
    } 
    public int getPlayerIdentifier() {
      return playerIdentifier;
    }
    
} 