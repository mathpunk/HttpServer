package HttpServer;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThat;

public class RouterTest {

    @Test
    public void itDefinesNewRoutes() {
        Routes routes = new Routes();
        Router router = new Router(routes);

        String uri = "/";
        String method = "GET";
        RequestHandler handler = new RequestHandler(
               (request) -> new Response().setStatus(200)
        );
        router.defineRoute(uri, method, handler);

        Response response = router.route(uri, method);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void it404sUndefinedResources() {
        Routes routes = new Routes();
        Router router = new Router(routes);

        String uri = "/absent-resource";
        String method = "GET";
        RequestHandler handler = new RequestHandler(
                (request) -> new Response().setStatus(200)
        );
        router.defineRoute(uri, method, handler);

        Response response = router.route(uri, method);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void it405sUndefinedMethods() {
        Routes routes = new Routes();
        Router router = new Router(routes);

        String uri = "/immutable-resource";
        String method = "GET";
        RequestHandler handler = new RequestHandler(
                (request) -> new Response().setStatus(200)
        );
        router.defineRoute(uri, method, handler);

        Response expectOk = router.route(uri, method);
        assertEquals(200, expectOk.getStatus());

        String disallowedMethod = "PUT";
        Response expectDisallowed = router.route(uri, disallowedMethod);
        assertEquals(405, expectDisallowed.getStatus());
    }
}
