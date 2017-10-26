package HttpServer;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
public class CobSpecUnitTest {

    private LoggerInterface logger;
    private MockTraffic simpleGet;
    private Controller controller;


    @Before
    public void setup() {
        logger = new TestLog();
        simpleGet = new MockTraffic().request(new String[]{
                "GET / HTTP/1.1",
                "Host: localhost:1337",
                "Accept: */*"});
        controller = new Controller();
    }

    @Test
    public void simpleGetReturnsOk() throws IOException {
        RequestParser parser = new RequestParser(simpleGet, logger);
        Request request = parser.read();
        Response response = controller.respond(request);

        MockClient client = new MockClient();
        ResponseWriter writer = new ResponseWriter(response, client, logger);

        writer.write();
        String expectation = "HTTP/1.1 200 OK";
        assert (client.received(expectation));
    }

    @Test
    public void itIsNotFoundForFavicon() throws IOException {
        MockTraffic getFavicon = new MockTraffic().request(new String[] {
                "GET /favicon.ico HTTP/1.1",
                "Host: localhost:1337",
                "Accept: */*"
        });

        RequestParser parser = new RequestParser(getFavicon, logger);
        Request request = parser.read();
        Response response = controller.respond(request);

        MockClient client = new MockClient();
        ResponseWriter writer = new ResponseWriter(response, client, logger);

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
        Request request = parser.read();
        Response response = controller.respond(request);

        MockClient client = new MockClient();
        ResponseWriter writer = new ResponseWriter(response, client, logger);

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
        Request request = parser.read();
        Response response = controller.respond(request);

        MockClient client = new MockClient();
        ResponseWriter writer = new ResponseWriter(response, client, logger);

        writer.write();

        String statusExpectation = "HTTP/1.1 418";
        assert(client.received(statusExpectation));
        String bodyExpectation = "I'm a teapot";
        assert(client.received(bodyExpectation));
    }

    @Test
    public void simpleHeadReturnsOk() throws IOException {
        MockTraffic simpleHead = new MockTraffic().request(new String[]{
                "HEAD / HTTP/1.1",
                "Host: localhost:1337",
                "Accept: */*"});

        RequestParser parser = new RequestParser(simpleHead, logger);
        Request request = parser.read();
        Response response = controller.respond(request);

        MockClient client = new MockClient();
        ResponseWriter writer = new ResponseWriter(response, client, logger);

        writer.write();
        String expectation = "HTTP/1.1 200 OK";
        assert (client.received(expectation));
    }


}
