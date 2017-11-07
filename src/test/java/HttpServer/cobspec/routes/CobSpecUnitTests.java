package HttpServer.cobspec.routes;
import HttpServer.core.definer.*;
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
import java.util.HashMap;
import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CobSpecUnitTests {

    private Logger logger;
    private IRouteDefiner definer;
    private Router router;
    private Function runner;

    @Before
    public void setup() {
        logger = new QuietLogger();
        definer = new IRouteDefiner() {
            private final Router router;

            {
                Router blankRouter = new Router();
                FileRouteDefiner fileDefiner = new FileRouteDefiner("./cob_spec/public", blankRouter);
                TeaRouteDefiner teaDefiner = new TeaRouteDefiner(fileDefiner.getRouter());
                HashMap<String, String> redirectionMap = new HashMap<String, String>();
                redirectionMap.put("/redirect", "/");
                RedirectDefiner redirectDefiner = new RedirectDefiner(teaDefiner.getRouter(), redirectionMap);
                this.router = redirectDefiner.getRouter();

                // Options tests are a concern of the router, in my opinion. Not set with a definer.
                Handler okHandler = new FunctionalHandler(200);
                router.defineRoute("/method_options", "GET", okHandler);
                router.defineRoute("/method_options", "HEAD", okHandler);
                router.defineRoute("/method_options", "POST", okHandler);
                router.defineRoute("/method_options", "OPTIONS", okHandler);
                router.defineRoute("/method_options", "PUT", okHandler);
                router.defineRoute("/method_options2", "GET", okHandler);
                router.defineRoute("/method_options2", "OPTIONS", okHandler);
                serveRoot();
                serveForm();
            }

            @Override
            public Router getRouter() {
                return router;
            }

            private void serveForm() {
                router.defineRoute("/form", "PUT", new FunctionalHandler(200));
            }

            private void serveRoot() {
                router.defineRoute("/", "GET", new FunctionalHandler(200));
                router.defineRoute("/", "HEAD", new FunctionalHandler(200));
            }
        };
        router = definer.getRouter();
        runner = (requestLine) -> {
            MockTraffic simpleGet = new MockTraffic().request(new String[]{
                    (String) requestLine,
                    "Host: localhost:1337",
                    "Accept: */*"});
            MockClient client = new MockClient();
            RequestParser parser = new RequestParser(simpleGet, logger);
            Request request = null;
            try {
                request = parser.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Response response = router.route(request);
            ResponseWriter writer = new ResponseWriter(client, logger);
            try {
                writer.write(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return client;
        };
    }

    @Test
    public void simpleGetReturnsOk() throws IOException {
        MockClient client = (MockClient) runner.apply("GET / HTTP/1.1");
        String expectation = "HTTP/1.1 200 OK";
        assertEquals(client.output.get(0), expectation);
    }

    @Test
    public void faviconNotFound() throws IOException {
        String requestLine =  "GET /favicon.ico HTTP/1.1";
        MockClient client = (MockClient) runner.apply(requestLine);
        String expectation = "HTTP/1.1 404 Not Found";
        assertEquals(client.output.get(0), expectation);
    }

    @Test
    public void simplePut() throws IOException {
        String requestLine =  "PUT /form HTTP/1.1";
        MockClient client = (MockClient) runner.apply(requestLine);
        String expectation = "HTTP/1.1 200 OK";
        assertEquals(expectation, client.output.get(0));
    }

    @Test
    public void teaIsOkay() throws IOException {
        String requestLine = "GET /tea HTTP/1.1";
        MockClient client = (MockClient) runner.apply(requestLine);
        String expectation = "HTTP/1.1 200 OK";
        assertEquals(client.output.get(0), expectation);
    }

    @Test
    public void isATeapot() throws IOException {
        String requestLine = "GET /coffee HTTP/1.1";
        String expectedStatusLine = "HTTP/1.1 418 I'm a teapot";
        String expectedBody = "I'm a teapot";
        MockClient client = (MockClient) runner.apply(requestLine);
        assertEquals(expectedStatusLine, client.output.get(0));
        assertEquals(3, client.output.size());
        assertEquals(expectedBody, client.output.get(2));
    }

    @Test
    public void simpleHeadReturnsOk() throws IOException {
        String requestLine = "HEAD / HTTP/1.1";
        MockClient client = (MockClient) runner.apply(requestLine);
        String expectedStatusLine = "HTTP/1.1 200 OK";
        assertEquals(expectedStatusLine, client.output.get(0));
    }

    @Test
    public void getMockFileAllowed() throws IOException {
        String requestLine = "GET /file1 HTTP/1.1";
        MockClient client = (MockClient) runner.apply(requestLine);
        String expectedStatusLine = "HTTP/1.1 200 OK";
        assertEquals(expectedStatusLine, client.output.get(0));
    }

    @Test
    public void putMockFileDisallowed() throws IOException {
        String requestLine = "PUT /file1 HTTP/1.1";
        MockClient client = (MockClient) runner.apply(requestLine);
        String expectedStatusLine = "HTTP/1.1 405 Method Not Allowed";
        assertEquals(expectedStatusLine, client.output.get(0));
    }

    @Test
    public void itWritesARedirectStatusLine() throws IOException {
        String requestLine = "GET /redirect HTTP/1.1";
        MockClient client = (MockClient) runner.apply(requestLine);
        String expectedStatusLine = "HTTP/1.1 302 Found";
        assertEquals(expectedStatusLine, client.output.get(0));
    }

    @Test
    public void itWritesARedirectLocation() throws IOException {
        String requestLine = "GET /redirect HTTP/1.1";
        MockClient client = (MockClient) runner.apply(requestLine);
        String expectedLocationLine = "Location: /";
        assertEquals(expectedLocationLine, client.output.get(1));
    }

    @Test
    public void itRespondsOkToMethodOptionsOne() throws IOException {
        String requestLine = "OPTIONS /method_options HTTP/1.1";
        MockClient client = (MockClient) runner.apply(requestLine);
        String expectedStatusLine = "HTTP/1.1 200 OK";
        assertEquals(expectedStatusLine, client.output.get(0));
    }

    @Test
    public void itSetsAllowHeaderForOptionsRequest() throws IOException {
        String requestLine = "OPTIONS /method_options HTTP/1.1";
        MockClient client = (MockClient) runner.apply(requestLine);
        assertThat(client.output.get(1), containsString("Allow"));
    }

    @Test
    public void itFindsAllowedMethodsForMethodOptions() throws IOException {
        String requestLine = "OPTIONS /method_options HTTP/1.1";
        MockClient client = (MockClient) runner.apply(requestLine);
        assertThat(client.output.get(1), containsString("GET"));
        assertThat(client.output.get(1), containsString("HEAD"));
        assertThat(client.output.get(1), containsString("POST"));
        assertThat(client.output.get(1), containsString("OPTIONS"));
        assertThat(client.output.get(1), containsString("PUT"));
    }

    @Test
    public void itFindsAllowedMethodsForMethodOptionsTwo() throws IOException {
        String requestLine = "OPTIONS /method_options2 HTTP/1.1";
        MockClient client = (MockClient) runner.apply(requestLine);
        assertThat(client.output.get(1), containsString("GET"));
        assertThat(client.output.get(1), containsString("OPTIONS"));
    }

}
