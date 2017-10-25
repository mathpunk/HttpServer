package HttpServer;

import java.util.HashMap;

public class Router {

    public Router() {

    }

    public void defineRoute(String verb, String uri, Response response) { }

    public Response respond(String verb, String uri) {
        return new Response().putStatus(200);
    }

    public Response route(Request request) {
         return new Response().putStatus(405);
    }

}
