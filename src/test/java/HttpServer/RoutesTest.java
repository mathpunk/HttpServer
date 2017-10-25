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

    @Ignore
    public void it404sMissingResources() {
        // NB: I'm not confident this responsibility is in Routes and not Router.
        String uri = "/nothing-is-here";
        String method = "GET";

        Request request = new Request();
        request.setUri(uri);
        request.setMethod(method);

        Routes routes = new Routes();
        RequestHandler handler = routes.retrieve(uri, method);
        Response response = handler.apply(request);
        assertEquals(404, response.getStatus());
    }

    @Ignore
    public void it405sMissingMethods() {
        // NB: I'm not confident this responsibility is in Routes and not Router.
        String uri = "/nothing-is-here";
        String method = "GET";

        Request request = new Request();
        request.setUri(uri);
        request.setMethod(method);

        Routes routes = new Routes();
        RequestHandler handler = routes.retrieve(uri, method);
        Response response = handler.apply(request);
        assertEquals(405, response.getStatus());
    }
}
