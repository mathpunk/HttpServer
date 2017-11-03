package HttpServer.router;

import HttpServer.definer.Handler;
import HttpServer.request.Request;
import HttpServer.definer.FunctionalHandler;
import HttpServer.response.Response;

public class Router {

    private final Routes routes;

    public Router() {
        this.routes = new Routes();
    }

    public void defineRoute(String uri, String method, Handler handler) {
        routes.define(uri, method, handler);
    }

    public Response route(Request request) {
        String uri = request.getUri();
        String method = request.getMethod();
        Handler handler = routes.retrieveHandler(uri, method);
        return handler.respond(request);
    }
}
