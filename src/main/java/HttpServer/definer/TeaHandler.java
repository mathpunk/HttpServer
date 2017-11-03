package HttpServer.definer;

import HttpServer.request.Request;
import HttpServer.response.Response;

public class TeaHandler implements Handler {

    TeaHandler() {}

    @Override
    public Response respond(Request request) {
        Response response = new Response();
        String uri = request.getUri().trim();
        System.out.println("URI received by handler: " + uri);
        if (uri.equals("/tea")) {
            response.setStatus(200);
        } else if (uri.equals("/coffee")) {
            response.setStatus(418);
            response.setBody("I'm a teapot");
        } else {
            // If the TeaHandler is given a uri other than coffee or tea,
            // someone writing server-side code has defined a route
            // incorrectly.
            response.setStatus(500);
            response.setBody("Your request was mistakenly handled by a teapot.");
        }
        return response;
    }
}
