package HttpServer.core.request;
import HttpServer.core.Message;

import java.util.ArrayList;
import java.util.HashMap;

public class Request extends Message {

    private String method;
    private Uri uri;

    public Request() {
        super();
    }

    public Request(Uri uri, String method) {
        super();
        this.setUri(uri);
        this.setMethod(method);
    }

    public Request(String uri, String method) {
        super();
        this.setUri(uri);
        this.setMethod(method);
    }

    public void setMethod(String method) { this.method = method; }

    public void setUri(String uriString) {
        this.uri = new Uri(uriString);
    }

    public void setUri(Uri uri) { this.uri = uri; }

    public String getUriString() {
        return uri.getUriString();
    }

    public String getMethod() { return this.method; }

    public HashMap<String,String> getParameters() {
        return uri.getParameters();
    }

    public String getParameter(String parameterKey) {
        return getParameters().get(parameterKey);
    }

    public String getPath() {
        return uri.getPath();
    }

    public Uri getUri() {
        return this.uri;
    }
}


