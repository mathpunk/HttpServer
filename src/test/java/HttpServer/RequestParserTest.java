package HttpServer;
import org.junit.Test;
import java.io.IOException;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;

public class RequestParserTest {

    LoggerInterface logger = new TestLog();

    @Test
    public void itParsesAGetMethod() throws IOException {
        MockTraffic getRoot = new MockTraffic().request(new String[] {
                "GET / HTTP/1.1",
                "Host: localhost:1337",
                "User-Agent: curl/7.56.0",
                "Accept: */*" });
        RequestParser parser = new RequestParser(getRoot, logger);
        parser.read();
        HashMap request = parser.parse();
        assertEquals("GET", request.get("Method"));
    }

    @Test
    public void itParsesAPutMethod() throws IOException {
        MockTraffic simplePut = new MockTraffic().request(new String[] {
                "PUT /form HTTP/1.1"
        });
        RequestParser parser = new RequestParser(simplePut, logger);
        parser.read();
        HashMap request = parser.parse();
        assertEquals("PUT", request.get("Method"));
    }

    @Test
    public void itParsesTheURI() throws IOException {
        MockTraffic getRoot = new MockTraffic().request(new String[] {
                "GET / HTTP/1.1",
                "Host: localhost:1337",
                "Accept: */*" });
        RequestParser parser = new RequestParser(getRoot, logger);
        parser.read();
        HashMap request = parser.parse();
        assertEquals("/", request.get("URI"));
    }

}

