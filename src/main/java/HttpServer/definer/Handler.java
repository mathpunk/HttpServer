package HttpServer.definer;

import HttpServer.response.Response;
import HttpServer.request.Request;

public interface Handler {
    Response respond(Request request);
}
