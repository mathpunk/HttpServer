package HttpServer.core.message.response;

import HttpServer.core.utility.logger.Logger;
import HttpServer.core.utility.socket.Writable;

import java.io.IOException;
import java.nio.charset.Charset;
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
        String body = response.getBody();
        if (body != null) {
            byte[] bytes = body.getBytes();
            logger.log("Writing " + bytes.length + " bytes");
            client.writeBytes(bytes);
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
