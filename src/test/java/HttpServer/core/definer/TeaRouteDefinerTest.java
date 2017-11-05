package HttpServer.core.definer;

import HttpServer.core.request.Request;
import HttpServer.core.response.Response;
import HttpServer.core.router.Router;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TeaRouteDefinerTest {
    private TeaRouteDefiner definer;

    @Before
    public void setup() {
        Router router = new Router();
        this.definer = new TeaRouteDefiner(router);
    }

    @Test
    public void itSetsATeaRoute() {
        Router router = definer.getRouter();

        Request request = new Request();
        request.setUri("/tea");
        request.setMethod("GET");

        Response response = router.route(request);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void itSetsACoffeeRoute() {
        Router router = definer.getRouter();

        Request request = new Request();
        request.setMethod("GET");
        request.setUri("/coffee");


        Response response = router.route(request);
        assertEquals(418, response.getStatus());
    }
}