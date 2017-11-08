package HttpServer.core.request;
import HttpServer.core.utility.socket.MockTraffic;
import HttpServer.core.utility.logger.Logger;
import HttpServer.core.utility.logger.QuietLogger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class RequestParserTest {

    Logger logger = new QuietLogger();
    private MockTraffic simpleGet;
    private MockTraffic simplePut;

    @Before
    public void mockSomeTraffic() {
        simpleGet = new MockTraffic().request(new String[] {
                "GET / HTTP/1.1",
                "Host: localhost:1337"
        });
        simplePut = new MockTraffic().request(new String[] {
                "PUT /form HTTP/1.1",
                "Host: localhost:1337"
        });
    }

    @Test
    public void itSetsAndGetsTheMethod() throws IOException {
        RequestParser parsingGet = new RequestParser(simpleGet, logger);
        Request getRequest = parsingGet.read();
        assertEquals("GET", getRequest.getMethod());

        RequestParser parsingPut = new RequestParser(simplePut, logger);
        Request putRequest = parsingPut.read();
        assertEquals("PUT", putRequest.getMethod());
    }

    @Test
    public void itSetsAndGetsTheUri() throws IOException {
        RequestParser parser = new RequestParser(simpleGet, logger);
        Request request = parser.read();
        assertEquals("/", request.getUriString());
    }

    @Test
    public void itSetsAndGetsTheVersion() throws IOException {
        RequestParser parser = new RequestParser(simpleGet, logger);
        Request request = parser.read();
        assertEquals("HTTP/1.1", request.getVersion());
    }

    @Test
    public void itSetsAndGetsHeaders() throws IOException {
        RequestParser parser = new RequestParser(simpleGet, logger);
        Request request = parser.read();
        assertEquals("localhost:1337", request.getHeader("Host"));
    }
}

