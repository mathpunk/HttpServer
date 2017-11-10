package HttpServer.core.responder;

import HttpServer.core.message.response.Response;
import HttpServer.core.message.request.Request;

public interface Responder {
    Response respond(Request request);
}
