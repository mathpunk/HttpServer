package HttpServer;

import java.io.IOException;
import java.util.HashMap;

public class ResponseWriter {

    private RequestParser parser;
    private Writable writing;
    private Controller routes;
    private LoggerInterface logger;

    public ResponseWriter(RequestParser parser, Writable writing, LoggerInterface logger) {
        this.logger = logger;
        this.routes = new Controller();
        this.parser = parser;
        this.writing = writing;
    }

    public void write() throws IOException {
        HashMap request = parser.parse();

        logger.log("\nWriting response:");
        logger.log("--------------------------");

        Response basicResponse = respondWithStatus(request);

        String statusLine = basicResponse.statusLine();
        writeLine(statusLine);

        if (basicResponse.containsKey("Content-Type")) {
            String contentType = basicResponse.get("Content-Type");
            writeLine("Content-Type: " + contentType);
        }

        if (basicResponse.containsKey("Body")) {
            String body = basicResponse.get("Body");
            int contentLength = body.length();
            writeLine("Content-Length: " + contentLength);
            writeLine("");
            writeLine(body);
            writeLine("");
        } else {
            writeLine("Content-Length: " + 0);
            writeLine("");
        }

        writing.flush();
        writing.close();
    }

    private void writeLine(String message) throws IOException {
        logger.log(message);
        writing.writeLine(message);
    }

    private Response respondWithStatus(HashMap<String, String> request) throws IOException {
        String verb = request.get("Method");
        String resource = request.get("URI");
        Response response = routes.respond(verb, resource);
        return response;
    }
}
