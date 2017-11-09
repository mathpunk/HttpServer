package HttpServer.core.router;

import HttpServer.core.responder.FunctionalResponder;
import HttpServer.core.responder.Responder;

import java.util.ArrayList;
import java.util.HashMap;

public class Routes {

    private HashMap<String, HashMap<String, Responder>> resources;

    public Routes() {
        resources = new HashMap();
    }

    public void define(String uri, String method, Responder responder) {
        HashMap allowedMethods;
        if (resources.get(uri) == null) {
           allowedMethods = new HashMap<String, Responder>();
        } else {
            allowedMethods = resources.get(uri);
        }
        allowedMethods.put(method, responder);
        resources.put(uri, allowedMethods);
    }

    public Responder retrieveHandler(String uri, String method) {
        return retrieveResourceHandler(uri, method);
    }

    public boolean isEmpty() {
        return resources.isEmpty();
    }

    private Responder retrieveResourceHandler(String uri, String method) {
        if (resources.get(uri) == null) {
            return new FunctionalResponder(404);
        } else {
            return retrieveMethodHandler(uri, method);
        }
    }

    private Responder retrieveMethodHandler(String uri, String method) {
        HashMap allowedMethods = resources.get(uri);
        if (allowedMethods.get(method) == null) {
            return new FunctionalResponder(405);
        } else {
            return (Responder) allowedMethods.get(method);
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
