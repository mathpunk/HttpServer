package HttpServer.core.request;

import org.junit.Test;

import java.util.HashMap;

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
    public void itSetsAndGetsUrisToAndFromStrings() {
        Request request = new Request();
        request.setUri("/foo");
        assertEquals(request.getUriString(), "/foo");
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
        assertEquals("/", request.getUriString());
        assertEquals("POST", request.getMethod());
    }

    @Test
    public void itProvidesParameterAccess() {
        Request request = new Request("/parameters?like=this&and=that", "GET");
        HashMap<String, String> parameters = request.getParameters();
        assertNotNull(parameters);
        assertEquals("this", parameters.get("like"));
        assertEquals("that", parameters.get("and"));
    }

    @Test
    public void itDecodesParameters() {
        Request request = new Request("/query?language=C%2B%2B", "GET");
        assertEquals("C++", request.getParameters().get("language"));
    }

    @Test
    public void itProvidesSingleParameterAccess() {
        Request request = new Request("/query?language=C%2B%2B", "GET");
        assertEquals("C++", request.getParameter("language"));
    }

    @Test
    public void itCanProvideTheResourcePathSeparatedFromParameters() {
        Request request = new Request("/query?language=C%2B%2B", "GET");
        assertEquals("/query", request.getResourcePath());
    }

    @Test
    public void itSetsAndGetsHeaders() {
        Request request = new Request("/query?language=C%2B%2B", "GET");
        request.setHeader("Cookie", "language=rust");
        assertEquals("language=rust", request.getHeader("Cookie"));

    }

}
