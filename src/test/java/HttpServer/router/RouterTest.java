package HttpServer.router;

import HttpServer.definer.Handler;
import HttpServer.definer.FunctionalHandler;
import HttpServer.response.Response;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class RouterTest {

    @Test
    public void itDefinesNewRoutes() {
        Router router = new Router();

        String uri = "/";
        String method = "GET";
        Handler handler = new FunctionalHandler(200);
        router.defineRoute(uri, method, handler);

        Response response = router.route(uri, method);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void itDefinesMultipleVerbs() {
        String uri = "/form";
        String method = "POST";
        String anotherMethod = "PUT";

        Handler handler = new FunctionalHandler((request) -> new Response());
        Handler anotherHandler = new FunctionalHandler((request) -> new Response());

        Routes routes = new Routes();
        routes.define(uri, method, handler);
        routes.define(uri, anotherMethod, anotherHandler);

        Handler retrievedHandler = routes.retrieveHandler(uri, method);
        assertEquals(handler, retrievedHandler);

        Handler anotherRetrievedHandler = routes.retrieveHandler(uri, anotherMethod);
        assertEquals(anotherHandler, anotherRetrievedHandler);
    }

    @Test
    public void it404sUndefinedResources() {
        Router router = new Router();

        String uri = "/absent-resource";
        String method = "GET";

        Response response = router.route(uri, method);
        assertEquals(404, response.getStatus());
    }

    @Test
    public void it405sUndefinedMethods() {
        Router router = new Router();

        String uri = "/immutable-resource";
        String method = "GET";
        Handler handler = new FunctionalHandler(200);
        router.defineRoute(uri, method, handler);

        Response expectOk = router.route(uri, method);
        assertEquals(200, expectOk.getStatus());

        String disallowedMethod = "PUT";
        Response expectDisallowed = router.route(uri, disallowedMethod);
        assertEquals(405, expectDisallowed.getStatus());
    }
}
