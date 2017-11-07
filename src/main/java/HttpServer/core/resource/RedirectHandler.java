package HttpServer.core.resource;

import HttpServer.core.request.Request;
import HttpServer.core.response.Response;

import java.util.HashMap;

public class RedirectHandler implements Handler {

    HashMap<String, String> redirectionMap;

    public RedirectHandler(HashMap<String, String> redirectionMap) {
        this.redirectionMap = redirectionMap;
    }

    public void redirect(String redirectFrom, String redirectTo) {
        redirectionMap.put(redirectFrom, redirectTo);
    }

    @Override
    public Response respond(Request request) {
        String uri = request.getUri();
        String location = redirectionMap.get(uri);
        Response response = new Response();
        response.setStatus(302);
        response.setHeader("Location", location);
        return response;
    }
}
