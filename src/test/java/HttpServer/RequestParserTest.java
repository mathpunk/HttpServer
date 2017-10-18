package HttpServer;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class RequestParserTest {

    @Test
    public void itParsesAGetMethod() throws IOException {
        MockTraffic traffic = new MockTraffic();
        traffic.emulateSimpleCurl();
        RequestParser parser = new RequestParser(traffic);
        parser.read();

        HashMap request = parser.parse();
        assertEquals("GET", request.get("Method"));
    }

    @Ignore
    public void itParsesAPutMethod() throws IOException {
        MockTraffic traffic = new MockTraffic();
        traffic.emulateSimplePut();
        RequestParser parser = new RequestParser(traffic);
        parser.read();

        HashMap request = parser.parse();
        assertEquals("PUT", request.get("Method"));
    }

    @Test
    public void itParsesTheURI() throws IOException {
        MockTraffic traffic = new MockTraffic();
        traffic.emulateSimpleCurl();
        RequestParser parser = new RequestParser(traffic);
        parser.read();

        HashMap request = parser.parse();
        assertEquals("/", request.get("URI"));
    }

}

