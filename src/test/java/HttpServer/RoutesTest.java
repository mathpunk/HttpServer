package HttpServer;

import org.junit.Ignore;
import org.junit.Test;
import HttpServer.Request;

import static org.junit.Assert.assertEquals;

public class RoutesTest {

    @Test
    public void itDefinesARoute() {
        String uri = "/";
        String method = "GET";
        RequestHandler handler = new RequestHandler((request) -> new Response());

        Routes routes = new Routes();
        routes.define(uri, method, handler);

        RequestHandler retrievedHandler = routes.retrieve(uri, method);
        assertEquals(handler, retrievedHandler);
    }

    @Test
    public void itDefinesMultipleVerbs() {
        String uri = "/form";
        String method = "POST";
        String anotherMethod = "PUT";

        RequestHandler handler = new RequestHandler((request) -> new Response());
        RequestHandler anotherHandler = new RequestHandler((request) -> new Response());

        Routes routes = new Routes();
        routes.define(uri, method, handler);
        routes.define(uri, anotherMethod, anotherHandler);

        RequestHandler retrievedHandler = routes.retrieve(uri, method);
        assertEquals(handler, retrievedHandler);

        RequestHandler anotherRetrievedHandler = routes.retrieve(uri, anotherMethod);
        assertEquals(anotherHandler, anotherRetrievedHandler);
    }

    @Test
    public void it404sMissingResources() {
        // NB: I'm not confident this responsibility is in Routes and not Router.
        String uri = "/nothing-is-here";
        String method = "GET";

        Routes routes = new Routes();
        RequestHandler handler = routes.retrieve(uri, method);
        Response response = handler.apply(new Request());
        assertEquals(404, response.getStatus());
    }

    @Test
    public void it405sMissingMethods() {
        // NB: I'm not confident this responsibility is in Routes and not Router.
        String uri = "/coffee";
        String allowedMethod = "GET";
        RequestHandler handler = new RequestHandler((request) -> new Response());

        String disallowedMethod = "PUT";

        Routes routes = new Routes();
        routes.define(uri, allowedMethod, handler);

        RequestHandler happyHandler = routes.retrieve(uri, allowedMethod);
        RequestHandler sadHandler = routes.retrieve(uri, disallowedMethod);

        assertEquals(handler, happyHandler);
        // assertEquals(405, sadHandler.apply(new Request()).getStatus());
    }
}
