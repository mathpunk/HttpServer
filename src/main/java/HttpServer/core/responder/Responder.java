package HttpServer.core.service;

import HttpServer.core.response.Response;
import HttpServer.core.request.Request;

public interface Responder {
    Response respond(Request request);
}
