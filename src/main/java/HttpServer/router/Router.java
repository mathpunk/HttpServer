package HttpServer.router;

import HttpServer.request.Request;
import HttpServer.controller.RequestHandler;
import HttpServer.response.Response;

public class Router {

    private final Routes routes;

    public Router(Routes routes) {
        this.routes = routes;
    }

    public void defineRoute(String uri, String method, RequestHandler handler) {
        routes.define(uri, method, handler);
    }

    public void defineRoute(String uri, String method, Response response) {
        RequestHandler handler = new RequestHandler((request) -> response);
        defineRoute(uri, method, handler);
    }

    public Response respond(Request request) {
        String verb = request.getMethod();
        String uri = request.getUri();
        return route(uri, verb);
    }

    public Response route(String uri, String method) {
        RequestHandler handler = routes.retrieve(uri, method);
        return handler.apply(new Request());
    }

    public Response route(Request request) {
        return respond(request);
    }
}
