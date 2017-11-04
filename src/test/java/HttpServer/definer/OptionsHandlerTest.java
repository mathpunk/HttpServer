package HttpServer.definer;

import HttpServer.request.Request;
import HttpServer.response.Response;
import HttpServer.router.MockRouter;
import HttpServer.router.Router;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class OptionsHandlerTest {

    private OptionsHandler handler;

    @Test
    public void itRespondsWithOk() {
        Handler handler = new OptionsHandler(new Router());
        Request request = new Request();
        request.setMethod("OPTIONS");
        request.setUri("/method_options");
        Response response = handler.respond(request);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void itQueriesTheRouterWhenRespondingToStar() {
        MockRouter router = new MockRouter();
        Handler handler = new OptionsHandler(router);
        Request request = new Request();
        request.setMethod("OPTIONS");
        request.setUri("*");
        handler.respond(request);
        assert(router.wasAskedForMethods);
    }

    @Test
    public void itSetsAnAllowHeader() {
        Router router = new Router();
        Handler handler = new OptionsHandler(router);
        router.defineRoute("/puttable", "PUT", new FunctionalHandler(200));
        router.defineRoute("/gettable", "GET", new FunctionalHandler(200));

        Request request = new Request();
        request.setMethod("OPTIONS");
        request.setUri("*");

        Response response = handler.respond(request);
        assertNotNull(response.getHeader("Allow"));
    }

    @Test
    public void itSetsTheAllowHeaderValue() {
        Router router = new Router();
        Handler handler = new OptionsHandler(router);
        router.defineRoute("/puttable", "PUT", new FunctionalHandler(200));
        router.defineRoute("/gettable", "GET", new FunctionalHandler(200));

        Request request = new Request();
        request.setMethod("OPTIONS");
        request.setUri("*");

        Response response = handler.respond(request);
        String allowedMethods = response.getHeader("Allow");
        assertThat(allowedMethods, containsString("GET"));
        assertThat(allowedMethods, containsString("PUT"));
    }
}
