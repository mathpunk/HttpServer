package HttpServer.request;
import java.util.ArrayList;

public class Request {

    private String method;
    private String uri;
    private String version;

    public Request(ArrayList<String> linesRead) {
        String requestLine = linesRead.get(0);
    }

    public Request() { }

    public void setMethod(String method) { this.method = method; }

    public void setUri(String uri) { this.uri = uri; }

    public String getUri() { return this.uri; }

    public String getMethod() { return this.method; }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }
}


