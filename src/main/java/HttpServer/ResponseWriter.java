package HttpServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ResponseWriter {

    private RequestParser parser;
    private Writable writing;
    private ArrayList<String> resources;

    public ResponseWriter(RequestParser parser, Writable writing) {
        this.resources = new ArrayList<>();
        resources.add("/");
        this.parser = parser;
        this.writing = writing;
    }

    public void write() throws IOException {
        HashMap request = parser.parse();

        System.out.println("\nWriting response:");
        System.out.println("--------------------------");

        String requestLine = requestLine(request);
        System.out.println(requestLine);
        writing.writeLine(requestLine);

        String contentType = "Content-Type: text/html";
        System.out.println(contentType);
        writing.writeLine(contentType);

        String contentLength = "Content-Length: 0";
        System.out.println(contentLength);
        writing.writeLine(contentLength);

        System.out.println("<CRLF>");
        writing.writeLine("");
    }

    private String requestLine(HashMap request) throws IOException {
        String requestedResource = (String) request.get("URI");
        boolean found = resources.stream().anyMatch(resource -> requestedResource.matches(resource));
        String requestLine;
        if (found) {
            requestLine = "HTTP/1.1 200 OK";
        } else {
            requestLine = "HTTP/1.1 404 Not Found";
        }
        return requestLine;
    }
}
