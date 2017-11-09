package HttpServer.core.resource;

import HttpServer.core.request.Request;
import HttpServer.core.request.Uri;
import HttpServer.core.response.Response;

public class CookieService implements Handler {

    @Override
    public Response respond(Request request) {
        Uri uri = new Uri(request.getUriString());
        String action = uri.getPath();
        Response response = new Response();
        if (action.equals("/cookie")) {
            flavorCookie(uri, response);
        } else if (action.equals("/eat_cookie")) {
            eatCookie(request, response);
        } else {
            response.setStatus(500);
        }
        return response;
    }

    private void eatCookie(Request request, Response response) {
        response.setStatus(200);
        String flavorString = request.getHeader("Cookie");
        String[] tokens = flavorString.split("=");
        String flavor = tokens[1];
        response.setBody(cookieText(flavor));
    }

    private void flavorCookie(Uri uri, Response response) {
        response.setStatus(200);
        String cookie = "type=" + uri.getParameters().get("type");
        response.setHeader("Set-Cookie", cookie);
        response.setBody("Eat");
    }

    private String cookieText(String flavor) {
        return "mmmm " + flavor;
    }
}