package HttpServer;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResponseWriterTest {

    @Test public void itWritesLines() throws IOException {
        Response response = new Response();
        response.setStatus(200);

        MockClient client = new MockClient();
        TestLog logger = new TestLog();

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
        TestLog logger = new TestLog();

        ResponseWriter writer = new ResponseWriter(client, logger);
        try {
            writer.write(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String firstWrittenLine = client.output.get(0);
        assertThat(firstWrittenLine, containsString("200"));
    }

}
