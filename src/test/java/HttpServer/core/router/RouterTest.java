import HttpServer.core.handler.FunctionalHandler;
import HttpServer.core.router.Router;
import HttpServer.core.router.Routes;

import HttpServer.core.handler.*;
import HttpServer.core.request.Request;
import HttpServer.core.response.Response;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertNotNull;

public class RouterTest {

    private Router router;
    private Request simpleGet;
    private Handler okHandler;

    @Before
    public void setup() {
        String uri = "/";
        String method = "GET";
        simpleGet = new Request(uri, method);
        okHandler = new FunctionalHandler(200);
        router = new Router();
        router.defineRoute(uri, method, okHandler);
    }

    @Test
    public void itCanDefineARoute() {
        Response response = router.route(simpleGet);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void it404sUndefinedResources() {
        Request request = new Request("/absent-resource",  "GET");
        Response response = router.route(request);
        assertEquals(404, response.getStatus());
    }

    @Test
    public void it405sUndefinedMethods() {
        String uri = "/";
        String allowedMethod = "GET";
        String disallowedMethod = "PUT";

        Request goodRequest = new Request(uri, allowedMethod);
        Request badRequest = new Request(uri, disallowedMethod);

        Response expectAllowed = router.route(goodRequest);
        assertEquals(200, expectAllowed.getStatus());
        Response expectNotAllowed = router.route(badRequest);
        assertEquals(405, expectNotAllowed.getStatus());
    }

    @Test
    public void optionsIsImplicitlyAllowed() {
        Request request = new Request("/", "OPTIONS");
        Response response = router.route(request);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void itSetsAnAllowHeaderForOptionsRequests() {
        router.defineRoute("/", "PUT", okHandler);
        Request request = new Request("/", "OPTIONS");
        Response response = router.route(request);
        String allowedMethods = response.getHeader("Allow");

        assertNotNull(allowedMethods);
        assertThat(allowedMethods, containsString("GET"));
        assertThat(allowedMethods, containsString("PUT"));
    }

    @Test
    public void it404sOptionRequestsToNonExistentResources() {
        // bug fix
        Request request = new Request("OPTIONS", "/absent-resource" );
        Response response = router.route(request);
        assertEquals(404, response.getStatus());
    }
}
