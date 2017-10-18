package HttpServer;
import org.junit.Test;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ResponseWriterTest {

    @Test
    public void itIsOkWithSimpleCurl() throws IOException {
        MockTraffic traffic = new MockTraffic();
        MockClient client = new MockClient();
        traffic.emulateSimpleCurl();
        RequestParser parser = new RequestParser(traffic);
        ResponseWriter writer = new ResponseWriter(parser, client);
        parser.read();
        writer.write();

        String expectation = "HTTP/1.1 200 OK";
        assert(client.received(expectation));
    }

    @Test
    public void itIsNotFoundForFaviconCurl() throws IOException {
        MockTraffic traffic = new MockTraffic();
        MockClient client = new MockClient();
        traffic.emulateFaviconCurl();
        RequestParser parser = new RequestParser(traffic);
        ResponseWriter writer = new ResponseWriter(parser, client);
        parser.read();
        writer.write();

        String expectation = "HTTP/1.1 400 Not Found";
        assert(client.received(expectation));
    }

}
