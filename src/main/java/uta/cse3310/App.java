// This is example code provided to CSE3310 Fall 2022
// You are free to use as is, or changed, any of the code provided

// Please comply with the licensing requirements for the
// open source packages being used.

// This code is based upon, and derived from the this repository
//            https:/thub.com/TooTallNate/Java-WebSocket/tree/master/src/main/example

// http server include is a GPL licensed package from
//            http://www.freeutils.net/source/jlhttp/

/*
 * Copyright (c) 2010-2020 Nathan Rajlich
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without
 *  restriction, including without limitation the rights to use,
 *  copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following
 *  conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  OTHER DEALINGS IN THE SOFTWARE.
 */

package uta.cse3310;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;


import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.time.Instant;
import java.time.Duration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class App extends WebSocketServer {  

  // Test players for leaderboard 
  Set<Player> activeUsers = new HashSet<>();
  ArrayList<Player> players = new ArrayList<Player>();
  private Vector<Game> activeGames = new Vector<Game>(); 
  private Leaderboard leaderboard;
  private int GameId = 0;
  private int connectionId = 0;
  private Instant startTime; 
  private Puzzle puzzle;

  public App(int port) { 
     super(new InetSocketAddress(port)); 

  }
  public App(InetSocketAddress address) { 
    super(address);
    
  }
  public App(int port, Draft_6455 draft) { 
     super(new InetSocketAddress(port), Collections.<Draft>singletonList(draft));
    
  }

  @Override
  public void onOpen(WebSocket conn, ClientHandshake handshake) {   
      System.out.println(conn + " Connected!!");
      ServerEvent event = new ServerEvent();
      Game game = new Game(GameId + 1); 
      conn.setAttachment(game);
      event.gameId = game.gameIdentifier;
      Gson gson = new Gson(); 
      String jsonString = gson.toJson(event);
      broadcast(jsonString);
      game.startGame(); 
      
      char[][] puzzleGrid = game.getBoard(); 
      String puzzleJson = gson.toJson(puzzleGrid);
      conn.send("{\"type\": \"puzzle\", \"data\": " + puzzleJson + "}");  
      System.out.println(puzzleJson);
      
      ArrayList<String> wordsFromList = game.wordList(); 
      String wordListJson = gson.toJson(wordsFromList); 
      conn.send("{\"type\": \"wordList\", \"data\": " + wordListJson + "}"); 
      System.out.println(wordListJson); 

    
      /*
      Player player1 = new Player("Testplayer 1", 0, 0, 0); 
      Player player2 = new Player("Testplayer 2", 1, 0, 0);
      Player player3 = new Player("Testplayer 3", 2, 0, 0);
      Player player4 = new Player("Testplayer 4", 3, 0, 0); 

      players.add(player1);
      players.add(player2);
      players.add(player3);
      players.add(player4); 
       */
      Leaderboard leaderboard = new Leaderboard(players);

      String leaderboardJson = gson.toJson(leaderboard); 
      conn.send("{\"type\": \"leaderboard\", \"data\": " + leaderboardJson + "}"); 
      System.out.println(leaderboardJson);

  }

  @Override
  public void onClose(WebSocket conn, int code, String reason, boolean remote) { 
      System.out.println(conn + " has closed"); 
      Game game = conn.getAttachment();
      game = null;
    
  }

  @Override
  public void onMessage(WebSocket conn, String message) {  
  System.out.println("Received message: " + message);
    Game game = conn.getAttachment();
    Gson gson = new Gson(); 

    JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
        if (jsonObject.has("type") && jsonObject.get("type").getAsString().equals("username")) {
            String username = jsonObject.get("data").getAsString();
            handleLogin(conn, username);
            Player player = new Player(username, connectionId++, 0, 0); 
             players.add(player);
            conn.send("Username created: " + username);
        }
        else if(jsonObject.has("type") && jsonObject.get("type").getAsString().equals("wordCheck")) {
            String word = jsonObject.get("word").getAsString(); 
            System.out.println(word); 
            game.checkForWord(word.toLowerCase());
        }      
  }
  
  private void handleLogin(WebSocket conn, String username) {
    if (!isUsernameTaken(username)) {
      Player player = new Player(username);
      activeUsers.add(player);
      broadcastActiveUsers();
      Gson gson = new Gson();
      conn.send(gson.toJson(new Message("loginSuccess", "Login successful.")));
    } else {
      Gson gson = new Gson();
      conn.send(gson.toJson(new Message("loginFailure", "Username already taken.")));
    }
  }
  private boolean isUsernameTaken(String username) {

    for (Player player : activeUsers) {
      if (player.getPlayerUsername().equals(username)) {
        return true;
      }
    }
    return false;
  }

  // Broadcast active users to all clients
  private void broadcastActiveUsers() {
    List<String> activeUsernames = new ArrayList<>();
    Gson gson = new Gson();
    for (Player player : activeUsers) {
      activeUsernames.add(player.getPlayerUsername());
    }
    Message message = new Message("activeUsersUpdate", activeUsernames);
    broadcast(gson.toJson(message));
  }

  @Override
  public void onMessage(WebSocket conn, ByteBuffer message) {
      System.out.println(conn + ": " + message); 
  }

  @Override
  public void onError(WebSocket conn, Exception ex) { 
       ex.printStackTrace();
    
  }

  @Override
  public void onStart() {
    
  }

  private String escape(String S) { 

    return "escape";
    
  } 
  public static void main(String[] args) {   
    String HttpPort = System.getenv("HTTP_PORT");
    int port = 9080;
    if (HttpPort!=null) {
      port = Integer.valueOf(HttpPort);
    }

    // Set up the http server

    HttpServer H = new HttpServer(port, "./html");
    H.start();
    System.out.println("http Server started on port: " + port);

    // create and start the websocket server

    port = 9880;
    String WSPort = System.getenv("WEBSOCKET_PORT");
    if (WSPort!=null) {
      port = Integer.valueOf(WSPort);
    }

    App A = new App(port);
    A.setReuseAddr(true);
    A.start();
    System.out.println("websocket Server started on port: " + port);
      
  
    
    /*String sep = "=".repeat(50);
    
    WordList wordList = new WordList();
     
    Puzzle puzzle = new Puzzle(50,50); 
    puzzle.displayPuzzle();
    System.out.println("\n" + sep + "\n"); 
    wordList.displayWordList();
    */
  }
}