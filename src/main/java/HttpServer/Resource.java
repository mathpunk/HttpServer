package HttpServer;

import java.util.HashMap;
import java.util.function.Function;

public class Resource {
    private String uri;
    private HashMap<String, Function> allowedMethods = new HashMap<>();

    public Resource(Object o) {
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setHandler(String method, Function f) {
        allowedMethods.put(method, f);
    }

    public Function handler(String method) {
        return allowedMethods.get(method);
    }
}
