package HttpServer.router;

import HttpServer.definer.Handler;
import HttpServer.request.Request;
import HttpServer.response.Response;

import java.util.ArrayList;

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

    public ArrayList<String> getDefinedMethods(String uri) {
        if (routes.isEmpty()) {
            return new ArrayList<>();
        } else {
            return routes.getDefinedMethods(uri);
        }
    }

    public ArrayList<String> getDefinedUris() {
        return routes.getDefinedUris();
    }
}
