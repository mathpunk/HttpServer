package HttpServer.definer;

import HttpServer.request.Request;
import HttpServer.response.Response;

public class TeaHandler implements Handler {

    TeaHandler() {}

    @Override
    public Response respond(Request request) {
        Response response = new Response();
        if (request.getUri() == "/tea") {
            response.setStatus(200);
        } else if (request.getUri() == "/coffee") {
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
