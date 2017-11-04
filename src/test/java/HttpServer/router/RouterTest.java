package HttpServer.router;

import HttpServer.definer.*;
import HttpServer.request.Request;
import HttpServer.response.Response;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

public class RouterTest {

    @Test
    public void itCanHaveARouteDefinedDirectly() {
        Router router = new Router();

        String uri = "/";
        String method = "GET";
        Request request = new Request();
        request.setUri(uri);
        request.setMethod(method);

        Handler handler = new FunctionalHandler(200);
        router.defineRoute(uri, method, handler);

        Response response = router.route(request);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void itCanHaveMultipleVerbsDefinedDirectly() {
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

        Request request = new Request();
        String uri = "/absent-resource";
        String method = "GET";
        request.setUri(uri);
        request.setMethod(method);

        Response response = router.route(request);
        assertEquals(404, response.getStatus());
    }

    @Test
    public void it405sUndefinedMethods() {
        FileRouteDefiner definer = new FileRouteDefiner("./cob_spec/public", new Router());
        Router router = definer.getRouter();

        String uri = "/file1";

        String allowedMethod = "GET";
        String disallowedMethod = "PUT";

        Request goodRequest = new Request();
        goodRequest.setUri(uri);
        goodRequest.setMethod(allowedMethod);

        Request badRequest = new Request();
        badRequest.setUri(uri);
        badRequest.setMethod(disallowedMethod);

        Response expectOk = router.route(goodRequest);
        assertEquals(200, expectOk.getStatus());

        Response expectNotAllowed = router.route(badRequest);
        assertEquals(405, expectNotAllowed.getStatus());
    }

    @Test
    public void itCanHaveTeaRoutesDefined() {
        Router blankRouter = new Router();
        TeaRouteDefiner definer = new TeaRouteDefiner(blankRouter);
        Router router = definer.getRouter();

        Request request = new Request();
        request.setUri("/tea");
        request.setMethod("GET");

        Response response = router.route(request);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void itCanBeDefinedAsATeapot() {
        Router blankRouter = new Router();
        TeaRouteDefiner definer = new TeaRouteDefiner(blankRouter);
        Router router = definer.getRouter();

        Request request = new Request();
        request.setUri("/coffee");
        request.setMethod("GET");

        Response response = router.route(request);
        assertEquals(418, response.getStatus());
    }

    @Test
    public void itRoutesTeaRequests() {
        Router blankRouter = new Router();
        TeaRouteDefiner definer = new TeaRouteDefiner(blankRouter);
        Router router = definer.getRouter();

        Request request = new Request();
        request.setMethod("GET");
        request.setUri("/tea");

        Response response = router.route(request);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void itCanListItsDefinedUris() {
        // This functionality will be useful for handling wildcard OPTION requests.
        Router blankRouter = new Router();
        TeaRouteDefiner teaDefiner = new TeaRouteDefiner(blankRouter);
        Router router = teaDefiner.getRouter();
        ArrayList<String> uris = router.getDefinedUris();
        assertThat(uris, hasItems("/coffee", "/tea"));
    }

    @Test
    public void itCanListMethodsForOneUri() {
        Router blankRouter = new Router();
        TeaRouteDefiner teaDefiner = new TeaRouteDefiner(blankRouter);
        Router router = teaDefiner.getRouter();
        ArrayList<String> teaMethods = router.getDefinedMethods("/tea");
        assertThat(teaMethods, hasItem("GET"));
    }

    @Test
    public void itCanListMethodsForAllUris() {
        Router blankRouter = new Router();
        TeaRouteDefiner teaDefiner = new TeaRouteDefiner(blankRouter);
        Router router = teaDefiner.getRouter();
        router.defineRoute("/form", "POST", new FunctionalHandler(200));
        ArrayList<String> teaMethods = router.getDefinedMethods("*");
        assertThat(teaMethods, hasItems("GET", "POST"));
    }

    @Ignore
    public void itCanHaveFileRoutesDefined() {
        FileRouteDefiner definer = new FileRouteDefiner("./cob_spec/public", new Router());
        Router router = definer.getRouter();

        Request request = new Request();
        request.setUri("/file1");
        request.setMethod("GET");

        Response response = router.route(request);
        System.out.println(response.getBody());
        assertEquals("file1 contents", response.getBody());
    }
}
