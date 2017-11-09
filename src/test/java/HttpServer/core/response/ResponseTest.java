package HttpServer.core.response;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResponseTest {

    @Test
    public void itSetsAndGetsStatusOK() {
        Response response = new Response();
        response.setStatus(200);
        assertEquals(response.getStatus(), 200);
    }

    @Test
    public void itSetsAndGetsStatusNotFound() {
        Response response = new Response();
        response.setStatus(404);
        assertEquals(response.getStatus(), 404);
    }

    @Test
    public void itSetsAndGetsBody() {
        Response response = new Response();
        response.setBody("I am a response");
        assertEquals(response.getBody(), "I am a response");
    }

    @Test
    public void itGetsItsBodySetFromAFile() throws IOException {
        Response response = new Response();
        StringBuilder content = new StringBuilder();
        Stream<String> lines = Files.lines(Paths.get("./cob_spec/public/file1"));
        lines.forEach(line -> content.append(line).append("\n"));
        lines.close();
        response.setBody(content.toString().trim());
        assertEquals("file1 contents", response.getBody());
    }

    @Test
    public void itSetsAndGetsHeaders() {
        Response response = new Response();
        response.setHeader("Content Length", "0");
        assertEquals(response.getHeader("Content Length"), "0");
    }

    @Test
    public void itSetsAndGetsHttpVersion() {
        Response response = new Response();
        String versionSupported = "HTTP 1/1.1";
        response.setVersion(versionSupported);
        assertEquals(versionSupported, response.getVersion());
    }

    @Test
    public void itGetsAStatusLine() {
        Response response = new Response();
        response.setStatus(200);
        String statusLine = response.getStatusLine();
        assertThat(statusLine, containsString("200"));
        assertThat(statusLine, containsString("OK"));
    }

    @Test
    public void itGetsAHead() {
        Response response = new Response();
        response.setStatus(200);
        response.setHeader("Content-Length", 0);
        ArrayList<String> head = response.getHead();
        assertThat(head.get(0), containsString("200"));
        assertThat(head.get(1), containsString("Content-Length"));
    }

    @Test
    public void itGetsAHeaderAsAString() {
        Response response = new Response();
        response.setHeader("Content-Length", "0");
        String headerString = response.getHeaderAsString("Content-Length");
        assertEquals("Content-Length: 0", headerString);

    }
}
