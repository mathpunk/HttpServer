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
        return retrieveResource(uri, method);
    }

    private RequestHandler retrieveResource(String uri, String method) {
        if (uriAssociations.get(uri) == null) {
            return new RequestHandler((request) -> new Response().putStatus(404));
        } else {
            return retrieveMethod(uri, method);
        }
    }

    private RequestHandler retrieveMethod(String uri, String method) {
        HashMap actionDefinitions = uriAssociations.get(uri);
        return (RequestHandler) actionDefinitions.get(method);
    }
}
