import HttpServer.core.responder.FunctionalResponder;
import HttpServer.core.router.Router;

import HttpServer.core.responder.*;
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
    private Responder okResponder;

    @Before
    public void setup() {
        String uri = "/";
        String method = "GET";
        simpleGet = new Request(uri, method);
        okResponder = new FunctionalResponder(200);
        router = new Router();
        router.defineRoute(uri, method, okResponder);
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
        router.defineRoute("/", "PUT", okResponder);
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
        Request request = new Request("/i-dont-have-this", "OPTIONS" );
        Response response = router.route(request);
        assertEquals(404, response.getStatus());
    }

    @Test
    public void itFindsResourcesWhenUriIsParameterized() {
        router.defineRoute("/parameters", "GET", okResponder);
        Request request = new Request("/parameters?key=value", "GET" );
        Response response = router.route(request);
        assertEquals(200, response.getStatus());
    }
}
