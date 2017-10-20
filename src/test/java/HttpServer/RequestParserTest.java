package HttpServer;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class RequestParserTest {

    LoggerInterface logger = new TestLog();
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
    public void itParsesTheMethod() throws IOException {
        RequestParser parsingGet = new RequestParser(simpleGet, logger);
        Request getRequest = parsingGet.read();
        assertEquals("GET", getRequest.getMethod());

        RequestParser parsingPut = new RequestParser(simplePut, logger);
        Request putRequest = parsingPut.read();
        assertEquals("PUT", putRequest.getMethod());
    }

    @Test
    public void itParsesTheURI() throws IOException {
        RequestParser parser = new RequestParser(simpleGet, logger);
        Request request = parser.read();
        assertEquals("/", request.getUri());
    }

}

