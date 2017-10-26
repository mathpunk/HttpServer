package HttpServer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class CobSpecUnitTests {

    private LoggerInterface logger;
    private Router router;

    @Before
    public void setup() {
        logger = new TestLog();
        Routes routes = new Routes();
        router = new Router(routes);
        router.defineRoute("/", "GET", new Response().setStatus(200));
        router.defineRoute("/tea", "GET", new Response().setStatus(200));
        router.defineRoute("/coffee", "GET", new Response().setStatus(418));
    }

    @Test
    public void simpleGetReturnsOk() throws IOException {
        MockTraffic simpleGet = new MockTraffic().request(new String[]{
                "GET / HTTP/1.1",
                "Host: localhost:1337",
                "Accept: */*"});
        MockClient client = new MockClient();

        RequestParser parser = new RequestParser(simpleGet, logger);
        Request request = parser.read();
        Response response = router.route(request);
        ResponseWriter writer = new ResponseWriter(client, logger);
        writer.write(response);
        String expectation = "HTTP/1.1 200 OK";
        assertEquals(client.output.get(0), expectation);
    }

    @Test
    public void faviconNotFound() throws IOException {
        MockTraffic getFavicon = new MockTraffic().request(new String[]{
                "GET /favicon.ico HTTP/1.1",
                "Host: localhost:1337",
                "Accept: */*"});
        MockClient client = new MockClient();

        RequestParser parser = new RequestParser(getFavicon, logger);
        Request request = parser.read();
        Response response = router.route(request);
        ResponseWriter writer = new ResponseWriter(client, logger);
        writer.write(response);
        String expectation = "HTTP/1.1 404 Not Found";
        assertEquals(client.output.get(0), expectation);
    }

    @Test
    public void teaIsOkay() throws IOException {
        MockTraffic teaForTwo = new MockTraffic().request(new String[] {
                "GET /tea HTTP/1.1"
        });
        MockClient client = new MockClient();

        RequestParser parser = new RequestParser(teaForTwo, logger);
        Request request = parser.read();
        Response response = router.route(request);
        ResponseWriter writer = new ResponseWriter(client, logger);
        writer.write(response);
        String expectation = "HTTP/1.1 200 OK";
        assertEquals(client.output.get(0), expectation);
    }

    @Test
    public void isATeapot() throws IOException {
        MockTraffic coffeePlz = new MockTraffic().request(new String[] {
                "GET /coffee HTTP/1.1"
        });
        MockClient client = new MockClient();

        RequestParser parser = new RequestParser(coffeePlz, logger);
        Request request = parser.read();
        Response response = router.route(request);
        ResponseWriter writer = new ResponseWriter(client, logger);
        writer.write(response);
        String expectedStatusLine = "HTTP/1.1 418 I'm a teapot";
        assertEquals(expectedStatusLine, client.output.get(0));
    }

//    @Test
//    public void simpleHeadReturnsOk() throws IOException {
//        MockTraffic simpleHead = new MockTraffic().request(new String[]{
//                "HEAD / HTTP/1.1",
//                "Host: localhost:1337",
//                "Accept: */*"});
//        MockClient client = new MockClient();
//
//        RequestParser parser = new RequestParser(simpleHead, logger);
//        Request request = parser.read();
//        Response response = controller.respond(request);
//
//        ResponseWriter writer = new ResponseWriter(response, client, logger);
//        writer.write();
//
//        String expectation = "HTTP/1.1 200 OK";
//        assert (client.received(expectation));
//    }
//
//
}
