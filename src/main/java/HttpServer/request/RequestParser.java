package HttpServer.request;

import HttpServer.LoggerInterface;
import HttpServer.request.Request;
import HttpServer.socket.Readable;

import java.io.IOException;
import java.util.ArrayList;

public class RequestParser {

    private ArrayList<String> linesRead;
    private Readable source;
    private LoggerInterface logger;

    public RequestParser(Readable source, LoggerInterface logger) {
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
        return new Request(linesRead);
    }
}

