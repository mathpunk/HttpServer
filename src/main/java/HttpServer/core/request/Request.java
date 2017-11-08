package HttpServer.core.request;
import java.util.ArrayList;
import java.util.HashMap;

public class Request {

    private String method;
    private Uri uri;
    private String version;
    private HashMap<String, String> headers;

    public Request() {
        this.headers = new HashMap<>();
    }

    public Request(String uri, String method) {
        this.headers = new HashMap<>();
        this.setUri(uri);
        this.setMethod(method);
    }

    public void setMethod(String method) { this.method = method; }

    public void setUri(String uriString) {
        this.uri = new Uri(uriString);
    }

    public String getUriString() {
        return uri.getUriString();
    }

    public String getMethod() { return this.method; }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }

    public HashMap<String,String> getParameters() {
        return uri.getParameters();
    }

    public String getParameter(String parameterKey) {
        return getParameters().get(parameterKey);
    }

    public String getResourcePath() {
        return uri.getPath();
    }

    public void setHeader(String headerKey, String headerValue) {
        headers.put(headerKey, headerValue);
    }

    public String getHeader(String headerKey) {
        return headers.get(headerKey);
    }
}


