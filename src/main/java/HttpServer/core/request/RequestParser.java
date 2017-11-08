package HttpServer.core.request;

import HttpServer.core.utility.logger.Logger;
import HttpServer.core.utility.socket.Readable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Request request = new Request();
        int index = 0;
        int count = linesRead.size();
        while (index < count) {
            String line = linesRead.get(index);
            if (index == 0) {
                setRequestLine(request, line);
            } else {
                String divider = ":";
                int dividerIndex = line.indexOf(divider);
                String headerKey = line.substring(0, dividerIndex).trim();
                String headerValue = line.substring(dividerIndex+1, line.length()).trim();
                request.setHeader(headerKey, headerValue);
            }
            index++;
        }
        return request;
    }

    private void setRequestLine(Request request, String line) {
        String requestLine = line;
        String[] tokens = requestLine.split("\\s+");
        request.setMethod(tokens[0]);
        request.setUri(tokens[1]);
        request.setVersion(tokens[2]);
    }
}

