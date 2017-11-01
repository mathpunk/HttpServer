package HttpServer;

import HttpServer.controller.RequestHandler;

import java.util.HashMap;

public class Resource {
    private String uri;
    private HashMap<String, RequestHandler> allowedMethods = new HashMap<>();

    public Resource(Object o) {
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setHandler(String method, RequestHandler f) {
        allowedMethods.put(method, f);
    }

    public RequestHandler handler(String method) {
        return allowedMethods.get(method);
    }
}
