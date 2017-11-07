package HttpServer.core.resource;

import HttpServer.core.response.Response;
import HttpServer.core.request.Request;

public interface Handler {
    Response respond(Request request);
}
