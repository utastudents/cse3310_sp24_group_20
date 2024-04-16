package uta.cse3310;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import net.freeutils.httpserver.HTTPServer;
import net.freeutils.httpserver.HTTPServer.ContextHandler;
import net.freeutils.httpserver.HTTPServer.FileContextHandler;
import net.freeutils.httpserver.HTTPServer.Request;
import net.freeutils.httpserver.HTTPServer.Response;
import net.freeutils.httpserver.HTTPServer.VirtualHost;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// http server include is a GPL licensed package from
//            http://www.freeutils.net/source/jlhttp/

public class HttpServer {

    private static final String HTML = "./html";
    int port = 8080;
    String dirname = HTML;
    private Set<Player> activeUsers = new HashSet<>();

    public HttpServer(int portNum, String dirName) {
        port = portNum;
        dirname = dirName;
    }

    public void start() {
        try {
            File dir = new File(dirname);
            if (!dir.canRead())
                throw new FileNotFoundException(dir.getAbsolutePath());
            // set up server
            HTTPServer server = new HTTPServer(port);
            VirtualHost host = server.getVirtualHost(null); // default host
            host.setAllowGeneratedIndex(true); // with directory index pages
            host.addContext("/", new FileContextHandler(dir));
            host.addContext("/api/time", new ContextHandler() {
                public int serve(Request req, Response resp) throws IOException {
                    long now = System.currentTimeMillis();
                    resp.getHeaders().add("Content-Type", "text/plain");
                    resp.send(200, String.format("%tF %<tT", now));
                    return 0;
                }
            });

            host.addContext("/active-users", new ContextHandler() {
                public int serve(Request req, Response resp) throws IOException {
                    StringBuilder activeUserNames = new StringBuilder();
                    for (Player player : activeUsers) {
                        activeUserNames.append(player.getPlayerUsername()).append(", ");
                    }
                    // Remove the trailing comma and space
                    if (activeUserNames.length() > 0) {
                        activeUserNames.setLength(activeUserNames.length() - 2);
                    }


                    String leaderboardJson = generateLeaderboardJson(); // Assuming you have a method to generate JSON for the leaderboard
                    String response = "Active Users: " + activeUserNames + "\nLeaderboard: " + leaderboardJson;
                      resp.getHeaders().add("Content-Type", "text/plain");
                     resp.send(200, response);
                         return 0;
                         }
                         });

            // Add a new context for serving leaderboard data
            host.addContext("/leaderboard", new ContextHandler() {
                public int serve(Request req, Response resp) throws IOException {
                    // Assuming you have a method to retrieve leaderboard data
                    String leaderboardData = getLeaderboardData();
                    resp.getHeaders().add("Content-Type", "application/json");
                    resp.send(200, leaderboardData);
                    return 0;
                }
            });



            server.start();
        } catch (Exception e) {
            System.err.println("error: " + e);
        }

    }

    private String getLeaderboardData() {
        return "{\"leaderboard\": [{\"player\": \"Player1\", \"score\": 100}, {\"player\": \"Player2\", \"score\": 80}]}";
    }
    private List<Player> players = new ArrayList<>();
    private String generateLeaderboardJson() {
        JSONArray leaderboardArray = new JSONArray();
    
        // Assuming you have a list of players
        for (Player player : players) {
            JSONObject playerJson = new JSONObject();
            playerJson.put("username", player.getPlayerUsername());
            playerJson.put("score", player.getScore());
            // Add more player data as needed
    
            leaderboardArray.put(playerJson);
        }
    
        return leaderboardArray.toString();
    }

}