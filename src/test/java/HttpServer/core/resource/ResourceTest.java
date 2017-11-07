package HttpServer.core.resource;

import HttpServer.core.request.Request;
import HttpServer.core.response.Response;
import HttpServer.core.router.Router;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResourceTest {
    private Router router;

    // I'm going to test-drive the development of a resource that does the
    // parameters->body feature that cob spec asks for, and then see how it
    // can be generalized.

    @Before
    public void setup() {
        router = new Router();
    }

    @Test
    public void itCanRegisterItselfWithARouter() {
        Resource resource = new Resource();
        resource.setUriString("/parameters");
        resource.setAction("GET", new FunctionalHandler(200));
        resource.register(router);

        Request request = new Request("/parameters", "GET");
        Response response = router.route(request);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void itCanHaveAVerbActionSet() {
        Resource resource = new Resource();
        resource.setUriString("/parameters");
        resource.setAction("GET", new FunctionalHandler(200));
        resource.setAction("COFFEE", new FunctionalHandler(418));
        resource.register(router);

        Request getRequest = new Request("/parameters", "GET");
        Response getResponse = router.route(getRequest);
        assertEquals(200, getResponse.getStatus());

        Request coffeeRequest = new Request("/parameters", "COFFEE");
        Response coffeeResponse = router.route(coffeeRequest);
        assertEquals(418, coffeeResponse.getStatus());
    }


}
