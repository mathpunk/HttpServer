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

    public Request() {}

    public void setMethod(String method) { this.method = method; }

    public void setUri(String uri) { this.uri = uri; }

    public String getUri() { return this.uri; }

    public String getMethod() { return this.method; }

    private void parseRequestLine(String line) {
        String[] tokens = line.split("\\s+");
        setMethod(tokens[0]);
        setUri(tokens[1]);
    }
}


