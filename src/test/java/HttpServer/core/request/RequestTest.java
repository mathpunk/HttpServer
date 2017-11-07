package HttpServer.core.request;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RequestTest {

    @Test
    public void itSetsAndGetsMethods() {
        Request request = new Request();
        request.setMethod("GET");
        assertEquals(request.getMethod(), "GET");
    }

    @Test
    public void itSetsAndGetsUris() {
        Request request = new Request();
        request.setUri("/foo");
        assertEquals(request.getUri(), "/foo");
    }

    @Test
    public void itSetsAndGetsTheVersion() {
        Request request = new Request();
        request.setVersion("HTTP/2.0");
        assertEquals("HTTP/2.0", request.getVersion());
    }

    @Test
    public void itHasAConvenienceConstructor() {
        Request request = new Request("/", "POST");
        assertEquals("/", request.getUri());
        assertEquals("POST", request.getMethod());
    }

}
