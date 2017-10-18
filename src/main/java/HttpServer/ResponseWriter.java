package HttpServer;

import java.io.IOException;
import java.util.HashMap;

public class ResponseWriter {

    private RequestParser parser;
    private Writable writing;
    private Controller routes;

    public ResponseWriter(RequestParser parser, Writable writing) {
        this.routes = new Controller();
        this.parser = parser;
        this.writing = writing;
    }

    public void write() throws IOException {
        HashMap request = parser.parse();

        System.out.println("\nWriting response:");
        System.out.println("--------------------------");

        String statusLine = respondWithStatus(request);
        writeLine(statusLine);

        String contentType = "Content-Type: text/html";
        writeLine(contentType);

        String contentLength = "Content-Length: 0";
        writeLine(contentLength);

        System.out.println("<CRLF>");
        writing.writeLine("");

        writing.flush();
        writing.close();

    }

    private void writeLine(String message) throws IOException {
        System.out.println(message);
        writing.writeLine(message);
    }

    private String respondWithStatus(HashMap request) throws IOException {
        String verb = (String) request.get("Method");
        String resource = (String) request.get("URI");
        Response response = routes.respond(verb, resource);
        return response.statusLine();
    }
}
