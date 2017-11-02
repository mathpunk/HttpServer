package HttpServer;

import HttpServer.definer.Handler;

import java.util.HashMap;

public class Resource {
    private String uri;
    private HashMap<String, Handler> allowedMethods = new HashMap<>();

    public Resource(Object o) {
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setHandler(String method, Handler f) {
        allowedMethods.put(method, f);
    }

    public Handler handler(String method) {
        return allowedMethods.get(method);
    }
}
