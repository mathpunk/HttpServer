package HttpServer;

import java.io.IOException;
import java.util.HashMap;

public class Controller {

    public final Handler ok;
    public final Handler notFound;
    private final Handler teapot;
    public HashMap routes;

    public Controller() {
        routes = new HashMap();

        this.ok = request -> new Response().putStatus(200);
        this.notFound = request -> new Response().putStatus(404);
        this.teapot = request -> new Response().putStatus(418).putBody("I'm a teapot");

        defineRoute("GET", "/", ok);
        defineRoute("PUT", "/form", ok);
        defineRoute("GET", "/tea", ok);
        defineRoute("HEAD", "/", ok);
        defineRoute("GET", "/coffee", teapot);
    }

    public void defineRoute(String verb, String uri, Handler handler) {
        if (routes.containsKey(verb)) {
            HashMap verbRoutes = (HashMap) routes.get(verb);
            verbRoutes.put(uri, handler);
        } else {
            HashMap newVerbRoutes = new HashMap();
            newVerbRoutes.put(uri, handler);
            routes.put(verb, newVerbRoutes);
        }
    }

    public Response route(Request request) {
        String verb = request.getMethod();
        String uri = request.getUri();
        Response response;
        HashMap methodRoutes = (HashMap) routes.get(verb);
        if (methodRoutes.containsKey(uri)) {
            Handler handler = (Handler) methodRoutes.get(uri);
            response = handler.apply(request);
        } else {
            response = notFound.apply(request);
        }
        if (response.getBody() != null) {
            int contentLength = response.getBody().length();
            response.put("Content-Length", String.valueOf(contentLength));
        }
        return response;
    }
}

