package HttpServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class RequestParser {

    private ArrayList<String> lines;
    public HashMap request;
    private Readable source;
    private LoggerInterface logger;

    public RequestParser(Readable source, LoggerInterface logger) {
        this.logger = logger;
        this.lines = new ArrayList<>();
        this.request = new HashMap();
        this.source = source;
    }

    public void read() throws IOException {
        logger.log("Reading request: ");
        logger.log("--------------------------");
        String line = source.readLine();
        do {
            lines.add(line);
            logger.log(line);
            line = source.readLine();
        } while (!line.isEmpty());
    }

    public HashMap parse() {
        String requestLine = lines.get(0);
        String[] tokens = requestLine.split("\\s+");

        String method = tokens[0];
        request.put("Method", method);

        String path = tokens[1];
        request.put("URI", path);

        return request;
    }

    public HashMap request() {
        parse();
        return request;
    }

}

