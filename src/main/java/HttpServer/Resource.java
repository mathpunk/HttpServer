package HttpServer;

import java.util.ArrayList;

public class Resource {

    private String uri;
    private ArrayList<String> allowedMethods;

    public Resource() {
        this.allowedMethods = new ArrayList<>();
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void allowMethod(String verb) {
        allowedMethods.add(verb);
    }

    public String getUri() {
        return uri;
    }

    public ArrayList<String> allowedMethods() {
        return allowedMethods;
    }
}
