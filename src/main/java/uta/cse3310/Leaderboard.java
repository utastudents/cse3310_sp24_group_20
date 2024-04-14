package uta.cse3310; 

import java.util.ArrayList; 
import java.util.Collections; 
import java.util.Comparator; 

public class Leaderboard {
 
    private ArrayList<Player> players = new ArrayList<Player>();  

    public Leaderboard(ArrayList<Player> players) {
        this.players = players; 
        Collections.sort(this.players, Comparator.comparingInt(Player::getPlayerScore).reversed());

    } 
    
    @Override
    public String toString() { 
        StringBuilder returnString = new StringBuilder();
        for(int i = 0; i < players.size(); i++) {
            returnString.append(i+1).append(") ").append(players.get(i).getPlayerUsername()).append("     ").append(players.get(i).getPlayerScore()).append("\n");

        } 
        return returnString.toString();
    }
}