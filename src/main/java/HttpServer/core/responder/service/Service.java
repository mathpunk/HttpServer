package HttpServer.core.responder.service;

import HttpServer.core.request.Request;
import HttpServer.core.responder.Responder;
import HttpServer.core.response.Response;

public interface Service extends Responder {

    // ArrayList<Clause> clausesServed();
    // void register(Router router, Uri endpoint);

    Response respond(Request request);
}

