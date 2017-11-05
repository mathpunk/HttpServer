package HttpServer.core.definer;
import HttpServer.core.router.Router;
import HttpServer.core.socket.MockClient;
import HttpServer.core.socket.MockTraffic;
import HttpServer.core.request.Request;
import HttpServer.core.request.RequestParser;
import HttpServer.core.response.Response;
import HttpServer.core.response.ResponseWriter;
import HttpServer.core.utility.Logger;
import HttpServer.core.utility.QuietLogger;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CobSpecUnitTests {

    private Logger logger;
    private IRouteDefiner controller;
    private Router router;

    @Before
    public void setup() {
        logger = new QuietLogger();
        controller = new CobSpecRouteDefiner();
        router = controller.getRouter();
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
    public void simplePut() throws IOException {
        MockTraffic simplePut = new MockTraffic().request(new String[]{
                "PUT /form HTTP/1.1",
                "Host: localhost:1337",
                "Accept: */*"});
        MockClient client = new MockClient();

        RequestParser parser = new RequestParser(simplePut, logger);
        Request request = parser.read();
        Response response = router.route(request);
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
        Response response = router.route(request);
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
        Response response = router.route(request);
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
        Response response = router.route(request);
        ResponseWriter writer = new ResponseWriter(client, logger);
        writer.write(response);
        String expectedStatusLine = "HTTP/1.1 405 Method Not Allowed";
        assertEquals(expectedStatusLine, client.output.get(0));
    }

    @Test
    public void itWritesARedirectStatusLine() throws IOException {
        MockTraffic putFile = new MockTraffic().request(new String[] {
                "GET /redirect HTTP/1.1"
        });
        MockClient client = new MockClient();

        RequestParser parser = new RequestParser(putFile, logger);
        Request request = parser.read();
        Response response = router.route(request);
        ResponseWriter writer = new ResponseWriter(client, logger);
        writer.write(response);
        String expectedStatusLine = "HTTP/1.1 302 Found";
        assertEquals(expectedStatusLine, client.output.get(0));
    }

    @Test
    public void itWritesARedirectLocation() throws IOException {
        MockTraffic putFile = new MockTraffic().request(new String[] {
                "GET /redirect HTTP/1.1"
        });
        MockClient client = new MockClient();

        RequestParser parser = new RequestParser(putFile, logger);
        Request request = parser.read();
        Response response = router.route(request);
        ResponseWriter writer = new ResponseWriter(client, logger);
        writer.write(response);
        String expectedLocationLine = "Location: /";
        assertEquals(expectedLocationLine, client.output.get(1));
    }

    @Test
    public void itRespondsOkToMethodOptionsOne() throws IOException {
        MockTraffic options = new MockTraffic().request(new String[] {
                "OPTIONS /method_options HTTP/1.1"
        });
        MockClient client = new MockClient();

        RequestParser parser = new RequestParser(options, logger);
        Request request = parser.read();
        Response response = router.route(request);
        ResponseWriter writer = new ResponseWriter(client, logger);
        writer.write(response);
        String expectedStatusLine = "HTTP/1.1 200 OK";
        assertEquals(expectedStatusLine, client.output.get(0));
    }

    @Test
    public void itSetsAllowHeaderForOptionsRequest() throws IOException {
        MockTraffic options = new MockTraffic().request(new String[] {
                "OPTIONS /method_options HTTP/1.1"
        });
        MockClient client = new MockClient();

        RequestParser parser = new RequestParser(options, logger);
        Request request = parser.read();
        Response response = router.route(request);
        ResponseWriter writer = new ResponseWriter(client, logger);
        writer.write(response);

        assertThat(client.output.get(1), containsString("Allow"));
    }

    @Test
    public void itFindsAllowedMethodsForMethodOptions() throws IOException {
        MockTraffic options = new MockTraffic().request(new String[] {
                "OPTIONS /method_options HTTP/1.1"
        });
        MockClient client = new MockClient();

        RequestParser parser = new RequestParser(options, logger);
        Request request = parser.read();
        Response response = router.route(request);
        ResponseWriter writer = new ResponseWriter(client, logger);
        writer.write(response);

        assertThat(client.output.get(1), containsString("GET"));
        assertThat(client.output.get(1), containsString("HEAD"));
        assertThat(client.output.get(1), containsString("POST"));
        assertThat(client.output.get(1), containsString("OPTIONS"));
        assertThat(client.output.get(1), containsString("PUT"));
    }

    @Test
    public void itFindsAllowedMethodsForMethodOptionsTwo() throws IOException {
        MockTraffic options = new MockTraffic().request(new String[] {
                "OPTIONS /method_options2 HTTP/1.1"
        });
        MockClient client = new MockClient();

        RequestParser parser = new RequestParser(options, logger);
        Request request = parser.read();
        Response response = router.route(request);
        ResponseWriter writer = new ResponseWriter(client, logger);
        writer.write(response);

        assertThat(client.output.get(1), containsString("GET"));
        assertThat(client.output.get(1), containsString("OPTIONS"));
    }

}
