package HttpServer.definer;

import HttpServer.request.Request;
import HttpServer.response.Response;
import HttpServer.router.Router;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class RedirectDefinerTest {

    private Request request;
    private Router router;

    @Before
    public void setup() {
        HashMap<String, String> redirectionMap = new HashMap<>();
        redirectionMap.put("/redirect", "/");
        Router blankRouter = new Router();
        RedirectDefiner definer = new RedirectDefiner(blankRouter, redirectionMap);
        this.router = definer.getRouter();

        this.request = new Request();
        request.setUri("/redirect");
        request.setMethod("GET");

    }

    @Test
    public void itHasStatusCode302() {
        Response response = router.route(request);
        assertEquals(302, response.getStatus());
    }

    @Test
    public void itSetsLocation() {
        Response response = router.route(request);
        assertEquals("/", response.getHeader("Location"));
    }

}
