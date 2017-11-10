package HttpServer.core.responder;

import HttpServer.core.message.request.Request;
import HttpServer.core.responder.service.RedirectService;
import HttpServer.core.message.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class RedirectServiceTest {

    private Request request;
    private Response response;

    @Before
    public void setup() {
        HashMap<String, String> redirectionMap = new HashMap<>();
        redirectionMap.put("/redirect", "/");
        RedirectService service = new RedirectService(redirectionMap);
        this.request = new Request("/redirect", "GET");
        this.response = service.respond(request);
    }

    @Test
    public void itHasStatusCode302() {
        assertEquals(302, response.getStatus());
    }

    @Test
    public void itSetsLocation() {
        assertEquals("/", response.getHeader("Location"));
    }
}

