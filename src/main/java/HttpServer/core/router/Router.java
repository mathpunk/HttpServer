package HttpServer.core.router;

import HttpServer.core.definer.Handler;
import HttpServer.core.request.Request;
import HttpServer.core.response.Response;

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
        if (request.getMethod().equals("OPTIONS")) {
            return respondToOptionsQuery(request);
        } else {
            String uri = request.getUri();
            String method = request.getMethod();
            Handler handler = routes.retrieveHandler(uri, method);
            return handler.respond(request);
        }
    }

    public ArrayList<String> getDefinedMethods(String uri) {
        if (routes.isEmpty()) {
            return new ArrayList<>();
        } else {
            return routes.getDefinedMethods(uri);
        }
    }

    private Response respondToOptionsQuery(Request request) {
        Response response = new Response();
        String uri = request.getUri();
        if (uri != "*" && !getDefinedUris().contains(uri)) {
            response.setStatus(404);
            return response;
        }
        response.setStatus(200);
        ArrayList<String> allowedMethods = getDefinedMethods(request.getUri());
        response.setHeader("Allow", String.join(",", allowedMethods));
        return response;
    }

    public ArrayList<String> getDefinedUris() {
        return routes.getDefinedUris();
    }
}
