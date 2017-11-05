package HttpServer.core.router;

import HttpServer.core.definer.Handler;
import HttpServer.core.definer.FunctionalHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class Routes {

    private HashMap<String, HashMap<String, Handler>> resources;

    public Routes() {
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

    public boolean isEmpty() {
        return resources.isEmpty();
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

    public ArrayList<String> getDefinedUris() {
        ArrayList<String> uris = new ArrayList<>();
        for (String uri : resources.keySet()) {
            uris.add(uri);
        }
        return uris;
    }

    public ArrayList<String> getDefinedMethods(String uri) {
        ArrayList<String> methods = new ArrayList<>();
        if (uri.equals("*")) {
            collectAllResourceMethods(methods);
        } else {
            for (String method : resources.get(uri).keySet()) {
                methods.add(method);
            }
        }
        return methods;
    }

    private void collectAllResourceMethods(ArrayList<String> methods) {
        ArrayList<String> uris = getDefinedUris();
        for (String thisUri : uris) {
            ArrayList<String> theseMethods = getDefinedMethods(thisUri);
            for (String method : theseMethods) {
                if (!methods.contains(method)) {
                    methods.add(method);
                }
            }
        }
    }
}
