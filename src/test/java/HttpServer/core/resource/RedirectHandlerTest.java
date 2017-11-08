//package HttpServer.core.definer;
//
//import HttpServer.core.request.Request;
//import HttpServer.core.response.Response;
//import HttpServer.core.router.Router;
//import MockClient;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.util.HashMap;
//
//import static org.junit.Assert.assertEquals;
//
//public class RedirectHandlerTest {
//
//    private Request request;
//    private Router router;
//
//    @Before
//    public void setup() {
//        HashMap<String, String> redirectionMap = new HashMap<>();
//        redirectionMap.put("/redirect", "/");
//        Router blankRouter = new Router();
//        RedirectDefiner resource = new RedirectDefiner(blankRouter, redirectionMap);
//        this.router = resource.getRouter();
//
//        this.request = new Request();
//        request.setUri("/redirect");
//        request.setMethod("GET");
//    }
//
//    @Test
//    public void itHasStatusCode302() {
//        Response response = router.route(request);
//        assertEquals(302, response.getStatus());
//    }
//
//    @Test
//    public void itSetsLocation() {
//        Response response = router.route(request);
//        assertEquals("/", response.getHeader("Location"));
//    }
//
//
//import org.junit.Test;
//
//@Test
//public void itWritesARedirectStatusLine() throws IOException {
//        String requestLine = "GET /redirect HTTP/1.1";
//        MockClient client = (MockClient) runner.apply(requestLine);
//        String expectedStatusLine = "HTTP/1.1 302 Found";
//        assertEquals(expectedStatusLine, client.output.get(0));
//        }
//
//@Test
//public void itWritesARedirectLocation() throws IOException {
//        String requestLine = "GET /redirect HTTP/1.1";
//        MockClient client = (MockClient) runner.apply(requestLine);
//        String expectedLocationLine = "Location: /";
//        assertEquals(expectedLocationLine, client.output.get(1));
//        }
//
//}
