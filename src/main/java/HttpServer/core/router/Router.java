package HttpServer.core.router;

import HttpServer.core.responder.Responder;
import HttpServer.core.message.request.Request;
import HttpServer.core.message.response.Response;

import java.io.IOException;
import java.util.ArrayList;

public class Router {

    private final Routes routes;

    public Router() {
        this.routes = new Routes();
    }

    public void defineRoute(String uri, String method, Responder responder) {
        routes.define(uri, method, responder);
    }

    public Response route(Request request) {
        if (request.getMethod().equals("OPTIONS")) {
            return respondToOptionsQuery(request);
        } else {
            String uri = request.getPath();
            String method = request.getMethod();
            Responder responder = routes.retrieveHandler(uri, method);
            try {
                return responder.respond(request);
            } catch (IOException e) {
                System.out.println("Unhandled exception, 500ing. More information:");
                e.printStackTrace();
                return new Response(500);
            }
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
        String uri = request.getUriString();
        if (uri != "*" && !getDefinedUris().contains(uri)) {
            response.setStatus(404);
            return response;
        }
        return respondWithAllowedMethods(request, response);
    }

    private Response respondWithAllowedMethods(Request request, Response response) {
        response.setStatus(200);
        ArrayList<String> allowedMethods = getDefinedMethods(request.getUriString());
        response.setHeader("Allow", String.join(",", allowedMethods));
        return response;
    }

    public ArrayList<String> getDefinedUris() {
        return routes.getDefinedUris();
    }
}
