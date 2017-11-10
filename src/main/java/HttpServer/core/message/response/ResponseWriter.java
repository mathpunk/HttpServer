package HttpServer.core.message.response;

import HttpServer.core.utility.logger.Logger;
import HttpServer.core.utility.socket.Writable;

import java.io.IOException;
import java.util.ArrayList;

public class ResponseWriter {

    private Writable client;
    private Logger logger;

    public ResponseWriter(Writable client, Logger logger) {
        this.logger = logger;
        this.client = client;
    }

    public void write(Response response) throws IOException {
        logger.log("\nWriting response:");
        logger.log("--------------------------");

        writeHead(response);
        writeBody(response);
        client.flush();
        client.close();
    }

    private void writeBody(Response response) throws IOException {
        byte[] body = response.getBody();
        if (body != null) {
            logger.log("Writing " + body.length + " bytes");
            client.writeBytes(body);
        }
    }

    private void writeHead(Response response) throws IOException {
        ArrayList<String> head = response.getHead();
        for (String line : head) {
            client.writeLine(line);
            logger.log(line);
        }
        client.writeLine("");
    }
}
