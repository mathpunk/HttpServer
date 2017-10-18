package HttpServer;

import java.util.HashMap;

public class Controller {

    public HashMap routes;

    public Controller() {
        routes = new HashMap();
    }

//    public route(String verb, String uri) {
//        // "GET" -> "/" -> statusLine OK
//        // "PUT" -> "/form" -> statusLine OK
//        // "GET" -> "/tea" -> statusLine OK
//        // "GET" -> "/tea" -> statusLine OK
//        // "GET" -> "/coffee" -> Status: 418, body: "I am a teapot"
//    }

    public void addRoute(String verb, String uri, Response response) {
        if (routes.containsKey(verb)) {
            HashMap verbRoutes = (HashMap) routes.get(verb);
            verbRoutes.put(uri, response);
        } else {
            HashMap newVerbRoutes = new HashMap();
            newVerbRoutes.put(uri, response);
            routes.put(verb, newVerbRoutes);
        }
    }

    public Response respond(String verb, String uri) {
        HashMap verbRoutes = (HashMap) routes.get(verb);
        return (Response) verbRoutes.get(uri);
    }
}
