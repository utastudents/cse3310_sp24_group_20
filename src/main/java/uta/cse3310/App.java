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
 import java.util.HashMap;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;
 import java.util.HashSet;
 
 import javax.swing.text.html.HTMLDocument.Iterator;
 import javax.websocket.OnClose;
 import javax.websocket.Session;
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
 import com.google.gson.JsonSyntaxException;

 
public class App extends WebSocketServer {  
 
    Set<Player> activeUsers = new HashSet<>();
    Set<Player> userlist = new HashSet<>();

    ArrayList<Player> players = new ArrayList<Player>();
    private Vector<Game> activeGames = new Vector<Game>(); 
    private Leaderboard leaderboard;
    private int GameId = 0;
    private int connectionId = 0;
    private Instant startTime; 
    private Puzzle puzzle; 
    private int playerIdentifier = 0;
  
    private Duration gameDuration = Duration.ofMinutes(8); // Set the game duration to 8 minutes

    private static final Map<String, WebSocket> userSessions = new HashMap<>(); 
    private static String version = System.getenv("VERSION");
    
    private Map<String, Integer> scoreboard = new HashMap<>(); 
 
    public App(int port) { 
        super(new InetSocketAddress(port)); 
        // Initialize the puzzle with a default size
        this.puzzle = new Puzzle(50, 50);
   }
   
   public App(InetSocketAddress address) { 
        super(address);
   }
   public App(int port, Draft_6455 draft) { 
        super(new InetSocketAddress(port), Collections.<Draft>singletonList(draft));
     
   }
 
   private void startGameForAll() { 
       startTime = Instant.now();
       long remainingTime = gameDuration.getSeconds();
       Gson gson = new Gson();

    // Start the game and send initial game data to all clients
       for (WebSocket session : userSessions.values()) {
           Game game = session.getAttachment();
           if (game != null && session.isOpen()) {
               game.startGame();

            
               char[][] puzzleGrid = game.getBoard();
               String puzzleJson = gson.toJson(puzzleGrid);
               session.send("{\"type\": \"puzzle\", \"data\": " + puzzleJson + "}");  // Send puzzle grid to front-end
               System.out.println(puzzleJson);

            
               ArrayList<String> wordsFromList = game.wordList(); 
               String wordListJson = gson.toJson(wordsFromList); 
               session.send("{\"type\": \"wordList\", \"data\": " + wordListJson + "}"); // Send WordList to front-end
               System.out.println(wordListJson);

               session.send("{\"type\": \"startTimer\", \"data\": " + remainingTime + "}"); // Send timer over to front end
           }
       }

    // This section of code controls the time of the game. Its currently set to 8 minutes inorder to meet requriements.
       Timer timer = new Timer();
       timer.scheduleAtFixedRate(new TimerTask() {
           @Override
           public void run() {
               long elapsedTime = Duration.between(startTime, Instant.now()).getSeconds();
               long remainingTime = gameDuration.getSeconds() - elapsedTime;
               if (remainingTime <= 0) {
                   timer.cancel(); 
                   endGame(); 
               }
               for (WebSocket session : userSessions.values()) {
                   try {
                       if (session.isOpen()) { 
                       session.send("{\"type\": \"updateTimer\", \"data\": " + remainingTime + "}");
                       }
                   } 
                   catch (Exception e) {
                       System.out.println("Error sending timer update: " + e.getMessage());
                   }
               }
           }
       }, 0, 1000); 
   }

 
 
   @Override
   public void onOpen(WebSocket conn, ClientHandshake handshake) {   
       System.out.println(conn + " Connected!!"); 
       Gson gson = new Gson(); 

       //Obtain VERSION variable in order to print out the github hash on the title of the webpage. 
       String versionMessage = gson.toJson(new Message("version", version));
       System.out.println(versionMessage);
       conn.send(versionMessage);

       userSessions.put(conn.getRemoteSocketAddress().toString(), conn);
       ServerEvent event = new ServerEvent();
       Game game = new Game(GameId + 1); 
       conn.setAttachment(game);
       event.gameId = game.gameIdentifier;
       String jsonString = gson.toJson(event);
       broadcast(jsonString);
       game.startGame(); 
 
       Leaderboard leaderboard = new Leaderboard(players);
 
       String leaderboardJson = gson.toJson(leaderboard); 
       conn.send("{\"type\": \"leaderboard\", \"data\": " + leaderboardJson + "}"); 
       System.out.println(leaderboardJson);


 
   }
 
   public void onOpen(Session session) {
     System.out.println("Client connected: " + session.getId());
     
   }
 
   @Override
   public void onClose(WebSocket conn, int code, String reason, boolean remote) { 
       System.out.println(conn + " has closed"); 
       Game game = conn.getAttachment(); 
       userSessions.remove(conn.getRemoteSocketAddress().toString()); 
       game = null;
   }
   
   @Override
   public void onMessage(WebSocket conn, String message) {  
       System.out.println("Received message: " + message);
       Game game = conn.getAttachment();
       Gson gson = new Gson(); 
       System.out.println("The Message is "+message);
 
       try {
           JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
           if (jsonObject.has("type")) {
               String messageType = jsonObject.get("type").getAsString();
               if ("username".equals(messageType)) {
                   handleUsername(conn, jsonObject.get("data").getAsString());
               } 
               else if ("chatMessage".equals(messageType)) {
                   handleChatMessage(conn, jsonObject);
               } 
               else if ("wordCheck".equals(messageType)) {
                   handleWordCheck(conn, jsonObject);
               }
               else if ("startGame".equals(messageType)) {
                   startGameForAll();
               }
               else if ("gameType".equals(messageType)) {
                   handleGameTypeSelection(conn, jsonObject);
               }
           }

       } 
       catch (JsonSyntaxException e) {
       System.err.println("Error parsing JSON message: " + e.getMessage());

       }
   }
   private void handleGameTypeSelection(WebSocket conn, JsonObject messageJson) {
   // Extract the selected game type from the message

       System.out.println("Handling gameType message...");

       int numPlayers = messageJson.get("numPlayers").getAsInt();
       String userlist = messageJson.get("userlist").getAsString();
       switch (numPlayers) {
           case 2:
           // Create a two-player game
               createGame(conn, 2, userlist);
               break;
           case 3:
           // Create a three-player game
              createGame(conn, 3, userlist);
              break;
           case 4:
           // Create a four-player game
               createGame(conn, 4, userlist);
               break;
           default:
           // Handle unsupported number of players
           break;
       }

       JsonObject message = new JsonObject();
       message.addProperty("type", "gameType");
       message.addProperty("numPlayers", numPlayers);
       message.addProperty("userlist", userlist);
 
       Gson gson = new Gson();

       // Create a new JsonObject to hold the message
       Message message1 = new Message("gameType", userlist);
       String messageJson1 = gson.toJson(message1);
       conn.send(messageJson1); 
   }


   private void createGame(WebSocket conn, int numPlayers, String userlist) {
       Game game = new Game(GameId++);
       // If the required number of players has joined, start the game
       if (game.getNumPlayers() == numPlayers) {
           System.out.println("You can start game"+ numPlayers);

           startGameForAll();
       }
       broadcastUsernames(userlist);
   }

   private void broadcastUsernames(String userlist) {

   }


    private void handleJoinGame(WebSocket conn) {
        Game game = conn.getAttachment();
        Gson gson = new Gson();
        if (game != null) {
            game.startGame(); // Start the game for the joining user
           // Broadcast the puzzle data to all connected clients
            for (WebSocket session : userSessions.values()) {
                session.send("{\"type\": \"joinGame\", \"data\": " + gson.toJson(game.getBoard()) + "}");
            }
        }
    }
 
    private void handleUsername(WebSocket conn, String username) {
        Player player = new Player(username, connectionId++, 0, 0); 
        players.add(player); 
        activeUsers.add(player);
        System.out.println(player.getPlayerUsername());
        Gson gson = new Gson();
       // Create a new JsonObject to hold the message
        Message message = new Message("activeUsersUpdate", Collections.singletonList(username));
        String messageJson = gson.toJson(message);
        conn.send(messageJson); // Send active user update to the newly connected client
   
        broadcastActiveUsers();
    }
 
 
   private void handleChatMessage(WebSocket conn, JsonObject messageJson) {
       String username;
       if (messageJson.has("username") && messageJson.get("username").isJsonPrimitive()) {
           username = messageJson.get("username").getAsString();
       } 
       else {
           username = "Unknown User";
       }
       String message = messageJson.get("message").getAsString();
 
       for (WebSocket session : userSessions.values()) {
           JsonObject chatMessage = new JsonObject();
           chatMessage.addProperty("type", "chatMessage");
           chatMessage.addProperty("username", username);
           chatMessage.addProperty("message", message);
           session.send(chatMessage.toString());
       }
   }
 
   private void handleWordCheck(WebSocket conn, JsonObject messageJson) {
       Game game = conn.getAttachment();
       String username = messageJson.get("username").getAsString(); 
       String word = messageJson.get("word").getAsString(); 
 
       System.out.println(username + " submitted word: " + word);

       if (Instant.now().isAfter(startTime.plus(gameDuration))) {
           endGame(); // End the game if it has exceeded the time limit
           return;
       }
 
       if (game.isWordInList(word.toLowerCase())) {
           int score = word.length(); 
           System.out.println("Score: " + score);
           if (scoreboard.containsKey(username)) {
               int currentScore = scoreboard.get(username);
               int updatedScore = currentScore + score;
               scoreboard.put(username, updatedScore);
           }
           else {
               scoreboard.put(username, score);
           }
           System.out.println("Username: " + username + ", Word: " + word + ", Score: " + score);
           System.out.println("Total score for " + username + ": " + scoreboard.get(username));
           puzzle.incrementFoundWords();
           puzzle.foundwords();

       } 
       else {
           System.out.println("Word is not in the wordlist, no points awarded.");
       }
 
       game.checkForWord(word.toLowerCase());
   //Update player's score----------------------------------------------------------------------------------------------------------------
      for (Player i: players){
          if(username.equals(i.getPlayerUsername()))  i.setScore((int) scoreboard.get(username));
      }
      leaderboard = new Leaderboard(players);
      System.out.println(leaderboard.toString());
      broadcastLeaderboard();
   //-------------------------------------------------------------------------------------------------------------------------------------
   }

   private void endGame() {
       System.out.println("Game ended!");

       // Calculate final scores and display a summary
       StringBuilder summary = new StringBuilder("Game Summary:\n");
       for (Player player : players) {
           summary.append(player.getPlayerUsername()).append(": ").append(player.getScore()).append(" points\n");
       }
       System.out.println(summary.toString());

       // Find the winner
       Player winner = players.get(0); // Assuming the winner is at index 0
       int highestScore = 0;
       for (Player player : players) {
           if (player.getScore() > highestScore) {
           highestScore = player.getScore();
           winner = player;
           }
       }

       // Broadcast a message to all players indicating that the game has ended
       Gson gson = new Gson();
       String endGameMessage = gson.toJson(new Message("gameEnd", "The game has ended.The winner is: " + winner.getPlayerUsername() + ".Thanks for playing!"));
       broadcast(endGameMessage);

       puzzle.displayUser();
   }

    private void broadcastLeaderboard() {
        Gson gson = new Gson();
        String leaderboardJson = gson.toJson(leaderboard);
        for (WebSocket session : userSessions.values()) {
            session.send("{\"type\": \"leaderboard\", \"data\": " + leaderboardJson + "}");
        }
    }
 
 
    private void handleLogin(WebSocket conn, String username) {
        if (!isUsernameTaken(username)) {
            Player player = new Player(username, playerIdentifier, 0, 0);
            playerIdentifier++;
            activeUsers.add(player);
            Gson gson = new Gson();
            conn.send(gson.toJson(new Message("loginSuccess", "Login successful.")));
        } 
        else {
            Gson gson = new Gson();
            conn.send(gson.toJson(new Message("loginFailure", "Username already taken.")));
        }
    }
 
    private boolean isUsernameTaken(String username) {
 
        return activeUsers.stream().anyMatch(player -> player.getPlayerUsername().equals(username));
 
    }
   
 
    private void broadcastActiveUsers() {
        Gson gson = new Gson();
        List<String> activeUsernames = new ArrayList<>();
        for (Player player : activeUsers) {
            activeUsernames.add(player.getPlayerUsername());
        }
        Message message = new Message("activeUsersUpdate", activeUsernames);
        String messageJson = gson.toJson(message);
       
        for (WebSocket session : userSessions.values()) {
            session.send(messageJson);
        }
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
    System.out.println("Server started!");
    setConnectionLostTimeout(0);
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
 
     port = 9180;
     String WSPort = System.getenv("WEBSOCKET_PORT");
     if (WSPort!=null) {
       port = Integer.valueOf(WSPort);
     }
 
     App A = new App(port);
     A.setReuseAddr(true);
     A.start();
     System.out.println("websocket Server started on port: " + port);
  
   }
 } 



