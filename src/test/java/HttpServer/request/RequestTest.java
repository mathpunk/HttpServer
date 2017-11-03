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
    public void itParsesTheMethod() {
        ArrayList<String> requestInput = new ArrayList<>();
        requestInput.add("GET / HTTP/1.1");
        Request request = new Request(requestInput);
        assertEquals("GET", request.getMethod());
    }

    @Test
    public void itParsesTheUri() {
        ArrayList<String> requestInput = new ArrayList<>();
        requestInput.add("GET / HTTP/1.1");
        Request request = new Request(requestInput);
        assertEquals("/", request.getUri());
    }

    @Test
    public void itParsesTheVersion() {
        ArrayList<String> requestInput = new ArrayList<>();
        requestInput.add("GET / HTTP/1.1");
        Request request = new Request(requestInput);
        assertEquals("HTTP/1.1", request.getVersion());
    }
}
