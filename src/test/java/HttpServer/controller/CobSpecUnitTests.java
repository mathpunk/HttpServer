package HttpServer.controller;
import HttpServer.socket.MockClient;
import HttpServer.socket.MockTraffic;
import HttpServer.request.Request;
import HttpServer.request.RequestParser;
import HttpServer.response.Response;
import HttpServer.response.ResponseWriter;
import HttpServer.utility.Logger;
import HttpServer.utility.QuietLogger;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class CobSpecUnitTests {

    private Logger logger;
    private Controller controller;

    @Before
    public void setup() {
        logger = new QuietLogger();
        controller = new Controller();
        controller.init();
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
        Response response = controller.route(request);
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
        Response response = controller.route(request);
        ResponseWriter writer = new ResponseWriter(client, logger);
        writer.write(response);
        String expectation = "HTTP/1.1 404 Not Found";
        assertEquals(client.output.get(0), expectation);
    }

    @Test
    public void simplePut() throws IOException {
        MockTraffic simplePut = new MockTraffic().request(new String[]{
                "PUT /form HTTP/1.1",
                "Host: localhost:1337",
                "Accept: */*"});
        MockClient client = new MockClient();

        RequestParser parser = new RequestParser(simplePut, logger);
        Request request = parser.read();
        Response response = controller.route(request);
        ResponseWriter writer = new ResponseWriter(client, logger);
        writer.write(response);
        String expectation = "HTTP/1.1 200 OK";
        assertEquals(expectation, client.output.get(0));
    }

    @Test
    public void teaIsOkay() throws IOException {
        MockTraffic teaForTwo = new MockTraffic().request(new String[] {
                "GET /tea HTTP/1.1"
        });
        MockClient client = new MockClient();

        RequestParser parser = new RequestParser(teaForTwo, logger);
        Request request = parser.read();
        Response response = controller.route(request);
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
        Response response = controller.route(request);
        ResponseWriter writer = new ResponseWriter(client, logger);
        writer.write(response);
        String expectedStatusLine = "HTTP/1.1 418 I'm a teapot";
        String expectedBody = "I'm a teapot";
        assertEquals(expectedStatusLine, client.output.get(0));
        assertEquals(3, client.output.size());
        assertEquals(expectedBody, client.output.get(2));
    }

    @Test
    public void simpleHeadReturnsOk() throws IOException {
        MockTraffic simpleHead = new MockTraffic().request(new String[] {
                "HEAD / HTTP/1.1"
        });
        MockClient client = new MockClient();

        RequestParser parser = new RequestParser(simpleHead, logger);
        Request request = parser.read();
        Response response = controller.route(request);
        ResponseWriter writer = new ResponseWriter(client, logger);
        writer.write(response);
        String expectedStatusLine = "HTTP/1.1 200 OK";
        assertEquals(expectedStatusLine, client.output.get(0));
    }

    @Test
    public void getMockFileAllowed() throws IOException {
        // Replace later with test for file resource
        MockTraffic getFile = new MockTraffic().request(new String[] {
                "GET /file1 HTTP/1.1"
        });
        MockClient client = new MockClient();

        RequestParser parser = new RequestParser(getFile, logger);
        Request request = parser.read();
        Response response = controller.route(request);
        ResponseWriter writer = new ResponseWriter(client, logger);
        writer.write(response);
        String expectedStatusLine = "HTTP/1.1 200 OK";
        assertEquals(expectedStatusLine, client.output.get(0));
    }

    @Test
    public void putMockFileDisallowed() throws IOException {
        // Replace later with test for file resource
        MockTraffic putFile = new MockTraffic().request(new String[] {
                "PUT /file1 HTTP/1.1"
        });
        MockClient client = new MockClient();

        RequestParser parser = new RequestParser(putFile, logger);
        Request request = parser.read();
        Response response = controller.route(request);
        ResponseWriter writer = new ResponseWriter(client, logger);
        writer.write(response);
        String expectedStatusLine = "HTTP/1.1 405 Method Not Allowed";
        assertEquals(expectedStatusLine, client.output.get(0));
    }
}
