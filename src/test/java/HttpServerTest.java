
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Scanner;

import static org.junit.Assert.*;
import uta.cse3310.HttpServer;

public class HttpServerTest {

    private static final int PORT = 8080;
    private static final String HTML_DIRECTORY = "./html";
    private HttpServer httpServer;
    private Thread serverThread;

    @Before
    public void setUp() {
        httpServer = new HttpServer(PORT, HTML_DIRECTORY);
        serverThread = new Thread(() -> httpServer.start());
        serverThread.start();
    }

    @After
    public void tearDown() {
        serverThread.interrupt();
    }

    @Test
    public void testServerStart() {
        assertTrue(serverThread.isAlive());
    }

    
}
