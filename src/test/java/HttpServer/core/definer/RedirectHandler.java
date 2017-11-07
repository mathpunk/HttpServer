//package HttpServer.core.definer;
//
//import HttpServer.core.request.Request;
//import HttpServer.core.response.Response;
//import HttpServer.core.router.Router;
//import org.junit.Before;
//import org.junit.Test;
//
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
//        RedirectDefiner definer = new RedirectDefiner(blankRouter, redirectionMap);
//        this.router = definer.getRouter();
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
//}
