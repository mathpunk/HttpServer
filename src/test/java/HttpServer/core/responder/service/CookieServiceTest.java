package HttpServer.core.responder.service;

import HttpServer.core.message.request.Request;
import HttpServer.core.responder.service.CookieService;
import HttpServer.core.message.response.Response;
import org.junit.Test;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class CookieServiceTest {

    @Test
    public void itSetsCookieFlavorForTheClient() {
        CookieService baker = new CookieService();
        Request flavorRequest = new Request("/cookie?type=gingersnap", "GET");
        Response flavorResponse = baker.respond(flavorRequest);
        assertEquals(200, flavorResponse.getStatus());
        assertEquals("type=gingersnap", flavorResponse.getHeader("Set-Cookie"));
    }

    @Test
    public void itInstructsTheUserToEat() {
        CookieService baker = new CookieService();
        Request flavorRequest = new Request("/cookie?type=gingersnap", "GET");
        Response flavorResponse = baker.respond(flavorRequest);
        assertThat(flavorResponse.getBody(), containsString("Eat"));
    }

    @Test
    public void itSuccessfullyServesTheRightFlavor() {
        CookieService baker = new CookieService();
        Request flavorRequest = new Request("/cookie?type=gingersnap", "GET");
        baker.respond(flavorRequest);

        Request eatRequest = new Request("/eat_cookie", "GET");
        eatRequest.setHeader("Cookie", "type=gingersnap");
        Response eatResponse = baker.respond(eatRequest);
        assertEquals(200, eatResponse.getStatus());
        assertThat(eatResponse.getBody(), containsString("mmmm gingersnap"));
    }
}
