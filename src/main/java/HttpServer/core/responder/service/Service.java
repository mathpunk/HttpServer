package HttpServer.core.responder.service;

import HttpServer.core.message.request.Request;
import HttpServer.core.responder.Responder;
import HttpServer.core.message.response.Response;

import java.io.IOException;

public interface Service extends Responder {

    // ArrayList<Clause> clausesServed();
    // void register(Router router, Uri endpoint);

    Response respond(Request request) throws IOException;
}

