package HttpServer.definer;

import HttpServer.request.Request;
import HttpServer.response.Response;
import HttpServer.router.Router;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

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

        // Let's make sure that parsed and set Requests both get routed
        ArrayList<String> requestInput = new ArrayList<>();
        requestInput.add("GET /coffee HTTP/1.1");
        Request request = new Request(requestInput);

        Response response = router.route(request);
        assertEquals(418, response.getStatus());
    }
}
