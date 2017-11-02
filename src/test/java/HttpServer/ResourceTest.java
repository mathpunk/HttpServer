package HttpServer;

import HttpServer.definer.Handler;
import HttpServer.definer.FunctionalHandler;
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
        Handler ok = new FunctionalHandler(200);
        resource.setHandler("GET", ok);
        assertEquals(ok, resource.handler("GET"));
    }

}
