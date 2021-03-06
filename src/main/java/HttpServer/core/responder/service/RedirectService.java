package HttpServer.core.responder.service;

import HttpServer.core.message.request.Request;
import HttpServer.core.responder.Responder;
import HttpServer.core.message.response.Response;

import java.util.HashMap;

public class RedirectService implements Responder {

    HashMap<String, String> redirectionMap;

    public RedirectService(HashMap<String, String> redirectionMap) {
        this.redirectionMap = redirectionMap;
    }

    public void redirect(String redirectFrom, String redirectTo) {
        redirectionMap.put(redirectFrom, redirectTo);
    }

    @Override
    public Response respond(Request request) {
        String uri = request.getUriString();
        String location = redirectionMap.get(uri);
        Response response = new Response();
        response.setStatus(302);
        response.setHeader("Location", location);
        return response;
    }
}
