package HttpServer;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class MockSynchronousServerTest {
    @Test
    public void itConnects() {
        MockSynchronousServer server = new MockSynchronousServer(5000);
        server.nextConnection();
        assertNotNull(server);
    }

    @Test
    public void itTakesAPort() {
        int port = 5000;
        MockSynchronousServer server = new MockSynchronousServer(port);
        assertNotNull(server);
    }

    @Test
    public void itReadsTheMethod() {
        int port = 5000;
        MockSynchronousServer server = new MockSynchronousServer(port);
        String requestLine = server.readLine();
        assertThat(requestLine, containsString("GET"));
    }

    @Test
    public void itReadsTheHttpVersion() {
        int port = 5000;
        MockSynchronousServer server = new MockSynchronousServer(port);
        String requestLine = server.readLine();
        assertThat(requestLine, containsString("HTTP/1.1"));
    }

    @Test
    public void itReadsTheHost() {
        int port = 5000;
        MockSynchronousServer server = new MockSynchronousServer(port);
        String requestLine = server.readLine();
        String headerLine = server.readLine();
        assertThat(headerLine, containsString("Host:"));
    }
}
