package HttpServer;

import org.junit.Test;
import HttpServer.Request;

import static org.junit.Assert.assertEquals;

public class RoutesTest {

    @Test
    public void itDefinesARoute() {
        Routes routes = new Routes();

        String uri = "/";
        String method = "GET";
        RequestHandler handler = new RequestHandler();

        Request request = new Request();
        request.setUri("/");
        request.setMethod("GET");

        routes.define(uri, method, handler);
        RequestHandler retrievedHandler = routes.retrieve(uri, method);
        assertEquals(handler, retrievedHandler);
    }
}
