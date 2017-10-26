package HttpServer;

import java.util.HashMap;

public class Controller {

    private final Router router;

    public Controller() {
        Routes secretRoutes = new Routes();
        Router secretRouter = new Router(secretRoutes);
        this.router = secretRouter;

        Response ok = new Response().setStatus(200);
        defineRoute("HEAD", "/", ok);

        serveRoot();
        serveForm();
        serveTea();
    }

    public void serveRoot() {
        Response ok = new Response().setStatus(200);
        defineRoute("GET", "/", ok);
    }

    public void serveForm() {
        Response ok = new Response().setStatus(200);
        defineRoute("GET", "/form", ok);
        defineRoute("PUT", "/form", ok);
    }

    public void serveTea() {
        Response teaResponse = new Response().setStatus(200);
        Response coffeeResponse = new Response().setStatus(418).putBody("I'm a teapot");

        defineRoute("GET", "/tea", teaResponse);
        defineRoute("GET", "/coffee", coffeeResponse);
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

