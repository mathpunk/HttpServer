package HttpServer;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
public class ResponseWriterTest {

    private LoggerInterface logger;

    @Before
    public void setup() {
        this.logger = new TestLog();
    }

    @Test
    public void itIsOkWithSimpleGet() throws IOException {
        MockTraffic getRoot = new MockTraffic().request(new String[] {
                "GET / HTTP/1.1",
                "Host: localhost:1337",
                "Accept: */*" });
        RequestParser parser = new RequestParser(getRoot, logger);
        MockClient client = new MockClient();
        ResponseWriter writer = new ResponseWriter(parser, client, logger);

        parser.readFromClient();
        writer.write();
        String expectation = "HTTP/1.1 200 OK";
        assert(client.received(expectation));
    }

    @Test
    public void itIsNotFoundForFavicon() throws IOException {
        MockTraffic getFavicon = new MockTraffic().request(new String[] {
                "GET /favicon.ico HTTP/1.1",
                "Host: localhost:1337",
                "Accept: */*"
        });
        RequestParser parser = new RequestParser(getFavicon, logger);
        MockClient client = new MockClient();
        ResponseWriter writer = new ResponseWriter(parser, client, logger);

        parser.readFromClient();
        writer.write();
        String expectation = "HTTP/1.1 404 Not Found";
        assert(client.received(expectation));
    }

    @Test
    public void teaIsOkay() throws IOException {
        MockTraffic teaForTwo = new MockTraffic().request(new String[] {
                "GET /tea HTTP/1.1"
        });
        RequestParser parser = new RequestParser(teaForTwo, logger);
        MockClient client = new MockClient();
        ResponseWriter writer = new ResponseWriter(parser, client, logger);

        parser.readFromClient();
        writer.write();
        String expectation = "HTTP/1.1 200 OK";
        assert(client.received(expectation));
    }

    @Test
    public void isATeapot() throws IOException {
        MockTraffic coffeePlz = new MockTraffic().request(new String[] {
                "GET /coffee HTTP/1.1"
        });
        RequestParser parser = new RequestParser(coffeePlz, logger);
        MockClient client = new MockClient();
        ResponseWriter writer = new ResponseWriter(parser, client, logger);

        parser.readFromClient();
        writer.write();
        String statusExpectation = "HTTP/1.1 418";
        assert(client.received(statusExpectation));
        String bodyExpectation = "I'm a teapot";
        assert(client.received(bodyExpectation));
    }

}
