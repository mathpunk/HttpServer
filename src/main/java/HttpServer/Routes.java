package HttpServer;

import java.util.HashMap;

public class Routes {

    private HashMap<String, HashMap<String, RequestHandler>> uriAssociations;

    public Routes() {
        uriAssociations = new HashMap();
    }

    public void define(String uri, String method, RequestHandler handler) {
        HashMap actionDefinition = new HashMap<String, RequestHandler>();
        actionDefinition.put(method, handler);
        uriAssociations.put(uri, actionDefinition);
    }

    public RequestHandler retrieve(String uri, String method) {
        HashMap uriActions = uriAssociations.get(uri);
        return (RequestHandler) uriActions.get(method);
    }
}
