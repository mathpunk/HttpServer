package HttpServer;

import java.util.HashMap;

public class Controller {

    public final Response ok;
    public final Response notFound;
    private final Response teapot;
    private final Router router;
    public HashMap routes;

    public Controller() {
        Routes secretRoutes = new Routes();
        Router secretRouter = new Router(secretRoutes);
        this.router = secretRouter;
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
        RequestHandler handler = new RequestHandler((request) -> response);
        router.defineRoute(uri, verb, handler);
    }

    public Response respond(Request request) {
        String verb = request.getMethod();
        String uri = request.getUri();
        return router.route(uri, verb);
    }
}

