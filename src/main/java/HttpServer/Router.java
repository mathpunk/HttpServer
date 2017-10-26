package HttpServer;

public class Router {


    private final Routes routes;

    public Router(Routes routes) {
        this.routes = routes;
    }

    public void defineRoute(String uri, String method, RequestHandler handler) {
        routes.define(uri, method, handler);
    }

    public Response route(String uri, String method) {
        RequestHandler handler = routes.retrieve(uri, method);
        return handler.apply(new Request());
    }
}
