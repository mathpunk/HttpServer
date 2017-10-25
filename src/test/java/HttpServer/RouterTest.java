package HttpServer;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class RouterTest {

    private Router router;

    @Before
    public void setUp() {
        router = new Router();
    }
    @Test
    public void itDefinesANewRoute() {
        String verb = "GET";
        String uri = "/foo";
        Response ok = new Response().putStatus(200);

        router.defineRoute(verb, uri, ok);
        Response response = router.respond(verb, uri);
        assertEquals(response, ok);
    }

    @Test
    public void itDoesntFindNonResources() {
        String verb = "GET";
        String uri = "/i-dont-have-this";
        Response notFound = new Response().putStatus(404);

        System.out.println(router.respond(verb, uri));
        Response response = router.respond(verb, uri);
        assertEquals(response, notFound);
    }

    @Test
    public void itDisallowsUndefinedVerbs() {
        Request request = new Request();
        request.putMethod("PUT");
        request.putUri("/coffee");
        Response response = router.route(request);
        assertEquals(405, response.getStatus());
        assertEquals("Method Not Allowed", response.getReasonPhrase());
    }

}
