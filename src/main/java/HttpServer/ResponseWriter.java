package HttpServer;

import java.io.IOException;
import java.util.ArrayList;

public class ResponseWriter {

    private Writable client;
    private LoggerInterface logger;

    public ResponseWriter(Writable client, LoggerInterface logger) {
        this.logger = logger;
        this.client = client;
    }

    public void write(Response response) throws IOException {
        logger.log("\nWriting response:");
        logger.log("--------------------------");

        ArrayList<String> head = response.getHead();

        for (String line : head) {
            client.writeLine(line);
            logger.log(line);
        }
        client.writeLine("");
        if (response.getBody() != null) {

            client.writeLine(response.getBody());
        }
        client.flush();
        client.close();
    }
}
