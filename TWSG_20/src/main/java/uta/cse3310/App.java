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

public class App extends WebSocketServer {

  
  private Vector<Game> activeGames = new Vector<Game>();

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
      conn.send(puzzleJson);
      
  }

  @Override
  public void onClose(WebSocket conn, int code, String reason, boolean remote) { 
      System.out.println(conn + " has closed"); 
      Game game = conn.getAttachment();
      game = null;
    
  }

  @Override
  public void onMessage(WebSocket conn, String message) { 
   
    
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