package HttpServer.definer;

import HttpServer.request.Request;
import HttpServer.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TeaHandlerTest {

    private TeaHandler teaHandler;

    @Before
    public void setup() {
        teaHandler = new TeaHandler();
    }
    
    @Test
    public void itIsOkForTea() {
        Request request = new Request();
        request.setUri("/tea");
        request.setMethod("GET");
        Response response = teaHandler.respond(request);
        assertEquals(200, response.getStatus());
    }
}
