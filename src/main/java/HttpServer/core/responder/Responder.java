package HttpServer.core.responder;

import HttpServer.core.message.response.Response;
import HttpServer.core.message.request.Request;

import java.io.IOException;

public interface Responder {
    Response respond(Request request) throws IOException;
}
