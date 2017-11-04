package HttpServer.request;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

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
}
