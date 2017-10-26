package HttpServer;

import java.util.HashMap;

public class Controller {

    public final Response ok;
    public final Response notFound;
    private final Response teapot;
    public HashMap routes;

    public Controller() {
//        Routes secretRoutes = new Routes();
//        Router secretRouter = new Router(secretRoutes);
//        this.router = new Router()
        routes = new HashMap();

        this.ok = new Response().setStatus(200);
        this.notFound = new Response().setStatus(404);
        this.teapot = new Response().setStatus(418).putBody("I'm a teapot");

        defineRoute("GET", "/", ok);
        defineRoute("PUT", "/form", ok);
        defineRoute("GET", "/tea", ok);
        defineRoute("HEAD", "/", ok);
        defineRoute("GET", "/coffee", teapot);
    }

    public void defineRoute(String verb, String uri, Response response) {
        if (routes.containsKey(verb)) {
            HashMap verbRoutes = (HashMap) routes.get(verb);
            verbRoutes.put(uri, response);
        } else {
            HashMap newVerbRoutes = new HashMap();
            newVerbRoutes.put(uri, response);
            routes.put(verb, newVerbRoutes);
        }
    }

    public Response respond(Request request) {
        String verb = request.getMethod();
        String uri = request.getUri();
        Response response;
        HashMap methodRoutes = (HashMap) routes.get(verb);
        if (methodRoutes.containsKey(uri)) {
            response = (Response) methodRoutes.get(uri);
        } else {
            response = notFound;
        }
        if (response.getBody() != null) {
            int contentLength = response.getBody().length();
            response.put("Content-Length", String.valueOf(contentLength));
        }
        return response;
    }
}

