package HttpServer.definer;

import HttpServer.request.Request;
import HttpServer.response.Response;

public class TeaHandler implements Handler {

    TeaHandler() {}

    @Override
    public Response respond(Request request) {
        Response response = new Response();
        response.setStatus(200);
        return response;
    }
}
