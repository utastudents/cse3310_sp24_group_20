package uta.cse3310; 

import java.util.ArrayList; 
import java.util.Collections; 
import java.util.Comparator;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.java_websocket.WebSocket; 

public class Leaderboard {
 
    private ArrayList<Player> players = new ArrayList<Player>();  

    public Leaderboard(ArrayList<Player> players) {
        this.players = players; 
        updateLeaderboard();

    } 

    public void addPlayer(Player player) {
        players.add(player); 
        updateLeaderboard();
    } 
    public void updateLeaderboard() {
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

    public ArrayList<Player> getPlayers() {
        return players;
    }

    // private void handleLeaderboard(WebSocket conn){
    //     //Sorting players in descending order of score
    //     Collections.sort(players);

    //     //Iterator for each player object
    //     Iterator playersIterator = players.iterator();


    //     String message = new String();

    //     for(Player p: playersIterator){
    //         String number = new String(p.getScore());
    //         String newMessage = new String(p.getPlayerUsername()+number+"\n");
    //         message.concat(newMessage);
    //     }
    //     String messageJson = gson.toJson(message);
    //     conn.send(messageJson);
    //     }
}