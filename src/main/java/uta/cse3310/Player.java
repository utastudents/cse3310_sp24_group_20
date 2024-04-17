package uta.cse3310;

public class Player {  

  
    private String playerUsername; 
    private int playerIdentifier; 
    private int score; 
    private int totalGamesWon; 

    public Player(String playerUsername, int playerIdentifier, int score, int totalGamesWon) {
      this.playerUsername = playerUsername; 
      this.playerIdentifier = playerIdentifier; 
      this.score = score; 
      this.totalGamesWon = totalGamesWon;
    } 
    public Player(String playerUsername) {
        this.playerUsername = playerUsername;
        this.playerIdentifier = 0;
        this.score = 0;
        this.totalGamesWon = 0;
    }

    public String getPlayerUsername() {
        return playerUsername; 
    } 
    public void increaseScore(int pointsEarned) {
        score += pointsEarned;
    }
    public int getPlayerScore() {
        return score; 
    } 
    public void increaseWins(int win) {
        totalGamesWon += win; 
    } 
    public int getPlayerIdentifier() {
        return playerIdentifier;
    }

    public int getScore() {
        return score;
    }

    public int getTotalGamesWon() {
        return totalGamesWon;
    }
    
    
} 