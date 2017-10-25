package HttpServer;

import HttpServer.Resource;

import java.util.HashMap;

public class Router {

    private HashMap<String, Resource> resources;
    private HashMap<String, HashMap<String, Handler>> handlers;

    public Router() {
        resources = new HashMap<>();
    }

    public void defineRoute(String verb, String uri, Response response) { }

    public Response respond(String verb, String uri) {
        return new Response().putStatus(200);
    }

    public Response route(Request request) {
        if (resources.get(request.getUri()) == null) {
            return new Response().putStatus(404);
        } else if (handlers.get(request.getUri()) == null || handlers.get(request.getUri()).get(request.getMethod()) == null) {
            return new Response().putStatus(405);
        } else {
            Handler handler = handlers.get(request.getUri()).get(request.getMethod());
            return handler.apply(request);
        }
    }

    public void defineResource(Resource resource) {
        String uri = resource.getUri();
        resources.put(uri, resource);
    }
}
