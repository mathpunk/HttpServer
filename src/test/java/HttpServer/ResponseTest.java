package HttpServer;

import org.junit.Test;

import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;

public class ResponseTest {

    @Test
    public void itReadsAndWritesStatuses() {
        Response response = new Response();
        response.putStatus(200);
        assertEquals(response.getStatus(), 200);
    }

    @Test
    public void itReadsAndWritesNotFoundStatus() {
        Response response = new Response();
        response.putStatus(404);
        assertEquals(response.getStatus(), 404);
    }

    @Test
    public void itWritesAndReadsBody() {
        Response response = new Response();
        response.putBody("I am a response");
        assertEquals(response.getBody(), "I am a response");
    }

    @Test
    public void itWritesAndReadsArbitraryHeaders() {
        Response response = new Response();
        response.putHeader("Content Length", "0");
        assertEquals(response.getHeader("Content Length"), "0");
    }

    @Test
    public void itWritesAndReadsAVersion() {
        Response response = new Response();
        String versionSupported = "HTTP 1/1.1";
        response.putVersion(versionSupported);
        assertEquals(versionSupported, response.getVersion());
    }

    @Test
    public void itGets404ReasonPhrases() {
        Response response = new Response();
        response.putStatus(404);
        String reason = response.getReasonPhrase();
        assertEquals("Not Found", reason);
    }

    @Test
    public void itGets200ReasonPhrases() {
        Response response = new Response();
        response.putStatus(200);
        String reason = response.getReasonPhrase();
        assertEquals("OK", reason);
    }

    @Test
    public void itGets405ReasonPhrase() {
        Response response = new Response();
        response.putStatus(405);
        String reason = response.getReasonPhrase();
        assertEquals("Method Not Allowed", reason);
    }
}
