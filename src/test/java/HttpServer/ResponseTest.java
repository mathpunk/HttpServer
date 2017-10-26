package HttpServer;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResponseTest {

    @Test
    public void itReadsAndWritesStatuses() {
        Response response = new Response();
        response.setStatus(200);
        assertEquals(response.getStatus(), 200);
    }

    @Test
    public void itReadsAndWritesNotFoundStatus() {
        Response response = new Response();
        response.setStatus(404);
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
    public void itWritesAStatusLine() {
        Response response = new Response();
        response.setStatus(200);
        String statusLine = response.getStatusLine();
        assertThat(statusLine, containsString("200"));
        assertThat(statusLine, containsString("OK"));
    }
}
