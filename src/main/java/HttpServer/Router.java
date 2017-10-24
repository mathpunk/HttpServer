package HttpServer;

public class Router {

    public Router() { }

    public void defineRoute(String verb, String uri, Response response) { }

    public Response respond(String verb, String uri) {
        return new Response().putStatus(200);
    }

    public Response respond(Request request) {
        return respond(request.getMethod(), request.getUri());
    }
}
