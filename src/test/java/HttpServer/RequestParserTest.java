package HttpServer;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class RequestParserTest {

    @Test
    public void smokeTest() {
        assertEquals(1 + 1, 2);
    }

/*    @Test
    public void itParsesSomeLines() throws IOException {
        MockTraffic traffic = new MockTraffic();
        traffic.emulateCurl();
        RequestParser parser = new RequestParser(traffic);
        parser.read();
        ArrayList<String> requestLines = parser.compiledRequest();
        assertTrue(requestLines.size() > 0);
    }

    @Test
    public void itParsesAllTheLines() throws IOException {
        MockTraffic traffic = new MockTraffic();
        traffic.emulateCurl();
        RequestParser parser = new RequestParser(traffic);
        parser.read();
        ArrayList<String> requestLines = parser.compiledRequest();
        assertTrue(requestLines.size() == 4);
    }*/


}

/*

        import static org.hamcrest.CoreMatchers.containsString;
        import static org.junit.Assert.*;

public class MockSynchronousServerTest {
    @Test
    public void itReadsTheMethod() {
        int port = 5000;
        MockSynchronousServer server = new MockSynchronousServer(port);
        server.simulateGoodRequest();
        String requestLine = server.readLine();
        assertThat(requestLine, containsString("GET"));
    }

    @Test
    public void itReadsTheHttpVersion() {
        int port = 5000;
        MockSynchronousServer server = new MockSynchronousServer(port);
        server.simulateGoodRequest();
        String requestLine = server.readLine();
        assertThat(requestLine, containsString("HTTP/1.1"));
    }

    @Test
    public void itReadsTheHost() {
        int port = 5000;
        MockSynchronousServer server = new MockSynchronousServer(port);
        server.simulateGoodRequest();
        String requestLine = server.readLine();
        String headerLine = server.readLine();
        assertThat(headerLine, containsString("Host:"));
    }

    @Test
    public void itWrites() {
        int port = 5000;
        MockSynchronousServer server = new MockSynchronousServer(port);
        server.simulateGoodRequest();
        server.readLine();
        server.readLine();
        server.readLine();
        server.readLine();
        String statusLine = "HTTP/1.1 200 OK";
        server.writeLine(statusLine);
        String sent = (String) server.connection.bufferOut.get(0);
        assertEquals(statusLine, sent);
    }
}*/
