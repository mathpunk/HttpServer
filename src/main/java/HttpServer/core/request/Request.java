package HttpServer.core.request;
import java.util.ArrayList;
import java.util.HashMap;

public class Request {

    private String method;
    private Uri uri;
    private String version;

//    public Request(ArrayList<String> linesRead) {
//        String requestLine = linesRead.get(0);
//    }

    public Request() { }

    public Request(String uri, String method) {
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
}


