package HttpServer.router;

import HttpServer.definer.FileRouteDefiner;
import HttpServer.definer.Handler;
import HttpServer.definer.FunctionalHandler;
import HttpServer.definer.TeaRouteDefiner;
import HttpServer.request.Request;
import HttpServer.response.Response;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

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

        ArrayList<String> requestInput = new ArrayList<>();
        requestInput.add("GET /tea HTTP/1.1");
        Request request = new Request(requestInput);

        Response response = router.route(request);
        assertEquals(200, response.getStatus());
    }

    @Test
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
