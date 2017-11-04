package HttpServer.request;

import HttpServer.utility.Logger;
import HttpServer.socket.Readable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class RequestParser {

    private ArrayList<String> linesRead;
    private Readable source;
    private Logger logger;

    public RequestParser(Readable source, Logger logger) {
        this.logger = logger;
        this.linesRead = new ArrayList<>();
        this.source = source;
    }

    public Request read() throws IOException {
        logger.log("Reading request: ");
        logger.log("--------------------------");
        String line = source.readLine();
        do {
            linesRead.add(line);
            logger.log(line);
            line = source.readLine();
        } while (!line.isEmpty());
        return buildRequest();
    }

    private Request buildRequest() {
        String requestLine = linesRead.get(0);
        String[] tokens = requestLine.split("\\s+");
        Request request = new Request();
        request.setMethod(tokens[0]);
        request.setUri(tokens[1]);
        request.setVersion(tokens[2]);
        return request;
    }
}

