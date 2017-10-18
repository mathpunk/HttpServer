package HttpServer;

import java.util.HashMap;

public class Controller {

    public HashMap routes;

    public Controller() {
        routes = new HashMap();

        Response ok = new Response().ok();
        add("GET", "/", ok);
        add("GET","/form", ok);
        add("GET", "/tea", ok);
        add("GET", "/coffee", new Response().teapot());
    }

    public void add(String verb, String uri, Response response) {
        if (routes.containsKey(verb)) {
            HashMap verbRoutes = (HashMap) routes.get(verb);
            verbRoutes.put(uri, response);
        } else {
            HashMap newVerbRoutes = new HashMap();
            newVerbRoutes.put(uri, response);
            routes.put(verb, newVerbRoutes);
        }
    }

    public Response respond(String verb, String uri) {
            HashMap verbRoutes = (HashMap) routes.get(verb);
        if (verbRoutes.containsKey(uri)) {
            return (Response) verbRoutes.get(uri);
        } else {
            return new Response().notFound();
        }
    }

}
