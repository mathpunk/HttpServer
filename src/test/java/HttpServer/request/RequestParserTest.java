package HttpServer.request;
import HttpServer.request.Request;
import HttpServer.request.RequestParser;
import HttpServer.socket.MockTraffic;
import HttpServer.utility.Logger;
import HttpServer.utility.QuietLogger;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class RequestParserTest {

    Logger logger = new QuietLogger();
    private MockTraffic simpleGet;
    private MockTraffic simplePut;

    @Before
    public void mockSomeTraffic() {
        simpleGet = new MockTraffic().request(new String[] {
                "GET / HTTP/1.1",
                "Host: localhost:1337",
                "Accept: */*" });
        simplePut = new MockTraffic().request(new String[] {
                "PUT /form HTTP/1.1",
                "Host: localhost:1337",
                "Accept: */*"
        });
    }

    @Test
    public void itCreatesARequestWithCorrectMethod() throws IOException {
        RequestParser parsingGet = new RequestParser(simpleGet, logger);
        Request getRequest = parsingGet.read();
        assertEquals("GET", getRequest.getMethod());

        RequestParser parsingPut = new RequestParser(simplePut, logger);
        Request putRequest = parsingPut.read();
        assertEquals("PUT", putRequest.getMethod());
    }

    @Test
    public void itCreatesARequestWithCorrectUri() throws IOException {
        RequestParser parser = new RequestParser(simpleGet, logger);
        Request request = parser.read();
        assertEquals("/", request.getUri());
    }

    @Test
    public void itCreatesARequestWithCorrectVersion() throws IOException {
        RequestParser parser = new RequestParser(simpleGet, logger);
        Request request = parser.read();
        assertEquals("HTTP/1.1", request.getVersion());
    }
}

