package HttpServer.response;

import HttpServer.response.Response;
import HttpServer.response.ResponseWriter;
import HttpServer.socket.MockClient;
import HttpServer.utility.QuietLogger;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class ResponseWriterTest {

    @Test public void itWritesLines() throws IOException {
        Response response = new Response();
        response.setStatus(200);

        MockClient client = new MockClient();
        QuietLogger logger = new QuietLogger();

        ResponseWriter writer = new ResponseWriter(client, logger);
        try {
            writer.write(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert(client.output.size() > 0);
    }

    @Test public void itWritesAStatusLineFirst() throws IOException {
        Response response = new Response();
        response.setStatus(200);

        MockClient client = new MockClient();
        QuietLogger logger = new QuietLogger();

        ResponseWriter writer = new ResponseWriter(client, logger);
        try {
            writer.write(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String firstWrittenLine = client.output.get(0);
        assertThat(firstWrittenLine, containsString("200"));
    }

    @Test
    public void itWritesHeaders() throws IOException {
        Response response = new Response();
        response.setStatus(200);
        response.setHeader("Content-Length", 0);

        MockClient client = new MockClient();
        QuietLogger logger = new QuietLogger();

        ResponseWriter writer = new ResponseWriter(client, logger);
        try {
            writer.write(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String firstWrittenLine = client.output.get(0);
        String secondWrittenLine = client.output.get(1);
        assertThat(firstWrittenLine, containsString("200"));
        assertThat(secondWrittenLine, containsString("Content-Length"));
    }

    @Test
    public void itSeparatesHeadersAndBody() {
        Response response = new Response();
        response.setStatus(200);
        response.setHeader("Content-Length", 0);
        response.setBody("I'm a response");

        MockClient client = new MockClient();
        QuietLogger logger = new QuietLogger();

        ResponseWriter writer = new ResponseWriter(client, logger);
        try {
            writer.write(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String statusLine = client.output.get(0);
        String headerLine = client.output.get(1);
        String CRLF = client.output.get(2);
        String body = client.output.get(3);

        assertThat(statusLine, containsString("200"));
        assertThat(headerLine, containsString("Content-Length"));
        assertEquals("", CRLF);
        assertThat(body, containsString("I'm a response"));
    }
}

