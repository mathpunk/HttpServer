package HttpServer;

import java.util.HashMap;

public class Routes {

    private HashMap<String, HashMap<String, RequestHandler>> uriAssociations;

    public Routes() {
        uriAssociations = new HashMap();
    }

    public void define(String uri, String method, RequestHandler handler) {
        HashMap actionDefinitions;
        if (uriAssociations.get(uri) == null) {
           actionDefinitions = new HashMap<String, RequestHandler>();
        } else {
            actionDefinitions = uriAssociations.get(uri);
        }
        actionDefinitions.put(method, handler);
        uriAssociations.put(uri, actionDefinitions);
    }

    public RequestHandler retrieve(String uri, String method) {
        HashMap actionDefinition = uriAssociations.get(uri);
        return (RequestHandler) actionDefinition.get(method);
    }
}
