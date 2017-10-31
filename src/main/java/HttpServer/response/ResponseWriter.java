package HttpServer.response;

import HttpServer.LoggerInterface;
import HttpServer.socket.Writable;

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

        writeHead(response);
        writeBody(response);
        client.flush();
        client.close();
    }

    private void writeBody(Response response) throws IOException {
        if (response.getBody() != null) {
            client.writeLine(response.getBody());
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
