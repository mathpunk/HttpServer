package HttpServer;

import java.io.IOException;

public class ResponseWriter {

    private RequestParser parser;
    private Writable writing;

    public ResponseWriter(RequestParser parser, Writable writing) {
        this.parser = parser; // Intermediate class here? This has strings.
        this.writing = writing;
    }

    public void write() throws IOException {

        System.out.println("\nWriting response:");
        System.out.println("--------------------------");

        String requestLine = "HTTP/1.1 200 OK";
        System.out.println(requestLine);
        try {
            writing.writeLine(requestLine);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String contentType = "Content-Type: text/html";
        System.out.println(contentType);
        writing.writeLine(contentType);

        String contentLength = "Content-Length: 0";
        System.out.println(contentLength);
        writing.writeLine(contentLength);

        System.out.println("<CRLF>");
        writing.writeLine("");
    }
}
