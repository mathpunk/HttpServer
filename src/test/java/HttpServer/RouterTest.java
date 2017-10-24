package HttpServer;

import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class RouterTest {

    @Test
    public void itDefinesANewRoute() {
        String verb = "GET";
        String uri = "/foo";
        Response ok = new Response().putStatus(200);

        Router router = new Router();
        router.defineRoute(verb, uri, ok);

        Response response = router.respond(verb, uri);
        assertEquals(response, ok);
    }

    @Ignore
    public void itDoesntFindNonResources() {
        String verb = "GET";
        String uri = "/i-dont-have-this";
        Response notFound = new Response().putStatus(404);

        Router router = new Router();
        System.out.println(router.respond(verb, uri));
        Response response = router.respond(verb, uri);
        assertEquals(response, notFound);
    }

}
