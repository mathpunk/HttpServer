package HttpServer;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class RequestParserTest {

    @Test
    public void smokeTest() {
        assertEquals(1 + 1, 2);
    }

    @Test
    public void itParsesTheMethod() throws IOException {
        MockTraffic traffic = new MockTraffic();
        traffic.emulateSimpleCurl();
        RequestParser parser = new RequestParser(traffic);
        parser.read();
        HashMap request = parser.request();
        assertEquals("GET", request.get("Method"));
    }

    @Test
    public void itParsesThePath() throws IOException {
        MockTraffic traffic = new MockTraffic();
        traffic.emulateSimpleCurl();
        RequestParser parser = new RequestParser(traffic);
        parser.read();
        HashMap request = parser.request();
        assertEquals("/", request.get("URL"));
    }

}

