package HttpServer;

import HttpServer.controller.RequestHandler;
import HttpServer.response.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResourceTest {

    @Test
    public void itHasAUri() {
        Resource resource = new Resource(new Object());
        resource.setUri("/thing");
        String uri = resource.getUri();
        assertEquals("/thing", uri);
    }

    @Test
    public void itHasAGetHandler() {
        Resource resource = new Resource(new Object());
        RequestHandler f = new RequestHandler((request) -> new Response().setStatus(200));
        resource.setHandler("GET", f);
        assertEquals(f, resource.handler("GET"));
    }

}
