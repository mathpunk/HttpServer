package HttpServer.router;

import HttpServer.definer.Handler;
import HttpServer.definer.FunctionalHandler;

import java.util.HashMap;

public class Routes {

    private HashMap<String, HashMap<String, Handler>> resources;

    Routes() {
        resources = new HashMap();
    }

    public void define(String uri, String method, Handler handler) {
        HashMap allowedMethods;
        if (resources.get(uri) == null) {
           allowedMethods = new HashMap<String, Handler>();
        } else {
            allowedMethods = resources.get(uri);
        }
        allowedMethods.put(method, handler);
        resources.put(uri, allowedMethods);
    }

    public Handler retrieveHandler(String uri, String method) {
        return retrieveResourceHandler(uri, method);
    }

    private Handler retrieveResourceHandler(String uri, String method) {
        if (resources.get(uri) == null) {
            return new FunctionalHandler(404);
        } else {
            return retrieveMethodHandler(uri, method);
        }
    }

    private Handler retrieveMethodHandler(String uri, String method) {
        HashMap allowedMethods = resources.get(uri);
        if (allowedMethods.get(method) == null) {
            return new FunctionalHandler(405);
        } else {
            return (Handler) allowedMethods.get(method);
        }
    }
}
