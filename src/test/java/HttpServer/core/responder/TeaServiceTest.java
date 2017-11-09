package HttpServer.core.service;

import HttpServer.core.request.Request;
import HttpServer.core.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TeaServiceTest {

    private TeaService teaService;

    @Before
    public void setup() {
        teaService = new TeaService();
    }
    
    @Test
    public void itIsOkForTea() {
        Request request = new Request();
        request.setUri("/tea");
        request.setMethod("GET");
        Response response = teaService.respond(request);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void itIsATeapot() {
        Request request = new Request();
        request.setUri("/coffee");
        request.setMethod("GET");
        Response response = teaService.respond(request);
        assertEquals(418, response.getStatus());
    }

    @Test
    public void itAcknowledgesItIsATeapot() {
        Request request = new Request();
        request.setUri("/coffee");
        request.setMethod("GET");
        Response response = teaService.respond(request);
        assertEquals("I'm a teapot", response.getBody());
    }

    @Test
    public void itOtherwiseFails() {
        Request request = new Request();
        request.setUri("/anything-else");
        request.setMethod("GET");
        Response response = teaService.respond(request);
        assertEquals(500, response.getStatus());
    }
}
