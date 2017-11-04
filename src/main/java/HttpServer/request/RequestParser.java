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
        HashMap<String, String> parsed = parseRequestLine(linesRead.get(0));
        Request request = new Request();
        request.setMethod(parsed.get("Method"));
        request.setUri(parsed.get("Uri"));
        request.setVersion(parsed.get("Version"));
        return request;
    }

    public HashMap<String, String> parseRequestLine(String input) {
        HashMap<String, String> data = new HashMap<>();
        String[] tokens = input.split("\\s+");
        data.put("Method", tokens[0]);
        data.put("Uri", tokens[1]);
        data.put("Version", tokens[2].trim());
        return data;
    }
}

