package HttpServer;
import java.util.ArrayList;

public class Request {

    private String method;
    private String uri;
    private String version;

    public Request(ArrayList<String> linesRead) {
        String requestLine = linesRead.get(0);
        parseRequestLine(requestLine);
    }

    public void putMethod(String method) { this.method = method; }

    public void putUri(String uri) { this.uri = uri; }

    public String getUri() { return this.uri; }

    public String getMethod() { return this.method; }

    private void parseRequestLine(String line) {
        String[] tokens = line.split("\\s+");
        putMethod(tokens[0]);
        putUri(tokens[1]);
    }
}


